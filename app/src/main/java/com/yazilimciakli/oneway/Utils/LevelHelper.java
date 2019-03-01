package com.yazilimciakli.oneway.Utils;

import android.content.Context;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LevelHelper {

    List<String> levelList;
    Gson gson = new Gson();

    FileHelper fileHelper;


    private static final Pattern TAG_REGEX = Pattern.compile("<item>(.+?)</item>");

    private static List<String> getTagValues(final String str) {
        final List<String> tagValues = new ArrayList<String>();
        final Matcher matcher = TAG_REGEX.matcher(str);
        while (matcher.find()) {
            tagValues.add(matcher.group(1));
        }
        return tagValues;
    }

    public LevelHelper(Context context) {
        // internetten indirlen level dosyasını okuyabilirsen oku ve levelList'i ayarla,
        // internetten indirilen level dosyasını okuyamazsan, catch'e düşersen, varsayılan level dosyasını oku.

        fileHelper = new FileHelper(context);
        try {
            String xmlLevelFile = fileHelper.read();
            levelList = getTagValues(xmlLevelFile);
        } catch (IOException e) {
            e.printStackTrace();
            levelList = Arrays.asList(context.getResources().getStringArray(R.array.levels));
        }
    }

    public Level getLevel(int levelId) {
        return gson.fromJson(levelList.get(levelId), Level.class);
    }

    public int getLevelSize() {
        return levelList.size();
    }

    public List<Level> getLevels() {
        List<Level> levels = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < levelList.size(); i++) {
            levels.add(getLevel(index++));
        }
        return levels;
    }


}