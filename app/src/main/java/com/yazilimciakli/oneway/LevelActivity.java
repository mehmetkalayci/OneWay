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
    TextView points;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        MainActivity.isBack=false;

        lblSelectLevel = (TextView) findViewById(R.id.lblSelectLevel);
        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Atma.ttf");
        lblSelectLevel.setTypeface(typeface);


        levelPager = (ViewPager) findViewById(R.id.levelPager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        levelPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(levelPager);

        points=(TextView) findViewById(R.id.totalPoints);

        LevelHelper levelHelper = new LevelHelper(this);

        DatabaseHandler dbHandler;
        dbHandler = new DatabaseHandler(this);
        points.setText(dbHandler.getPoints());

        int page;
        double perPage = 9;
        double totalItems = levelHelper.getLevelSize();
        double totalPage = Math.ceil(totalItems / perPage);

        for (page = 1; page <= totalPage; page++) {
            int limit = (int) ((page - 1) * perPage);

            List<Level> tempLevels = new ArrayList<>();
            tempLevels = levelHelper.getLevels().subList(limit, (int) ((page == totalPage) ? totalItems : perPage * page));

            LevelAdapter levelAdapter = new LevelAdapter(this, tempLevels);
            viewPagerAdapter.addFragment(GridFragment.newInstance(levelAdapter,page));
        }

        if(!MainActivity.musicHelper.isPlaying())
            MainActivity.musicHelper.prepareMusicPlayer(this, MusicHelper.MUSICS.MainMusic);
    }

    @Override
    public void onBackPressed() {
        MainActivity.isBack=true;
        MainActivity.musicHelper.setPlaying(true);
        startActivity(new Intent(LevelActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPagerAdapter.notifyDataSetChanged();
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