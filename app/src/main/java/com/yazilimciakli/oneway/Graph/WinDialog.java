package com.yazilimciakli.oneway.Graph;

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
import com.yazilimciakli.oneway.R;

public class WinDialog extends Dialog {

    Context cntx;
    String title, remainingTime, score;
    TextView lblTitle, lblTopMessage, lblBottomMessage, lblWinTitle, lblScore, lblRemainingTime;
    ImageButton repeat_button, next_button;
    Typeface typeface;
    int levelID;

    public WinDialog(Context context, String title, String remainingTime, String score,int levelID) {
        super(context);
        this.cntx=context;
        this.title = title;
        this.remainingTime = remainingTime;
        this.score = score;
        this.levelID=levelID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.win_game);

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        lblTitle = (TextView) findViewById(R.id.level_title);
        lblRemainingTime = (TextView) findViewById(R.id.remaining_time);
        lblTopMessage = (TextView) findViewById(R.id.top_message);
        lblBottomMessage = (TextView) findViewById(R.id.bottom_message);
        lblWinTitle = (TextView) findViewById(R.id.win_title);
        lblScore = (TextView) findViewById(R.id.score);

        lblTitle.setTypeface(typeface);
        lblRemainingTime.setTypeface(typeface);
        lblTopMessage.setTypeface(typeface);
        lblBottomMessage.setTypeface(typeface);
        lblWinTitle.setTypeface(typeface);
        lblScore.setTypeface(typeface);

        lblTitle.setText(title);
        lblRemainingTime.setText(String.format(getContext().getResources().getString(R.string.remaining_time), remainingTime));
        lblScore.setText(score);

        repeat_button=(ImageButton) findViewById(R.id.repeat_button);
        next_button=(ImageButton)findViewById(R.id.next_button);

        repeat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLevelIntent = new Intent();

                openLevelIntent.setClass(cntx, GameActivity.class);
                openLevelIntent.putExtra("levelId", levelID-1);
                cntx.startActivity(openLevelIntent);

                Activity activity = (Activity) cntx;
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLevelIntent = new Intent();

                openLevelIntent.setClass(cntx, GameActivity.class);
                openLevelIntent.putExtra("levelId", levelID);
                cntx.startActivity(openLevelIntent);

                Activity activity = (Activity) cntx;
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });
    }
}
