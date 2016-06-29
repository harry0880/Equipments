package com.equipments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.equipments.GettersSetters.Instance;
import com.equipments.GettersSetters.NotificationToken;
import com.equipments.Utils.Dbhandler;
import com.google.firebase.iid.FirebaseInstanceId;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 29/06/2016.
 */
public class OTP extends AppCompatActivity {
EditText otp;
    FancyButton btnSubmit;
    Dbhandler dbh;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.otp_activity);
        initialize();
        context=this;
        Instance.setInstanceId(FirebaseInstanceId.getInstance().getId());
        NotificationToken.setNotificationToken(FirebaseInstanceId.getInstance().getToken());
        if(dbh.chklogin())

        {
            startActivity(new Intent(context, Login.class));
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VerifyServer().execute(otp.getText().toString());
            }
        });

         super.onCreate(savedInstanceState);
    }

    void initialize()
    {
        otp=(EditText) findViewById(R.id.otp);
        dbh= new Dbhandler(OTP.this);
        btnSubmit=(FancyButton) findViewById(R.id.btnSubmit);
    }

    private class VerifyServer extends AsyncTask<String,Void,Boolean>
    {
        ProgressDialog dialog=new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Authenticating Device");
            dialog.setTitle("Please Wait");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            return dbh.SendOTP(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
           dialog.dismiss();
            if(aBoolean)
                new getMaster_Tables().execute();
            else
            new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("Error").show();
            super.onPostExecute(aBoolean);
        }
    }
    private class getMaster_Tables extends AsyncTask<Void,Void,String>
    {
        ProgressDialog dialog=new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading Data");
            dialog.setTitle("Please Wait");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            return dbh.Load_Master_tables();

        }

        @Override
        protected void onPostExecute(String s) {
           dialog.dismiss();
            if(s.equals("Error"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("No Internet Connection").show();
            }
            else if(s.equals("ErrorServer"))
            {
                new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setTitleText("Unable to connect with Server!!!").show();
            }
            else {
                startActivity(new Intent(context, Login.class));
                finish();
            }
            super.onPostExecute(s);
        }
    }
}
