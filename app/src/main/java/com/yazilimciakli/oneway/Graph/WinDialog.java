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

public class WinDialog extends  Dialog implements android.view.View.OnClickListener
{
        public Context context;
        String gettitle,getremaining_time,getscore;
        TextView title,top_message,bottom_message,win_title,score,remaining_time;
        ImageButton repeat_button,next_button;
        Typeface typeface;
        public WinDialog(Context context, String title, String remaining_time, String score) {
            super(context);
            this.context = context;
            this.gettitle=title;
            this.getremaining_time=remaining_time;
            this.getscore=score;
        }


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");
            setContentView(R.layout.win_game);

            title=(TextView)findViewById(R.id.level_title);
            remaining_time=(TextView)findViewById(R.id.remaining_time);
            top_message=(TextView)findViewById(R.id.top_message);
            bottom_message=(TextView)findViewById(R.id.bottom_message);
            win_title=(TextView)findViewById(R.id.win_title);
            score=(TextView)findViewById(R.id.score);

            title.setTypeface(typeface);
            remaining_time.setTypeface(typeface);
            top_message.setTypeface(typeface);
            bottom_message.setTypeface(typeface);
            win_title.setTypeface(typeface);
            score.setTypeface(typeface);

            title.setText(gettitle);
            remaining_time.setText(String.format(getContext().getResources().getString(R.string.remaining_time), getremaining_time));
            score.setText(getscore);

        }

        @Override
        public void onClick(View v) {

        }
    }
