package com.yazilimciakli.oneway.Level;

import android.content.Context;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.R;

import java.util.Arrays;
import java.util.List;


public class GetLevels {

    Context context;
    Gson gson;
    List<String> levelList;
    public GetLevels(Context con) {
        super();
        context=con;
        gson = new Gson();
        levelList = Arrays.asList(context.getResources().getStringArray(R.array.levels));
    }

    public Level getLevels(int Locations)
    {
        String currentLevel = levelList.get(Locations).toString();
        Level level = gson.fromJson(currentLevel, Level.class);
        return level;
    }
    public Integer itemSize()
    {
        return levelList.size();
    }
}
