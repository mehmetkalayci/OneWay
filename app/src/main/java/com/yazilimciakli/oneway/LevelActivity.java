package com.yazilimciakli.oneway;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.Level.Level;

import java.util.Arrays;
import java.util.List;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);


        Gson gson = new Gson();

        List<String> levelList = Arrays.asList(getResources().getStringArray(R.array.levels));
        String currentLevel = levelList.get(0).toString();

        Level level = gson.fromJson(currentLevel, Level.class);

        Toast.makeText(LevelActivity.this, level.name, Toast.LENGTH_SHORT).show();


    }
}
