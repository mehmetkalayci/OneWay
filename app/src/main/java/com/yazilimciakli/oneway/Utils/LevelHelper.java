package com.yazilimciakli.oneway.Utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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