package com.yazilimciakli.oneway.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yazilimciakli.oneway.R;

/**
 * Created by Admin on 24.03.2017.
 */

public class ExitDialog extends Dialog {

    Context context;
    TextView exitTitle, exitMessage;
    Button yesBtn, noBtn;
    Typeface typeface;

    public ExitDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_exit);

        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        exitTitle = (TextView) findViewById(R.id.exitTitle);
        exitMessage = (TextView) findViewById(R.id.exitMessage);
        yesBtn = (Button) findViewById(R.id.yesBtn);
        noBtn = (Button) findViewById(R.id.noBtn);

        exitTitle.setTypeface(typeface);
        exitMessage.setTypeface(typeface);
        yesBtn.setTypeface(typeface);
        noBtn.setTypeface(typeface);

        final Activity activity = (Activity) context;

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }
}
