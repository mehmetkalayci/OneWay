package com.yazilimciakli.oneway.Graph;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yazilimciakli.oneway.R;

public class WinDialog extends Dialog {

    String title, remainingTime, score;
    TextView lblTitle, lblTopMessage, lblBottomMessage, lblWinTitle, lblScore, lblRemainingTime;
    ImageButton repeat_button, next_button;
    Typeface typeface;

    public WinDialog(Context context, String title, String remainingTime, String score) {
        super(context);
        this.title = title;
        this.remainingTime = remainingTime;
        this.score = score;
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
    }
}
