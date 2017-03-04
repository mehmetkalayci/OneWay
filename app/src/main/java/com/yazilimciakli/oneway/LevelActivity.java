package com.yazilimciakli.oneway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Button btn1=(Button)(findViewById(R.id.level1));
        Button btn2=(Button)(findViewById(R.id.level2));
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),GameActivity.class);
                intent.putExtra("level",0);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),GameActivity.class);
                intent.putExtra("level",1);
                startActivity(intent);
            }
        });

    }
}
