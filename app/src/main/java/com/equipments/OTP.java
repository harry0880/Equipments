package com.equipments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.equipments.GettersSetters.Instance;
import com.equipments.GettersSetters.NotificationToken;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Administrator on 29/06/2016.
 */
public class OTP extends AppCompatActivity {
EditText otp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.otp_activity);
        Instance.setInstanceId(FirebaseInstanceId.getInstance().getId());
        NotificationToken.setNotificationToken(FirebaseInstanceId.getInstance().getToken());
         super.onCreate(savedInstanceState);
    }

    void initialize()
    {
        otp=(EditText) findViewById(R.id.otp);
    }
}
