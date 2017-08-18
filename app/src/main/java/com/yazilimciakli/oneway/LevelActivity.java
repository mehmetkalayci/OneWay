package com.yazilimciakli.oneway;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yazilimciakli.oneway.Controls.GridFragment;
import com.yazilimciakli.oneway.Controls.LevelAdapter;
import com.yazilimciakli.oneway.Database.DatabaseHandler;
import com.yazilimciakli.oneway.Database.HealthHandler;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.Utils.LevelHelper;
import com.yazilimciakli.oneway.Utils.MusicHelper;
import com.yazilimciakli.oneway.Utils.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;


public class LevelActivity extends AppCompatActivity {

    Typeface typeface;
    TextView lblSelectLevel;

    CircleIndicator indicator;
    ViewPager levelPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView lblHealt, lblTotalPoints, lblGo, timer;
    int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        MainActivity.isBack = false;

        lblSelectLevel = (TextView) findViewById(R.id.lblSelectLevel);
        lblTotalPoints = (TextView) findViewById(R.id.lblTotalPoints);
        lblGo = (TextView) findViewById(R.id.lblGo);
        lblHealt = (TextView) findViewById(R.id.healt);
        timer = (TextView) findViewById(R.id.timer);

        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Atma.ttf");
        lblSelectLevel.setTypeface(typeface);
        lblTotalPoints.setTypeface(typeface);
        lblHealt.setTypeface(typeface);
        lblGo.setTypeface(typeface);

        final Date now = new Date();

        levelPager = (ViewPager) findViewById(R.id.levelPager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        levelPager.setAdapter(viewPagerAdapter);

        LevelHelper levelHelper = new LevelHelper(this);

        DatabaseHandler dbHandler = new DatabaseHandler(this);

        final HealthHandler healtHandler = new HealthHandler(this);

        //Level textlere değer atamaları yapılıyor
        lblTotalPoints.setText(dbHandler.getPoints());
        lblHealt.setText(String.valueOf(healtHandler.getHealth(1).get("health")));


        //kalan sürenin hesaplanması için hesap değişkeni oluşturup default değer atandı
        Long hesap = Long.valueOf(1000);

        //Canı kalmamışsa işlem yapacak
        if (Integer.parseInt(healtHandler.getHealth(1).get("health")) == 0) {
            /* Canı kalmamışsa işlem yapacak
            * eğer canı yoksa işlem başlar
            * hesap değişkenine veritabanından gelecekte olması istenilen süre çekilir
            * çekilen süreden şimdiki zaman çıkarılır ve kalan süre bulunur*/
            hesap = Long.parseLong(healtHandler.getHealth(1).get("timeStamp")) - now.getTime();

            //Kalan süre hesap değişkeninden timer a aktarıldı.
            new CountDownTimer(hesap, 1000) { // gelen süre milisaniye cinsinden

                //saniye sayma fonksiyonu
                public void onTick(long millisUntilFinished) {
                    timer.setText("" + String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }

                //sayma bittiğinde bu fonksiyon çalışacak
                public void onFinish() {
                    HealthHandler healtHandler = new HealthHandler(getApplicationContext());
                    if (Integer.parseInt(healtHandler.getHealth(1).get("health")) != 5) {
                        healtHandler.setHealt(5, now.getTime(), 1);
                    }
                    timer.setText(getResources().getString(R.string.fullHealth));
                    lblHealt.setText(String.valueOf(healtHandler.getHealth(1).get("health")));
                }
            }.start();
        } else {

            if (Integer.parseInt(healtHandler.getHealth(1).get("health")) == 5) {
                timer.setText(getResources().getString(R.string.fullHealth));
            } else {
                timer.setText(getResources().getString(R.string.lowHealth));
            }
        }


        int page;
        double perPage = 9;
        double totalItems = levelHelper.getLevelSize();
        double totalPage = Math.ceil(totalItems / perPage);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        pageNumber = (int) Math.ceil(databaseHandler.getAllLevels().size() / perPage);
        for (page = 1; page <= totalPage; page++) {
            int limit = (int) ((page - 1) * perPage);

            List<Level> tempLevels = new ArrayList<>();
            tempLevels = levelHelper.getLevels().subList(limit, (int) ((page == totalPage) ? totalItems : perPage * page));

            LevelAdapter levelAdapter = new LevelAdapter(this, tempLevels);
            viewPagerAdapter.addFragment(GridFragment.newInstance(levelAdapter, page));
        }
        levelPager.setCurrentItem(pageNumber - 1);
        indicator.setViewPager(levelPager);
        if (!MainActivity.musicHelper.isPlaying())
            MainActivity.musicHelper.prepareMusicPlayer(this, MusicHelper.MUSICS.MainMusic);
    }

    @Override
    public void onBackPressed() {
        MainActivity.isBack = true;
        MainActivity.musicHelper.setPlaying(true);
        startActivity(new Intent(LevelActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerAdapter.notifyDataSetChanged();
        if (SettingsActivity.getMusicStatus(this)) {
            MainActivity.musicHelper.playMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!MainActivity.isBack) {
            MainActivity.musicHelper.pauseMusic();
        }
    }


}