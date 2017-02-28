package com.example.administrator.hunting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Created by Administrator on 2017-02-11.
 */

public class RegisterAcitivty extends AppCompatActivity{

    EditText et_id;
    EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_id = (EditText) findViewById(R.id.et_register_id);
        et_password = (EditText) findViewById(R.id.et_register_password);

        Button btn_regist = (Button) findViewById(R.id.btn_register_done);
        btn_regist.setOnClickListener(mRegisterListener);
    }

    Button.OnClickListener mRegisterListener = new View.OnClickListener() {
        public void onClick(View v) {
            //입장하기 클릭시 DB에서 phoneNum잇나 확인
            Toast toast = Toast.makeText(getApplicationContext(), et_id.getText().toString() + "pass : " + et_password.getText().toString(), Toast.LENGTH_LONG);
            toast.show();
        }
    };
}
