package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.yazilimciakli.oneway.Utils.SharedPreferenceHelper;

public class SettingsActivity extends Activity {

    private static final String SETTING_VIBRATION = "vibrationStatus";
    private static final String SETTING_MUSIC = "musicStatus";


    CheckBox chkVibrationStatus, chkMusicStatus;

    public static boolean getVibrationStatus(Context context) {
        return SharedPreferenceHelper.getSharedPreferenceBoolean(context, SETTING_VIBRATION, true);
    }

    public static boolean getMusicStatus(Context context) {
        return SharedPreferenceHelper.getSharedPreferenceBoolean(context, SETTING_MUSIC, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MainActivity.isBack=false;

        chkVibrationStatus = (CheckBox) findViewById(R.id.chkVibrationStatus);
        chkMusicStatus = (CheckBox) findViewById(R.id.chkMusicStatus);


        boolean vibrationStatus = SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, true);
        chkVibrationStatus.setChecked(vibrationStatus);

        chkVibrationStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean lastStatus = changeVibrationStatus(isChecked);
                if (lastStatus == isChecked) {
                    if (isChecked) {
                        Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(50);
                        Toast.makeText(SettingsActivity.this, "Tireşim açık!", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(SettingsActivity.this, "Tireşim kapalı!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        final boolean musicStatus = SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, true);
        chkMusicStatus.setChecked(musicStatus);

        chkMusicStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                /*
                boolean lastStatus = changeMusicStatus(isChecked);
                if (lastStatus != musicStatus) {
                   if (isChecked) {
                        boolean musicStatus = MainActivity.musicHelper.changeStatus();
                        chkMusicStatus.setChecked(musicStatus);
                        Toast.makeText(SettingsActivity.this, "Müzik açık!", Toast.LENGTH_SHORT).show();
                    }else{
                       Toast.makeText(SettingsActivity.this, "Müzik kapalı!", Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Toast.makeText(SettingsActivity.this, "Ayarlar kaydedilemedi!", Toast.LENGTH_SHORT).show();
                }
                */

            }
        });
    }


    boolean changeMusicStatus(boolean status) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, status);
        return SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, true);
    }

    boolean changeVibrationStatus(boolean status) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, status);
        return SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, true);
    }

    @Override
    public void onBackPressed() {
        MainActivity.isBack=true;
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.musicHelper.playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!MainActivity.isBack)
        {
            MainActivity.musicHelper.pauseMusic();
        }
    }
}
