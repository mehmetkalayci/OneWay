package com.yazilimciakli.oneway;

import android.app.Application;
import android.content.Context;

import com.yazilimciakli.oneway.Utils.MusicHelper;

public class MyApplication extends Application {

    public static MusicHelper musicHelper = new MusicHelper();


    private static Context mContext;

    Foreground.Listener myListener = new Foreground.Listener() {
        public void onBecameForeground() {
            musicHelper.playMusic();
        }

        public void onBecameBackground() {
            musicHelper.pauseMusic();;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        Foreground.get(this).addListener(myListener);
    }

    public static Context getContext() {
        return mContext;
    }
}