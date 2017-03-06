package com.example.administrator.hunting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.telephony.TelephonyManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import server.LoginConnect;

/**
 * Created by Administrator on 2017-02-11.
 */

public class LoginActivity extends AppCompatActivity {

    private String phoneNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        phoneNum = telephonyManager.getLine1Number();

        ImageView iv_login = (ImageView) findViewById(R.id.iv_login);
        iv_login.setOnClickListener(mLoginListener);
        //없을시 RegisterForm
        //있을시 로그인이후

    }

    ImageView.OnClickListener mLoginListener = new View.OnClickListener(){
        public void onClick(View v) {
            boolean login = false;
            try {
                //db에서 phoneNumber확인
                login = new LoginConnect().execute(phoneNum).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //있을시 main으로
            if(login){
                Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            //없을시 register하기
            else{
                Intent intent = new Intent(getApplicationContext(), RegisterAcitivty.class);
                intent.putExtra("phoneNumber", phoneNum);
                startActivity(intent);
            }
            //Toast toast = Toast.makeText(getApplicationContext(),""+phoneNum,Toast.LENGTH_LONG);
            //toast.show();
        }

    };
}
