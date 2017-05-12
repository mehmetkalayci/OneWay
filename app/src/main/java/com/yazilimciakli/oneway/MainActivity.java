package com.yazilimciakli.oneway;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.ads.InterstitialAd;
import com.yazilimciakli.oneway.Dialog.ExitDialog;
import com.yazilimciakli.oneway.Utils.MusicManager;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Boolean continueMusic = true;


    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Reklam Kodları

        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1AB709011FC32397BD73E59EEF1AA148")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        });

        /*  /Reklam Kodları */




        /* Animation Code */
        final ImageView background1 = (ImageView) findViewById(R.id.anim1);
        final ImageView background2 = (ImageView) findViewById(R.id.anim2);
        final ImageView background3 = (ImageView) findViewById(R.id.anim3);

        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 4.0f);
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


        ImageButton btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        ImageButton btnSetting = (ImageButton) findViewById(R.id.btnSettings);
        ImageButton btnShare = (ImageButton) findViewById(R.id.btnShare);
        //ImageButton btnRateThisApp = (ImageButton) findViewById(R.id.btnRateThisApp);

        btnPlay.setOnClickListener(MainActivity.this);
        btnSetting.setOnClickListener(MainActivity.this);
        btnShare.setOnClickListener(MainActivity.this);
        //btnRateThisApp.setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                startActivity(new Intent(MainActivity.this, LevelActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
            case R.id.btnSettings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
                break;
            /*case R.id.btnRateThisApp:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.marketURL))));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            break;*/
            case R.id.btnShare:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.shareMessageTitle));
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.shareMessage));
                startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareIntentTitle)));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }

    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(this);
        exitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        exitDialog.setCancelable(false);
        exitDialog.show();
    }
}