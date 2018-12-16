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
import com.yazilimciakli.oneway.Database.LevelsDB;
import com.yazilimciakli.oneway.Database.PointsDB;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PointResponse;
import com.yazilimciakli.oneway.Level.Subpoint;
import com.yazilimciakli.oneway.LevelRequest.GsonRequest;
import com.yazilimciakli.oneway.LevelRequest.Request.LevelRequest;
import com.yazilimciakli.oneway.LevelRequest.Request.Points;
import com.yazilimciakli.oneway.LevelRequest.Request.Subpoints;
import com.yazilimciakli.oneway.Utils.FileHelper;
import com.yazilimciakli.oneway.Utils.LanguageHelper;
import com.yazilimciakli.oneway.Utils.SharedPreferenceHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LoadingActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    private String TAG="Loading_Activity_File";
    private Thread splashTread;
    private FileHelper fileHelper;
    private String levelUrl = "http://31.169.73.195/~yazilimc/demo/OneTouchLevel/creator/level.xml";
    private LevelsDB levelsDB;
    private PointsDB pointsDB;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        levelsDB=new LevelsDB(getApplicationContext());
        pointsDB=new PointsDB(getApplicationContext());
        // Varsayılan dili tespit et
        Locale current = getResources().getConfiguration().locale;
        String language = SharedPreferenceHelper.getSharedPreferenceString(LoadingActivity.this, SettingsActivity.SETTING_LANGUAGE, current.getLanguage());
        LanguageHelper.changeLocale(LoadingActivity.this, new Locale(language));


        // İnternetten levelleri güncelle
        // Volley için istek oluşturuldu
        GsonRequest<LevelRequest> gsonRequest=new GsonRequest<>(levelUrl,LevelRequest.class,null,successListener(),errorListener());
        // Volley RequestQueue oluşturuldu.
        RequestQueue requestQueue = Volley.newRequestQueue(LoadingActivity.this);
        // İstek RequestQueue 'ya eklendi.
        requestQueue.add(gsonRequest);

        StartAnimations();
    }
    private Response.Listener successListener()
    {
        return new Response.Listener<LevelRequest>() {
            @Override
            public void onResponse(LevelRequest response) {
                List<LevelRequest> responseList= Arrays.asList(response);
                List<LevelsResponse> levelResponse=new ArrayList<>();
                List<PointResponse> pointReponse=new ArrayList<>();
                levelsDB.clear();
                pointsDB.clear();
                for (LevelRequest responses:responseList) {
                    levelResponse.add(new LevelsResponse(
                            responses.getTime(),
                            responses.getMoveNumber(),
                            responses.getScore(),
                            Integer.valueOf(responses.getLevelid())));
                    if(responses.getPoints().length>0)
                    {
                        int pointID=1;

                        for(Points pointResponses:response.getPoints())
                        {
                            pointReponse.add(new PointResponse(
                                    Integer.valueOf(responses.getLevelid()),
                                    pointID,
                                    0,
                                    Integer.valueOf(pointResponses.getY()),
                                    Integer.valueOf(pointResponses.getX())
                            ));
                            pointID++;
                            if(pointResponses.getSubpoints().length>0) {
                                for(Subpoints subPointResponse:pointResponses.getSubpoints()) {
                                    pointReponse.add(new PointResponse(
                                            Integer.valueOf(responses.getLevelid()),
                                            pointID,
                                            1,
                                            Integer.valueOf(subPointResponse.getY()),
                                            Integer.valueOf(subPointResponse.getX())
                                    ));
                                }
                            }
                        }
                    }
                }
                levelsDB.create(levelResponse);
                pointsDB.create(pointReponse);
            }
        };
    }
    private Response.ErrorListener errorListener()
    {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+ error.getMessage());
            }
        };
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