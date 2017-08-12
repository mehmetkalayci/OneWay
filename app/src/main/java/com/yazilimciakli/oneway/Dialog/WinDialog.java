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

import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.MainActivity;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.MusicHelper;

public class WinDialog extends Dialog {

    Context context;
    String title, remainingTime, score;
    TextView lblTitle, lblTopMessage, lblBottomMessage, lblWinTitle, lblScore, lblRemainingTime;
    ImageButton btnRestart, btnNextLevel;
    Typeface typeface;
    int levelID;


    public WinDialog(Context context, String title, String remainingTime, String score, int levelID) {
        super(context);
        this.context = context;
        this.title = title;
        this.remainingTime = remainingTime;
        this.score = score;
        this.levelID = levelID;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_win_game);

        if(GameActivity.hideAd)
        {
            GameActivity.mInterstitialAd.show();
        }
        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        lblTitle = (TextView) findViewById(R.id.lblLevelTitle);
        lblRemainingTime = (TextView) findViewById(R.id.lblRemainingTime);
        lblTopMessage = (TextView) findViewById(R.id.lblMessageTitle);
        lblBottomMessage = (TextView) findViewById(R.id.lblMessage);
        lblWinTitle = (TextView) findViewById(R.id.lblWinTitle);
        lblScore = (TextView) findViewById(R.id.lblScore);

        lblTitle.setTypeface(typeface);
        lblRemainingTime.setTypeface(typeface);
        lblTopMessage.setTypeface(typeface);
        lblBottomMessage.setTypeface(typeface);
        lblWinTitle.setTypeface(typeface);
        lblScore.setTypeface(typeface);

        lblTitle.setText(title);
        lblRemainingTime.setText(String.format(getContext().getResources().getString(R.string.remaining_time), remainingTime));
        lblScore.setText(score);

        btnRestart = (ImageButton) findViewById(R.id.btnRestart);
        btnNextLevel = (ImageButton) findViewById(R.id.btnNextLevel);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                GameActivity.between=false;
                Intent openLevelIntent = new Intent();
                openLevelIntent.setClass(context, GameActivity.class);
                openLevelIntent.putExtra("levelId", levelID - 1);
                context.startActivity(openLevelIntent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                activity.finish();
            }
        });

        btnNextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                GameActivity.between=false;
                Intent openLevelIntent = new Intent();

                openLevelIntent.setClass(context, GameActivity.class);
                openLevelIntent.putExtra("levelId", levelID);
                context.startActivity(openLevelIntent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                activity.finish();

            }
        });
    }



    @Override
    public void onBackPressed() {
        dismiss();
        MainActivity.isBack=true;
        MainActivity.musicHelper.prepareMusicPlayer(getContext(), MusicHelper.MUSICS.MainMusic);
        Intent openLevelIntent = new Intent();
        openLevelIntent.setClass(getContext(), LevelActivity.class);
        context.startActivity(openLevelIntent);

        Activity activity = (Activity) context;
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        activity.finish();
    }

}
