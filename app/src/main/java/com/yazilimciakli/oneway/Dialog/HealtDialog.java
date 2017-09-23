package com.yazilimciakli.oneway.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.yazilimciakli.oneway.Database.HealthHandler;
import com.yazilimciakli.oneway.LevelActivity;
import com.yazilimciakli.oneway.R;

import java.util.Date;


public class HealtDialog extends Dialog implements RewardedVideoAdListener {
    private RewardedVideoAd mAd;

    public HealtDialog(Context context) {
        super(context);
    }

    ImageButton watchAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_nonhealt);
        watchAds = (ImageButton) findViewById(R.id.watchAd);
        this.getWindow().getAttributes().windowAnimations = R.style.popwindow_anim_style;
        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544/5224354917");
        mAd = MobileAds.getRewardedVideoAdInstance(getContext());
        mAd.setRewardedVideoAdListener(this);
        watchAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVideoAd();
            }
        });
        loadRewardVideoAd();
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }
    private void loadRewardVideoAd()
    {
        if (!mAd.isLoaded())
        {
            mAd.loadAd("ca-app-pub-3940256099942544/5224354917",new AdRequest.Builder().build());
        }
    }
    public  void startVideoAd()
    {
        if(mAd.isLoaded())
        {
            mAd.show();
        }
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
        HealthHandler healtHandler = new HealthHandler(getContext());
        final Date now = new Date();
        healtHandler.setHealt(5,now.getTime(),1);
        LevelActivity.timer.setText(getContext().getResources().getString(R.string.fullHealth));
        LevelActivity.lblhealt.setText(String.valueOf(healtHandler.getHealt(1).get("health")));
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
