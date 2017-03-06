package com.yazilimciakli.oneway;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.Controls.GridFragment;
import com.yazilimciakli.oneway.Controls.LevelAdapter;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.Utils.LevelHelper;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        lblSelectLevel = (TextView) findViewById(R.id.lblSelectLevel);
        typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/Atma.ttf");
        lblSelectLevel.setTypeface(typeface);

        levelPager = (ViewPager) findViewById(R.id.levelPager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);

        LevelHelper levelHelper = new LevelHelper(this);

        int objectCount = 9;
        double pageCount = Math.ceil((double) levelHelper.getLevelSize() / (double) objectCount);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        int begin = 0;
        int next = objectCount;
        for (int a = 0; a < pageCount; a++) {

            /*Bir sayfa için gerekli başlangıç*/

            List<String> stringList = levelHelper.limit(begin, next);
            List<Level> levelList = new ArrayList<>();
            Gson gson = new Gson();

            for (int i = 0; i < stringList.size(); i++) {
                levelList.add(i, gson.fromJson(stringList.get(i), Level.class));
            }

            LevelAdapter page = new LevelAdapter(this, levelList);
            viewPagerAdapter.addFragment(GridFragment.newInstance(page));

            /*Bir sayfa için gerekli Bitiş*/

            begin += objectCount;

            if (levelHelper.getLevelSize() - (objectCount * (a + 1)) < 9) {

                next = levelHelper.getLevelSize();

            } else {

                next = (objectCount * (a + 1) + objectCount);

            }
        }

        levelPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(levelPager);
    }
}
