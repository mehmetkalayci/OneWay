package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.yazilimciakli.oneway.Controls.ExpandableListviewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 9.08.2017.
 */

public class InfoActivity extends Activity {

    public List<String> list_parent;
    public ExpandableListviewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> gs_list;
    public List<String> fb_list;
    public List<String> tt_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        MainActivity.isBack = false;

        expandlist_view = (ExpandableListView) findViewById(R.id.expand_listview);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // Adapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new ExpandableListviewAdapter(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);
    }

    public void Hazırla() {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add(getResources().getString(R.string.howIsPlay));
        list_parent.add(getResources().getString(R.string.rules));
        list_parent.add(getResources().getString(R.string.producer));

        gs_list = new ArrayList<String>();
        gs_list.add(getResources().getString(R.string.l1));

        fb_list = new ArrayList<String>();
        fb_list.add(getResources().getString(R.string.r1));
        fb_list.add(getResources().getString(R.string.r2));
        fb_list.add(getResources().getString(R.string.r3));
        fb_list.add(getResources().getString(R.string.r4));
        fb_list.add(getResources().getString(R.string.r5));

        tt_list = new ArrayList<String>();
        tt_list.add(getResources().getString(R.string.p1));

        list_child.put(list_parent.get(0), gs_list);
        list_child.put(list_parent.get(1), fb_list);
        list_child.put(list_parent.get(2), tt_list);

    }


    @Override
    public void onBackPressed() {
        MainActivity.isBack = true;
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SettingsActivity.getMusicStatus(this)) {
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