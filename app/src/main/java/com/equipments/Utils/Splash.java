package com.equipments.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.equipments.InspectionList;
import com.equipments.R;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Splash extends AppCompatActivity {

    Dbhandler dbh;
    Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        dbh=new Dbhandler(Splash.this);
        context=this;
        if(!doesDatabaseExist(getApplicationContext(),DBConstant.DBNameMaster))
            new getMaster_Tables().execute();
        else
        {
            startActivity(new Intent(Splash.this,InspectionList.class));
            finish();
        }
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private class getMaster_Tables extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... params) {

            return dbh.Load_Master_tables();

        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("Error"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("No Internet Connection").show();
            }
            else if(s.equals("ErrorServer"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            }
            else {
               startActivity(new Intent(Splash.this, InspectionList.class));
                finish();
            }
            super.onPostExecute(s);
        }
    }
}
