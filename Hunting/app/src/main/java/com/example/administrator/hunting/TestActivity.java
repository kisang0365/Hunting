package com.example.administrator.hunting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2017-02-11.
 */

public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    public void onClick(View view){

        switch (view.getId()){
            case R.id.button1:
                Intent intent =  new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                intent =  new Intent(this, RegisterAcitivty.class);
                startActivity(intent);
                break;
            case R.id.button3:
                intent =  new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.button4:
                //intent =  new Intent(this, LoginActivity.class);
                //startActivity(intent);
                break;
        }
    }
}


