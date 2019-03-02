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

import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;
import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.MainActivity;
import com.yazilimciakli.oneway.Object.DatabaseObject;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.MusicHelper;

import java.util.Date;


public class GameOverDialog extends Dialog {

    Context context;
    TextView lblTitle, lblMessage;
    ImageButton btnRepeat;
    Typeface typeface;
    int levelID;
    DatabaseObject dbObject;
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

        lblTitle    = findViewById(R.id.lblGameOverTitle);
        lblMessage  =  findViewById(R.id.lblGameOverMessage);
        btnRepeat   =  findViewById(R.id.btnRepeat);

        dbObject=DatabaseObject.newInstance(getContext());

        lblTitle.setTypeface(typeface);
        lblMessage.setTypeface(typeface);

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLevelIntent = new Intent();
                GameActivity.between = false;

                int health = dbObject.getProfileDB().get(1).getHealt();
                Date now = new Date();
                dbObject.getProfileDB().setHealt(1,health-1,now.getTime());

                if (health == 0) {

                    dbObject.getProfileDB().setHealt(1,0,(now.getTime() + 10 * 60 * 1000));

                    MainActivity.musicHelper.prepareMusicPlayer(getContext(), MusicHelper.MUSICS.MainMusic);

                    int newTotalCoefficient=dbObject.getProfileDB().get(1).getTotalCoefficient()+1;
                    dbObject.getProfileDB().setCount(1,newTotalCoefficient);
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

        int health = dbObject.getProfileDB().get(1).getHealt();
        Date now = new Date();

        dbObject.getProfileDB().setHealt(1,health-1,now.getTime());

        if (health == 0) {
            dbObject.getProfileDB().setHealt(1,0,(now.getTime() + 10 * 60 * 1000));
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