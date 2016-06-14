package com.equipments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.equipments.Utils.DBConstant;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 10/06/2016.
 */
public class DataInput3 extends Fragment implements View.OnClickListener{

    ImageButton btnCamera;
    private String mCurrentPhotoPath;
    Bitmap processedBitmap;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView img,imgThumb[] ;
    RelativeLayout bar;
    EditText imdesc;
    static int img_cnt = 0;
    LinearLayout imagelayout;
    FancyButton btnSave,btnCam,btnCancel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_datainput3, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        initialize(view);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Save_Photo_Async_Task().execute();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    void initialize(View view)
    {
        btnCamera=(ImageButton) view.findViewById(R.id.btnCamera);

        bar=(RelativeLayout)view.findViewById(R.id.loadingPane);
        bar.setVisibility(View.GONE);

        img = (ImageView) view.findViewById(R.id.Snap);
        mAlbumStorageDirFactory = new BaseAlbumDirFactory();

        imdesc=(EditText) view.findViewById(R.id.desc);
        imgThumb=new ImageView[DBConstant.C_No_of_Image];
        imagelayout=(LinearLayout) view.findViewById(R.id.imagelayout);

        btnSave=(FancyButton) view.findViewById(R.id.btnSave);
        btnCam=(FancyButton) view.findViewById(R.id.btnCam);
        btnCancel=(FancyButton) view.findViewById(R.id.btnCancel);

      /*  btnSave.setIconResource(R.drawable.camera);
        btnCam.setIconResource(R.drawable.camera);
        btnCancel.setIconResource(R.drawable.camera);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        processedBitmap=handleSmallCameraPhoto();
    }

    private Bitmap handleSmallCameraPhoto() {
        int targetW = img.getWidth();
        int targetH = img.getHeight();
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        if(bitmap!=null) {
            img.setImageBitmap(bitmap);
            img.setVisibility(View.VISIBLE);
        }
        return bitmap;
    }

    private File setUpPhotoFile() throws IOException {

        File f = createImageFile();
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private String getAlbumName() {
        return "Equipments";
    }

    public void setThumbnail()
    {
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        imgThumb[img_cnt]=new ImageView(getActivity());
        imgThumb[img_cnt].setOnClickListener(this);
        imgThumb[img_cnt].setId(img_cnt+1);
        lp.setMargins(2,2,2,2);
        imagelayout.addView(imgThumb[img_cnt],lp);
        Bitmap scaled=null;
        if(processedBitmap.getWidth()>processedBitmap.getHeight()) {
            scaled = Bitmap.createScaledBitmap(processedBitmap, 150, 100, true);
        }else
        {
            scaled = Bitmap.createScaledBitmap(processedBitmap, 100, 150, true);
        }
        imgThumb[img_cnt].setImageBitmap(scaled);
        img_cnt++;
    }

    @Override
    public void onClick(View v) {

    }

    private class Save_Photo_Async_Task extends AsyncTask<Void,Void,Boolean>
    {
        @Override
        protected void onPreExecute() {
            bar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            return true;//save_photo();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            bar.setVisibility(View.GONE);
            if(result)
            {
                Toast.makeText(getActivity(),"Image Saved!!!",Toast.LENGTH_SHORT).show();
                setThumbnail();
                img.setImageResource(0);
                imdesc.setText("");
            }
            else
            {
                Toast.makeText(getActivity(),"Failed!!!",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(result);
        }
    }
}

