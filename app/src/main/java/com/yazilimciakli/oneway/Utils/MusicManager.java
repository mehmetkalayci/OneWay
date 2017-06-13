package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.yazilimciakli.oneway.R;

public class MusicManager {

    MediaPlayer mp = new MediaPlayer();
    Context context;

    public MusicManager(Context context) {
        this.context = context;
    }

    public enum MUSICS {MainMusic, GameMusic}

    public void Set(MUSICS music) {
        if (mp != null) {
            mp.reset();
            mp.release();
        }

        switch (music) {
            case MainMusic:
                mp = MediaPlayer.create(context, R.raw.puzzle_theme);
                break;
            case GameMusic:
                mp = MediaPlayer.create(context, R.raw.game_forest);
                break;
        }
    }

    public void Pause() {
        mp.pause();
    }

    public void Play() {
        mp.start();
    }
}