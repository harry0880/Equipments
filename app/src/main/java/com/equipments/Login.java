package com.equipments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.equipments.Utils.Dbhandler;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Administrator on 29/06/2016.
 */
public class Login extends AppCompatActivity {
    FancyButton login;
    EditText username,password;
    Dbhandler db;
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.login);
        context=this;
        initialize();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.LoginMatch(username.getText().toString(),password.getText().toString()))
                {
                    startActivity(new Intent(Login.this,InspectionList.class));
                }
                else {
                    new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE).setContentText("Wrong UserID and Password").show();
                }
            }
        });
        super.onCreate(savedInstanceState);
    }

    void initialize()
    {
     login=(FancyButton)findViewById(R.id.btnSubmit);
        username=(EditText) findViewById(R.id.etusername);
        password=(EditText) findViewById(R.id.etpassword);
        db=new Dbhandler(Login.this);

    }

}
