package com.yazilimciakli.oneway.Graph;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yazilimciakli.oneway.R;


public class GameOverDialog extends Dialog {

    TextView lblTitle, lblMessage;
    ImageButton btnRepeat;
    Typeface typeface;

    public GameOverDialog(Context context) {
        super(context);
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
    }
}
