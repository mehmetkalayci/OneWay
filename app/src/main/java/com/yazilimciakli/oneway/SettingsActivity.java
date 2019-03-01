package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.yazilimciakli.oneway.Utils.LanguageHelper;
import com.yazilimciakli.oneway.Utils.SharedPreferenceHelper;

import java.util.Locale;

public class SettingsActivity extends Activity {

    public static final String SETTING_VIBRATION = "vibrationStatus";
    public static final String SETTING_MUSIC = "musicStatus";
    public static final String SETTING_LANGUAGE = "languageStatus";


    CheckBox chkVibrationStatus, chkMusicStatus;
    RadioGroup rdgLanguages;
    private AdView mAdView;

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

        //reklam_yukle();
        MainActivity.isBack = false;

        chkVibrationStatus = (CheckBox) findViewById(R.id.chkVibrationStatus);
        chkMusicStatus = (CheckBox) findViewById(R.id.chkMusicStatus);
        rdgLanguages = (RadioGroup) findViewById(R.id.rdgLanguages);

        /*     Titreşim     */
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
                }
            }
        });
        /******Titreşim******/

        /*     Müzik    */
        final boolean musicStatus = SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, true);
        chkMusicStatus.setChecked(musicStatus);

        chkMusicStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                boolean lastStatus = changeMusicStatus(isChecked);
                if (lastStatus == isChecked) {
                    if (isChecked) {
                        boolean musicStatus = MainActivity.musicHelper.changeStatus();
                        chkMusicStatus.setChecked(musicStatus);
                    } else {
                        boolean musicStatus = MainActivity.musicHelper.changeStatus();
                    }
                }
            }
        });
        /******Müzik*******/

        /*     Dil Ayarları     */

        String language = SharedPreferenceHelper.getSharedPreferenceString(SettingsActivity.this, SETTING_LANGUAGE, "en");
        switch (language) {
            case "tr":
                rdgLanguages.check(R.id.rdTr);
                break;
            case "en":
                rdgLanguages.check(R.id.rdEn);
                break;
            case "es":
                rdgLanguages.check(R.id.rdEs);
                break;
            case "de":
                rdgLanguages.check(R.id.rdDe);
                break;
        }


        rdgLanguages.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                String language = "en";
                switch (checkedId) {
                    case R.id.rdTr:
                        language = "tr";
                        break;
                    case R.id.rdEn:
                        language = "en";
                        break;
                    case R.id.rdEs:
                        language = "es";
                        break;
                    case R.id.rdDe:
                        language = "de";
                        break;
                }
                LanguageHelper.changeLocale(SettingsActivity.this, new Locale(language));
                changeLanguageStatus(language);

                startActivity(new Intent(SettingsActivity.this, SettingsActivity.class));
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                finish();
            }
        });
        /*****Dil Ayarları******/
    }

    private void reklam_yukle() {
        mAdView = new AdView(this);
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId(getString(R.string.reklam_kimligi));

        LinearLayout layout = (LinearLayout) findViewById(R.id.reklam);
        layout.addView(mAdView);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    boolean changeVibrationStatus(boolean status) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, status);
        return SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_VIBRATION, true);
    }

    boolean changeMusicStatus(boolean status) {
        SharedPreferenceHelper.setSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, status);
        return SharedPreferenceHelper.getSharedPreferenceBoolean(SettingsActivity.this, SETTING_MUSIC, true);
    }

    String changeLanguageStatus(String locale) {
        SharedPreferenceHelper.setSharedPreferenceString(SettingsActivity.this, SETTING_LANGUAGE, locale);
        return SharedPreferenceHelper.getSharedPreferenceString(SettingsActivity.this, SETTING_LANGUAGE, "en");
    }

    @Override
    public void onBackPressed() {
        MainActivity.isBack = true;
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
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
