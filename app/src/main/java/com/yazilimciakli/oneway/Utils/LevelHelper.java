package com.yazilimciakli.oneway.Utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.R;

import java.util.Arrays;
import java.util.List;

public class LevelHelper {

    List<String> levelList;
    Gson gson = new Gson();

    public LevelHelper(Context context) {
        levelList = Arrays.asList(context.getResources().getStringArray(R.array.levels));
    }

    public Level getLevel(int levelId) {
        return gson.fromJson(levelList.get(0), Level.class);
    }

    public int getLevelSize() {
        return levelList.size();
    }
}