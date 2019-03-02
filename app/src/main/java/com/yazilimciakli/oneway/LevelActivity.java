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
import com.yazilimciakli.oneway.Database.LevelsDB;
import com.yazilimciakli.oneway.Database.PlayLevelsDB;
import com.yazilimciakli.oneway.Database.PointsDB;
import com.yazilimciakli.oneway.Database.ProfileDB;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.Object.DatabaseObject;
import com.yazilimciakli.oneway.Utils.LevelHelper;
import com.yazilimciakli.oneway.Utils.MusicHelper;
import com.yazilimciakli.oneway.Utils.ViewPagerAdapter;

import java.sql.SQLException;
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
    public static TextView lblhealt, lblTotalPoints, lblGo,timer;
    public static CountDownTimer waitTimer;
    private DatabaseObject databaseObject;
    int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        MainActivity.isBack = false;

        lblSelectLevel  =   findViewById(R.id.lblSelectLevel);
        lblTotalPoints  =   findViewById(R.id.lblTotalPoints);
        lblGo           =   findViewById(R.id.lblGo);
        lblhealt        =   findViewById(R.id.healt);
        timer           =   findViewById(R.id.timer);
        databaseObject  =   DatabaseObject.newInstance(getApplicationContext());
        typeface        =   Typeface.createFromAsset(getResources().getAssets(), "fonts/Atma.ttf");

        lblSelectLevel.setTypeface(typeface);
        lblTotalPoints.setTypeface(typeface);
        lblhealt.setTypeface(typeface);
        lblGo.setTypeface(typeface);
        /**ilk kez oyuna giriliyorsa custom profile oluştur*/
        if(databaseObject.getProfileDB().count()==0)
        {
            databaseObject.getProfileDB().create(new ProfileResponse(1,5,0,0,"",""));
        }
        final Date now = new Date();

        levelPager  =   findViewById(R.id.levelPager);
        indicator   =   findViewById(R.id.indicator);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        levelPager.setAdapter(viewPagerAdapter);

        //Level textlere değer atamaları yapılıyor
        int totalCoins      =       databaseObject.getProfileDB().getAll().get(0).getHealt();
        final int healt     =       databaseObject.getProfileDB().getAll().get(0).getHealt();
        String timeStamp    =   databaseObject.getProfileDB().getAll().get(0).getTimeStamp();
        lblTotalPoints.setText(String.valueOf(totalCoins));
        lblhealt.setText(String.valueOf(healt));


        //kalan sürenin hesaplanması için hesap değişkeni oluşturup default değer atandı
        Long hesap= Long.valueOf(1000);

        //Canı kalmamışsa işlem yapacak
        if(healt==0)
        {
            /** Canı kalmamışsa işlem yapacak
            * eğer canı yoksa işlem başlar
            * hesap değişkenine veritabanından gelecekte olması istenilen süre çekilir
            * çekilen süreden şimdiki zaman çıkarılır ve kalan süre bulunur*/
            hesap =Long.parseLong(timeStamp)-now.getTime();

            //Kalan süre hesap değişkeninden timer a aktarıldı.
            waitTimer=new CountDownTimer(hesap, 1000) { // gelen süre milisaniye cinsinden

                //saniye sayma fonksiyonu
                public void onTick(long millisUntilFinished) {
                    timer.setText(""+String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                //sayma bittiğinde bu fonksiyon çalışacak
                public void onFinish() {
                    if(healt!=5)
                    {
                        try {
                            int id=databaseObject.getProfileDB().getAll().get(0).getId();
                            databaseObject.getProfileDB().getUpdateBuilder(id)
                                    .updateColumnValue(ProfileResponse.HEALT,5)
                                    .updateColumnValue(ProfileResponse.ENDED_TIME,String.valueOf(now.getTime()))
                                    .update();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    timer.setText(getResources().getString(R.string.fullHealth));
                    lblhealt.setText(String.valueOf(databaseObject.getProfileDB().getAll().get(0).getHealt()));
                }
            }.start();
        }else
        {

            if(databaseObject.getProfileDB().getAll().get(0).getHealt()==5)
            {
                timer.setText(getResources().getString(R.string.fullHealth));
            }
            else
            {
                timer.setText(getResources().getString(R.string.lowHealth));
            }
        }




        int page;
        double perPage = 9;
        double totalItems = (double) databaseObject.getLevelsDB().count();
        double totalPage = Math.ceil(totalItems / perPage);


        pageNumber= (int) Math.ceil(totalItems/perPage);
        for (page = 1; page <= totalPage; page++) {
            int limit = (int) ((page - 1) * perPage);

            List<LevelsResponse> tempLevels;
            tempLevels = databaseObject.getLevelsDB().getAll().subList(limit, (int) ((page == totalPage) ? totalItems : perPage * page));
            LevelAdapter levelAdapter = new LevelAdapter(getApplicationContext(), tempLevels);
            viewPagerAdapter.addFragment(GridFragment.newInstance(levelAdapter, page));
        }
        levelPager.setCurrentItem(pageNumber-1);
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
        if(SettingsActivity.getMusicStatus(this))
        {
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