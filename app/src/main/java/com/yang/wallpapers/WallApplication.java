package com.yang.wallpapers;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.ScreenUtil;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class WallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(getApplicationContext());

        NetWorkUtils.init();
        ScreenUtil.init(this);
    }
}
