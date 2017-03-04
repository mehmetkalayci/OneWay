package com.yazilimciakli.oneway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.yazilimciakli.oneway.Graph.GameView;

public class GameActivity extends AppCompatActivity {

    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        Intent data=getIntent();
        Integer veri=data.getIntExtra("level",0);
        view=(GameView) findViewById(R.id.view);
        view.setWidth(width);
        view.setLevel(veri);
    }
}