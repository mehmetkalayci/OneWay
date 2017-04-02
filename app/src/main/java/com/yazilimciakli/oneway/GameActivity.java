package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.app.Activity;
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


        /* Animation Code */
        final ImageView background1 = (ImageView) findViewById(R.id.anim1);
        final ImageView background2 = (ImageView) findViewById(R.id.anim2);
        final ImageView background3 = (ImageView) findViewById(R.id.anim3);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 4.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(100000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                float width = background1.getWidth();
                float translationX = width * progress;
                background1.setTranslationX(translationX);
                background2.setTranslationX(translationX - width);
                background3.setTranslationX(translationX - width - width);
                background1.setTranslationX(translationX - width - width - width);
            }
        });
        animator.start();
        /* /Animation Code */


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        Intent openLevelIntent = getIntent();
        Integer data = openLevelIntent.getIntExtra("levelId", 0);

        view = (GameView) findViewById(R.id.view);
        view.setWidth(width);
        view.setLevel(data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
}