package com.yazilimciakli.oneway.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yazilimciakli.oneway.R;

public class ExitDialog extends Dialog {

    Context context;
    TextView exitTitle, exitMessage;
    Button btnYes, btnNo;
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

        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;


        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Atma.ttf");

        exitTitle = (TextView) findViewById(R.id.lblExitTitle);
        exitMessage = (TextView) findViewById(R.id.exitMessage);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnNo = (Button) findViewById(R.id.btnNo);

        exitTitle.setTypeface(typeface);
        exitMessage.setTypeface(typeface);
        btnYes.setTypeface(typeface);
        btnNo.setTypeface(typeface);

        final Activity activity = (Activity) context;

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                activity.finish();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        dismiss();
        return super.onTouchEvent(event);
    }
}
