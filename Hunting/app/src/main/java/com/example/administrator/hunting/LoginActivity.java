package com.example.administrator.hunting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.telephony.TelephonyManager;

/**
 * Created by Administrator on 2017-02-11.
 */

public class LoginActivity extends AppCompatActivity {
    @Override
    private String phoneNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        phoneNum = telephonyManager.getLine1Number();

        Button btn_login = (Button) findViewById(R.id.btn_login);
       btn_login.setOnClickListener(mLoginListener);
        //없을시 RegisterForm
        //있을시 로그인이후

    }

    Button.OnClickListener mLoginListener = new View.OnClickListener(){
        public void onClick(View v){
            //입장하기 클릭시 DB에서 phoneNum잇나 확인

        }

    };
}
