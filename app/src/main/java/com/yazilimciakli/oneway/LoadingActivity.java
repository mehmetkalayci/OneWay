package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yazilimciakli.oneway.Utils.FileHelper;
import com.yazilimciakli.oneway.Utils.LanguageHelper;
import com.yazilimciakli.oneway.Utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.Locale;

public class LoadingActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    Thread splashTread;
    FileHelper fileHelper;
    String levelUrl = "http://yazilimciakli.com/demo/OneTouchLevel/creator/level.xml";

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        // Varsayılan dili tespit et
        Locale current = getResources().getConfiguration().locale;
        String language = SharedPreferenceHelper.getSharedPreferenceString(LoadingActivity.this, SettingsActivity.SETTING_LANGUAGE, current.getLanguage());
        LanguageHelper.changeLocale(LoadingActivity.this, new Locale(language));


        // İnternetten level dosyasını güncelle
        fileHelper = new FileHelper(LoadingActivity.this);

        // Volley için istek oluşturuldu
        StringRequest request = new StringRequest(Request.Method.GET, levelUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    fileHelper.write(response);
                } catch (IOException e) {
                    String err = (e.getMessage()==null)?"SD Card failed":e.getMessage();
                    Log.d("ERROR: IOException -> ", err);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d("ERROR: VolleyError -> ", error.getMessage());
            }
        });

        // Volley RequestQueue oluşturuldu.
        RequestQueue requestQueue = Volley.newRequestQueue(LoadingActivity.this);
        // İstek RequestQueue 'ya eklendi.
        requestQueue.add(request);

        StartAnimations();
    }

    private void StartAnimations() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();
        ImageView imgLogo = (ImageView) findViewById(R.id.splash);
        imgLogo.clearAnimation();
        imgLogo.startAnimation(animation);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 1500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoadingActivity.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    LoadingActivity.this.finish();
                }
            }
        };
        splashTread.start();
    }

}