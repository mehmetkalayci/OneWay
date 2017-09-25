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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.yazilimciakli.oneway.Database.CoinsHandler;
import com.yazilimciakli.oneway.Database.HealthHandler;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.R;

import java.util.Date;


public class HealtDialog extends Dialog implements RewardedVideoAdListener {

    private RewardedVideoAd mAd;
    ImageButton showAd;
    Button coinsAd;
    TextView message;
    public HealtDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_nonhealt);
        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;
        mAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mAd.setRewardedVideoAdListener(this);
        showAd=(ImageButton) findViewById(R.id.watchAd);
        coinsAd=(Button) findViewById(R.id.coinAd);
        message=(TextView) findViewById(R.id.lblMessage);
        final CoinsHandler coinsHandler=new CoinsHandler(getContext());
        final int hesap;
        final int katsayi;
        katsayi=20*Integer.parseInt(coinsHandler.getCoins(1).get("totalCoefficient"));
        message.setText(getContext().getResources().getString(R.string.nonhealt));
        coinsAd.setText(""+katsayi);
        hesap=Integer.parseInt(coinsHandler.getCoins(1).get("totalCoin"))-katsayi;
        coinsAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hesap<=0)
                {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.you_have_no_money), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    coinsHandler.setCoins(hesap,1);
                    final Date now = new Date();
                    HealthHandler healtHandler = new HealthHandler(getContext());
                    healtHandler.setHealt(5,now.getTime(),1);
                    LevelActivity.timer.setText(getContext().getResources().getString(R.string.fullHealth));
                    LevelActivity.lblhealt.setText(String.valueOf(healtHandler.getHealth(1).get("health")));
                    LevelActivity.lblTotalPoints.setText(coinsHandler.getCoins(1).get("totalCoin"));
                    LevelActivity.waitTimer.cancel();
                    dismiss();
                }
            }
        });
        showAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAd.isLoaded();
                if (mAd.isLoaded()) {
                    mAd.show();
                }
            }
        });
        loadRewardedVideoAd();
    }
    private void loadRewardedVideoAd() {
        mAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        final Date now = new Date();
        HealthHandler healtHandler = new HealthHandler(getContext());
        healtHandler.setHealt(5,now.getTime(),1);
        LevelActivity.timer.setText(getContext().getResources().getString(R.string.fullHealth));
        LevelActivity.lblhealt.setText(String.valueOf(healtHandler.getHealth(1).get("health")));
        LevelActivity.waitTimer.cancel();
        dismiss();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }
}
