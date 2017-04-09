package com.yazilimciakli.oneway.Utils;


import android.content.Context;
import android.media.MediaPlayer;

public class MusicService {

    private static MusicService ourInstance = new MusicService();
    private static MediaPlayer mediaPlayer = new MediaPlayer();

    private MusicService() {
    }

    public static MusicService getInstance() {
        return ourInstance;
    }

    public void initialize(Context context, int music) {
        mediaPlayer = MediaPlayer.create(context, music);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100, 100);
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}