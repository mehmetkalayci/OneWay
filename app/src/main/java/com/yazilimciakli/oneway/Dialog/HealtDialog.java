package com.yazilimciakli.oneway.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.Object.DatabaseObject;
import com.yazilimciakli.oneway.R;

import java.util.Date;


public class HealtDialog extends Dialog {

    ImageButton showAd;
    Button coinsAd;
    TextView message;
    DatabaseObject coinsHandler;
    public HealtDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_nonhealt);
        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;

        showAd=(ImageButton) findViewById(R.id.watchAd);
        coinsAd=(Button) findViewById(R.id.coinAd);
        message=(TextView) findViewById(R.id.lblMessage);
        coinsHandler=coinsHandler.newInstance(getContext());
        final int hesap;
        final int katsayi;
        katsayi=20*coinsHandler.getProfileDB().get(1).getTotalCoefficient();
        message.setText(getContext().getResources().getString(R.string.nonhealt));
        coinsAd.setText(""+katsayi);
        hesap=coinsHandler.getProfileDB().get(1).getTotalCoins()-katsayi;
        coinsAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hesap<=0)
                {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.you_have_no_money), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    coinsHandler.getProfileDB().setCoins(1,hesap);
                    final Date now = new Date();
                    coinsHandler.getProfileDB().setHealt(1,5,now.getTime());
                    LevelActivity.timer.setText(getContext().getResources().getString(R.string.fullHealth));
                    LevelActivity.lblhealt.setText(String.valueOf(coinsHandler.getProfileDB().get(1).getHealt()));
                    LevelActivity.lblTotalPoints.setText(coinsHandler.getProfileDB().get(1).getTotalCoins());
                    LevelActivity.waitTimer.cancel();
                    dismiss();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        dismiss();
    }
}
