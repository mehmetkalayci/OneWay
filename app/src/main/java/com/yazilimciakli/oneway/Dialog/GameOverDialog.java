package com.yazilimciakli.oneway.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yazilimciakli.oneway.Database.CoinsHandler;
import com.yazilimciakli.oneway.Database.HealthHandler;
import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.MainActivity;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.MusicHelper;

import java.util.Date;


public class GameOverDialog extends Dialog {

    Context context;
    TextView lblTitle, lblMessage;
    ImageButton btnRepeat;
    Typeface typeface;
    int levelID;

    public GameOverDialog(Context context, int levelID) {
        super(context);
        this.context = context;
        this.levelID = levelID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_game_over);

        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        lblTitle = (TextView) findViewById(R.id.lblGameOverTitle);
        lblMessage = (TextView) findViewById(R.id.lblGameOverMessage);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);

        lblTitle.setTypeface(typeface);
        lblMessage.setTypeface(typeface);

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLevelIntent = new Intent();
                GameActivity.between = false;

                HealthHandler healthHandler = new HealthHandler(getContext());
                int health = Integer.parseInt(healthHandler.getHealth(1).get("health"));
                Date now = new Date();
                healthHandler.setHealt(health - 1, now.getTime(), 1);

                if (Integer.parseInt(healthHandler.getHealth(1).get("health")) == 0) {

                    healthHandler.setHealt(0, (now.getTime() + 10 * 60 * 1000), 1);
                    CoinsHandler coinsHandler=new CoinsHandler(getContext());
                    MainActivity.musicHelper.prepareMusicPlayer(getContext(), MusicHelper.MUSICS.MainMusic);
                    int a=Integer.parseInt(coinsHandler.getCoins(1).get("totalCoefficient"))+1;
                     coinsHandler.setCount(a,1);
                    openLevelIntent.setClass(getContext(), LevelActivity.class);

                } else {
                    openLevelIntent.setClass(context, GameActivity.class);
                }

                openLevelIntent.putExtra("levelId", levelID - 1);
                context.startActivity(openLevelIntent);


                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                activity.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dismiss();

        Intent openLevelIntent = new Intent();
        MainActivity.isBack = true;
        MainActivity.musicHelper.prepareMusicPlayer(getContext(), MusicHelper.MUSICS.MainMusic);

        HealthHandler healthHandler = new HealthHandler(getContext());
        int health = Integer.parseInt(healthHandler.getHealth(1).get("health"));
        Date now = new Date();
        healthHandler.setHealt(health - 1, now.getTime(), 1);

        if (Integer.parseInt(healthHandler.getHealth(1).get("health")) == 0) {
            healthHandler.setHealt(0, (now.getTime() + 10 * 60 * 1000), 1);
            openLevelIntent.setClass(getContext(), LevelActivity.class);
        } else {
            openLevelIntent.setClass(context, LevelActivity.class);
        }

        context.startActivity(openLevelIntent);

        Activity activity = (Activity) context;
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        activity.finish();
    }
}