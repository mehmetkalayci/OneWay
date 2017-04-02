package com.yazilimciakli.oneway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }
}
