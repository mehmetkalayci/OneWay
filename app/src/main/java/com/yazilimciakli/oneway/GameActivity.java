package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.yazilimciakli.oneway.Graph.GameView;

public class GameActivity extends AppCompatActivity {

    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        view = (GameView) findViewById(R.id.view);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        Intent openLevelIntent = getIntent();
        Integer data = openLevelIntent.getIntExtra("levelId", 0);

        final ImageView backgroundOne = (ImageView) findViewById(R.id.anim1);
        final ImageView backgroundTwo = (ImageView) findViewById(R.id.anim2);
        final ImageView backgroundThree = (ImageView) findViewById(R.id.anim3);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 4.0f);
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

        view.setWidth(width);
        view.setLevel(data);
    }
}