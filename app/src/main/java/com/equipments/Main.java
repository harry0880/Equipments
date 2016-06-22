package com.equipments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.equipments.GettersSetters.InpectionId;
import com.equipments.GettersSetters.Time;
import com.equipments.Utils.AndroidDatabaseManager;
import com.equipments.Utils.Dbhandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 09/06/2016.
 */
public class Main extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener,LocationSource,ActivityCompat.OnRequestPermissionsResultCallback {
    SearchableSpinner spInstType;
    ArrayAdapter<String> instituteTypeAdapter;
    Context context;

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean mRequestingLocationUpdates = false;
    Dbhandler db;
    private LocationRequest mLocationRequest;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private LocationSource.OnLocationChangedListener mMapLocationListener = null;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private static final int REQUEST_Permission = 0;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityviewpager);
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Equipments");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(R.layout.tabs, tab, false));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        FragmentPagerItems pages = new FragmentPagerItems(this);

        pages.add(FragmentPagerItem.of("Location", DataInput1.class));
        pages.add(FragmentPagerItem.of("Equipments 1", DataInput4.class));
        pages.add(FragmentPagerItem.of("Equipments 2", DataInput2.class));
        pages.add(FragmentPagerItem.of("Snap", DataInput3.class));


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);

        if (getGPSStatus()) {
            if (isMockSettingsON(context)) {
                final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("Mock Locations!!!");
                dialog.setContentText("Please disable mock location first!!");
                dialog.show();
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        {
                            // GPSon();
                            // dialog.dismiss();
                            finish();
                        }
                    }
                });
            } else if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_Permission
                );
            } else {
                initialize_Location();
            }
        } else {
            {
                final SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE).setTitleText("GPS Turned off!!!");
                dialog.setContentText("Please Turn On the GPS!!");
                dialog.show();
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        {
                            // GPSon();
                            // dialog.dismiss();
                            finish();
                        }
                    }
                });
            }
        }
    }

    void initialize_Location() {
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            mGoogleApiClient.connect();
        }
    }

    private Boolean getGPSStatus() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return statusOfGPS;
    }

    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        setLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    private boolean checkPlayServices() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog

            }

            return false;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL);

        mLocationRequest.setFastestInterval(FATEST_INTERVAL);

        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void setLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
           // getLocationPermission();
        } else {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

        if (mLastLocation != null) {

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            com.equipments.GettersSetters.Location.setLocation(latitude + " " + longitude);
            Toast.makeText(context, latitude + " " + longitude, Toast.LENGTH_SHORT).show();
            Time.setTime(get_Time());
        }
    }

    void getLocationPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private String get_Time()
    {
        long loc=mLastLocation.getTime();
        Date dt=new Date(loc);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(dt);
        return time;
    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation=location;
        setLocation();
        if (mMapLocationListener != null) {
            mMapLocationListener.onLocationChanged(location);
        }
    }

    @Override
    public void activate(LocationSource.OnLocationChangedListener onLocationChangedListener) {
        mMapLocationListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mMapLocationListener = null;
    }
    //END
    //***************////LocationManager////////***********


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Main.this, AndroidDatabaseManager.class));
        InpectionId.setId("-1");
        super.onBackPressed();
    }
    public void switchFragment(){
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }




}
