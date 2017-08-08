package com.yazilimciakli.oneway.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.yazilimciakli.oneway.R;


public class CreditDialog extends Dialog {

    public CreditDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_credit);

        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;
    }



}
