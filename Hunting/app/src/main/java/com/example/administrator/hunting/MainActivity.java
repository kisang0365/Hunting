package com.example.administrator.hunting;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import serve.ListViewMain;
import serve.ListViewMainAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView;
        ListViewMainAdapter adapter;

        adapter = new ListViewMainAdapter();

        listView = (ListView) findViewById(R.id.lv_main);
        listView.setAdapter(adapter);

        for(int i=0; i<5; i++)
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.basic_picture), "닉네임", "나이");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){
                ListViewMain item = (ListViewMain) parent.getItemAtPosition(position);

                String title = item.getTitle();
                String name = item.getDesc();
                Drawable iconDrawable = item.getIcon();
            }

        });
    }


}
