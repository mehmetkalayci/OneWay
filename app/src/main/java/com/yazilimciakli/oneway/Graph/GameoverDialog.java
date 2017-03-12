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

/**
 * Created by Admin on 11.03.2017.
 */

public class GameoverDialog extends  Dialog implements android.view.View.OnClickListener
{
    public Context context;
    TextView gameover_title,gameover_message;
    ImageButton gameover_repeat;
    Typeface typeface;
    public GameoverDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");
        setContentView(R.layout.game_over);

        gameover_title=(TextView)findViewById(R.id.gameover_title);
        gameover_message=(TextView)findViewById(R.id.gameover_message);
        gameover_repeat=(ImageButton) findViewById(R.id.gameover_repeat);

        gameover_title.setTypeface(typeface);
        gameover_message.setTypeface(typeface);

        gameover_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
