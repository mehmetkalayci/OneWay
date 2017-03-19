package com.yazilimciakli.oneway.Graph;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.R;


class GameOverDialog extends Dialog {

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
    public boolean dispatchKeyEvent(KeyEvent event) {

        Intent openLevelIntent = new Intent();
        openLevelIntent.setClass(getContext(), LevelActivity.class);
        context.startActivity(openLevelIntent);

        Activity activity = (Activity) context;
        activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_over);

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        lblTitle = (TextView) findViewById(R.id.gameover_title);
        lblMessage = (TextView) findViewById(R.id.gameover_message);
        btnRepeat = (ImageButton) findViewById(R.id.gameover_repeat);

        lblTitle.setTypeface(typeface);
        lblMessage.setTypeface(typeface);

        btnRepeat = (ImageButton) findViewById(R.id.gameover_repeat);

        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLevelIntent = new Intent();

                openLevelIntent.setClass(context, GameActivity.class);
                openLevelIntent.putExtra("levelId", levelID - 1);
                context.startActivity(openLevelIntent);

                Activity activity = (Activity) context;
                activity.overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });
    }
}