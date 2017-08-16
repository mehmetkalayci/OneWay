package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.yazilimciakli.oneway.Graph.GameView;
import com.yazilimciakli.oneway.Utils.MusicHelper;

public class GameActivity extends AppCompatActivity {


    GameView view;
    public static boolean between=true;
    //Reklam için
    public static InterstitialAd mInterstitialAd;
    public static boolean hideAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        reklam_goster();

        MainActivity.isBack = false;

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

        if (data == 0 || data == 15 || data == 30 || data == 45 || data == 60 || data == 75 || data == 90) {
            gameBackground.setBackgroundResource(R.drawable.levelbg1);
            hideAd=true;
        }
        if (data == 1 || data == 16 || data == 31 || data == 46 || data == 61 || data == 76 || data == 91) {
            gameBackground.setBackgroundResource(R.drawable.levelbg2);
            hideAd=false;
        }
        if (data == 2 || data == 17 || data == 32 || data == 47|| data == 62 || data == 77 || data == 92) {
            gameBackground.setBackgroundResource(R.drawable.levelbg3);
            hideAd=true;
        }
        if (data == 3 || data == 18 || data == 33 || data == 48 || data == 63 || data == 78 || data == 93) {
            gameBackground.setBackgroundResource(R.drawable.levelbg4);
            hideAd=false;
        }
        if (data == 4 || data == 19 || data == 34 || data == 49 || data == 64 || data == 79 || data == 94) {
            gameBackground.setBackgroundResource(R.drawable.levelbg5);
            hideAd=true;
        }
        if (data == 5 || data == 20 || data == 35 || data == 50 || data == 65 || data == 80 || data == 95) {
            gameBackground.setBackgroundResource(R.drawable.levelbg6);
            hideAd=false;
        }
        if (data == 6 || data == 21 || data == 36 || data == 51 || data == 66 || data == 81 || data == 96) {
            gameBackground.setBackgroundResource(R.drawable.levelbg7);
            hideAd=true;
        }
        if (data == 7 || data == 22 || data == 37 || data == 52 || data == 67 || data == 82 || data == 97) {
            gameBackground.setBackgroundResource(R.drawable.levelbg8);
            hideAd=false;

        }
        if (data == 8 || data == 23 || data == 38 || data == 53 || data == 68 || data == 83 || data == 98) {
            gameBackground.setBackgroundResource(R.drawable.levelbg9);
            hideAd=true;
        }
        if (data == 9 || data == 24 || data == 39 || data == 54 || data == 69 || data == 84 || data == 99) {
            gameBackground.setBackgroundResource(R.drawable.levelbg10);
            hideAd=false;
        }
        if (data == 10 || data == 25 || data == 40 || data == 55 || data == 70 || data == 85) {
            gameBackground.setBackgroundResource(R.drawable.levelbg11);
            hideAd=true;
        }
        if (data == 11 || data == 26 || data == 41 || data == 56 || data == 71 || data == 86) {
            gameBackground.setBackgroundResource(R.drawable.levelbg12);
            hideAd=false;
        }
        if (data == 12 || data == 27 || data == 42 || data == 57 || data == 72 || data == 87) {
            gameBackground.setBackgroundResource(R.drawable.levelbg13);
            hideAd=true;
        }
        if (data == 13 || data == 28 || data == 43 || data == 58 || data == 73 || data == 88) {
            gameBackground.setBackgroundResource(R.drawable.levelbg14);
            hideAd=false;
        }
        if (data == 14 || data == 29 || data == 44 || data == 59 || data == 74 || data == 89) {
            gameBackground.setBackgroundResource(R.drawable.levelbg15);
            hideAd=true;
        }
        if(between)
        {
            MainActivity.musicHelper.prepareMusicPlayer(this, MusicHelper.MUSICS.GameMusic);
            if(SettingsActivity.getMusicStatus(this))
            {
                MainActivity.musicHelper.playMusic();
            }
        }
    }

    public void reklam_goster() {
        MobileAds.initialize(this, getString(R.string.levelArasi));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.levelArasi));
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
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
        if(SettingsActivity.getMusicStatus(this))
        {
            MainActivity.musicHelper.playMusic();
        }
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