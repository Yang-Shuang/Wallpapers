package com.yang.wallpapers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yang.wallpapers.utils.AppConfigConst;

public class LancherActivity extends AppCompatActivity {

    private boolean hasDelay = false;
    private boolean hasCheckApi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancher);
        initData();
    }

    private void initData() {
        delay();
        checkApi();
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hasDelay = true;
                toHome();
            }
        }, 2000);
    }

    private void checkApi() {
        hasCheckApi = true;
    }

    private void toHome() {
        if (hasDelay && hasCheckApi) {
            Class activity = AppConfigConst.HOST_TYPE == 0 ? BingImageActivity.class : ADeskImageActivity.class;
            Intent intent = new Intent(this, activity);
            startActivity(intent);
            finish();
        }
    }
}
