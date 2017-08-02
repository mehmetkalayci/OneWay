package com.yazilimciakli.oneway.Utils;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LevelHelper {

    List<String> levelList;
    Gson gson = new Gson();

    FileHelper fileHelper;

    public LevelHelper(Context context) {
        List<String> stringLevels = new ArrayList<>();

        try {
            InputStream inputStream = fileHelper.getInputStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputStream);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("item");

            for (int i = 0; i < nList.getLength(); i++) {
                stringLevels.add(nList.item(i).getNodeValue());
            }

            levelList = stringLevels;

            Log.d("LEVELS", levelList.toString());

        } catch (Exception e) {
            e.printStackTrace();
            // internetten indirilen dosyayı okurken problem oluşursa,
            // varsayılan dosyayı oku
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