package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.yazilimciakli.oneway.R;


public class MusicHelper {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;


    public MusicHelper() {
    }

    public enum MUSICS {MainMusic, GameMusic}

    public void prepareMusicPlayer(Context context, MUSICS music) {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }

        switch (music) {
            case GameMusic:
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.game_forest);
                break;
            case MainMusic:
                mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.puzzle_theme);
                break;
        }
        mediaPlayer.setVolume(.25f, .25f);
        mediaPlayer.setLooping(true);
    }

    public void playMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}