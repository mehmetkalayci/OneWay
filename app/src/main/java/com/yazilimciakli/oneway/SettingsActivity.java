package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.yazilimciakli.oneway.Utils.MusicManager;
import com.yazilimciakli.oneway.Utils.SharedPreferenceHelper;

public class SettingsActivity extends Activity {

    Boolean continueMusic = true;


    private static final String SETTING_VIBRATION = "vibrationStatus";
    CheckBox chkVibrationStatus;

    public static boolean getVibrationStatus(Context context) {
        return SharedPreferenceHelper.getSharedPreferenceBoolean(context, SETTING_VIBRATION, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        chkVibrationStatus = (CheckBox) findViewById(R.id.chkVibrationStatus);

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
                    }
                    Toast.makeText(SettingsActivity.this, "Kaydedildi!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    boolean changeVibrationStatus(boolean status) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, status);
        return SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, true);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (!continueMusic) {
            MusicManager.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        continueMusic = false;
        MusicManager.start(this, MusicManager.MUSIC_MENU);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.continueMusic = true;

        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
}
