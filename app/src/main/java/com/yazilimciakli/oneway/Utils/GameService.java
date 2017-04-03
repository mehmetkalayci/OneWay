package com.yazilimciakli.oneway.Utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.yazilimciakli.oneway.R;

public class GameService {

    public static MediaPlayer musicPlayer;
    public static boolean isMusicPlay = true;

    public static void initGameService(Context context){
        musicPlayer = MediaPlayer.create(context, R.raw.jungle);
        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.setLooping(true);
    }

}