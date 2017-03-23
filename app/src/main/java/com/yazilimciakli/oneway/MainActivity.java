package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yazilimciakli.oneway.Utils.Calligrapher;

public class MainActivity extends AppCompatActivity {

    public static String FONT = "fonts/Atma.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, FONT, true);

        final ImageView backgroundOne = (ImageView) findViewById(R.id.anim1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.anim2);
        final ImageView backgroundThree = (ImageView) findViewById(R.id.anim3);

        final ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 4.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = backgroundOne.getWidth();
                final float translationX = width * progress;
                backgroundOne.setTranslationX(translationX);
                backgroundTwo.setTranslationX(translationX - width);
                backgroundThree.setTranslationX(translationX - width- width);
                backgroundOne.setTranslationX(translationX- width- width-width);
            }
        });
        animator.start();

        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, LevelActivity.class);
                startActivity(intent);
            }
        });

        ImageButton btnSetting = (ImageButton) findViewById(R.id.btnSettings);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });


    }
}