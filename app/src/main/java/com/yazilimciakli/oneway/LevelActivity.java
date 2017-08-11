package com.yazilimciakli.oneway;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yazilimciakli.oneway.Controls.GridFragment;
import com.yazilimciakli.oneway.Controls.LevelAdapter;
import com.yazilimciakli.oneway.Database.DatabaseHandler;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.Utils.LevelHelper;
import com.yazilimciakli.oneway.Utils.MusicHelper;
import com.yazilimciakli.oneway.Utils.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class LevelActivity extends AppCompatActivity {

    Typeface typeface;
    TextView lblSelectLevel;

    CircleIndicator indicator;
    ViewPager levelPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView lblPoints, lblTotalPoints, lblGo;
    int pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        MainActivity.isBack = false;

        lblSelectLevel = (TextView) findViewById(R.id.lblSelectLevel);
        lblPoints = (TextView) findViewById(R.id.lblPoints);
        lblTotalPoints = (TextView) findViewById(R.id.lblTotalPoints);
        lblGo = (TextView) findViewById(R.id.lblGo);

        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Atma.ttf");
        lblSelectLevel.setTypeface(typeface);
        lblPoints.setTypeface(typeface);
        lblTotalPoints.setTypeface(typeface);
        lblGo.setTypeface(typeface);

        levelPager = (ViewPager) findViewById(R.id.levelPager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        levelPager.setAdapter(viewPagerAdapter);

        LevelHelper levelHelper = new LevelHelper(this);

        DatabaseHandler dbHandler = new DatabaseHandler(this);
        lblTotalPoints.setText(dbHandler.getPoints());



        int page;
        double perPage = 9;
        double totalItems = levelHelper.getLevelSize();
        double totalPage = Math.ceil(totalItems / perPage);
        DatabaseHandler databaseHandler=new DatabaseHandler(this);
        pageNumber= (int) Math.ceil(databaseHandler.getAllLevels().size()/perPage);
        for (page = 1; page <= totalPage; page++) {
            int limit = (int) ((page - 1) * perPage);

            List<Level> tempLevels = new ArrayList<>();
            tempLevels = levelHelper.getLevels().subList(limit, (int) ((page == totalPage) ? totalItems : perPage * page));

            LevelAdapter levelAdapter = new LevelAdapter(this, tempLevels);
            viewPagerAdapter.addFragment(GridFragment.newInstance(levelAdapter, page));
        }
        levelPager.setCurrentItem(pageNumber);
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