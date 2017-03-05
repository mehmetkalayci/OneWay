package com.yazilimciakli.oneway;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.yazilimciakli.oneway.Controls.LevelAdapter;
import com.yazilimciakli.oneway.Controls.GridFragment;
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
        List<String> page1 = levelHelper.limit(0, 11);
        List<String> page2 = levelHelper.limit(11, 22);


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(GridFragment.newInstance(page1));
        viewPagerAdapter.addFragment(GridFragment.newInstance(levelItems));

        levelPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(levelPager);
    }
}
