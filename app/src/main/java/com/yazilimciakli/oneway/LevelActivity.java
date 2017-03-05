package com.yazilimciakli.oneway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yazilimciakli.oneway.Controls.LevelItem;
import com.yazilimciakli.oneway.Utils.LevelHelper;

public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        LevelHelper currentLevel=new LevelHelper(getBaseContext());

        String[]  title=new String[currentLevel.getLevelSize()];
        for(int i=0;i<currentLevel.getLevelSize();i++)
        {
            title[i]=currentLevel.getLevel(i).name;
        }
        LevelItem levelitem=new LevelItem(getBaseContext(),title);
        GridView gridView=(GridView) findViewById(R.id.grid);
        gridView.setAdapter(levelitem);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), GameActivity.class);
                intent.putExtra("level", position);
                startActivity(intent);
            }
        });
    }
}
