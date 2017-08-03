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
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yazilimciakli.oneway.Utils.FileHelper;

import java.io.IOException;

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

        // İnterntten level dosyasını güncelle

        fileHelper = new FileHelper(LoadingActivity.this);

        // Volley için istek oluşturuldu
        StringRequest request = new StringRequest(Request.Method.GET, levelUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    fileHelper.write(response);
                } catch (IOException e) {
                    Log.d("ERROR: IOException -> ", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR: VolleyError -> ", error.getMessage());
            }
        });

        // Volley RequestQueue oluşturuldu.
        RequestQueue requestQueue = Volley.newRequestQueue(LoadingActivity.this);
        // İstek RequestQueue 'ya eklendi.
        requestQueue.add(request);

        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);


        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);

        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 2500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
