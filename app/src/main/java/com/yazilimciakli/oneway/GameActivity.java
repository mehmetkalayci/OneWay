package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.yazilimciakli.oneway.Graph.GameView;
import com.yazilimciakli.oneway.Utils.MusicHelper;

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

        FrameLayout gameBackground = (FrameLayout) findViewById(R.id.gameBackground);


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

        if (data == 0) {
            gameBackground.setBackgroundResource(R.drawable.levelbg1);
        }
        if (data == 1) {
            gameBackground.setBackgroundResource(R.drawable.levelbg2);
        }
        if (data == 2) {
            gameBackground.setBackgroundResource(R.drawable.levelbg3);
        }
        if (data == 3) {
            gameBackground.setBackgroundResource(R.drawable.levelbg4);
        }
        if (data == 4) {
            gameBackground.setBackgroundResource(R.drawable.levelbg5);
        }
        if (data == 5) {
            gameBackground.setBackgroundResource(R.drawable.levelbg6);
        }
        if (data == 6) {
            gameBackground.setBackgroundResource(R.drawable.levelbg7);
        }
        if (data == 7) {
            gameBackground.setBackgroundResource(R.drawable.levelbg8);
        }
        if (data == 8) {
            gameBackground.setBackgroundResource(R.drawable.levelbg9);
        }
        if (data == 9) {
            gameBackground.setBackgroundResource(R.drawable.levelbg10);
        }
        if (data == 10) {
            gameBackground.setBackgroundResource(R.drawable.levelbg11);
        }
        if (data == 11) {
            gameBackground.setBackgroundResource(R.drawable.levelbg12);
        }
        if (data == 12) {
            gameBackground.setBackgroundResource(R.drawable.levelbg13);
        }
        if (data == 13) {
            gameBackground.setBackgroundResource(R.drawable.levelbg14);
        }
        if (data == 14) {
            gameBackground.setBackgroundResource(R.drawable.levelbg15);
        }
        if(MainActivity.isBack)
        {
            MainActivity.musicHelper.prepareMusicPlayer(this, MusicHelper.MUSICS.GameMusic);
            MainActivity.musicHelper.playMusic();
        }

    }

    @Override
    public void onBackPressed() {
        MainActivity.isBack=true;
        MainActivity.musicHelper.prepareMusicPlayer(this, MusicHelper.MUSICS.MainMusic);
        Intent intent = new Intent(this, LevelActivity.class);
        startActivity(intent);
        view.mHandler.removeCallbacks(this.view);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.musicHelper.playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!MainActivity.isBack)
        {
            MainActivity.musicHelper.pauseMusic();

        }
    }
}