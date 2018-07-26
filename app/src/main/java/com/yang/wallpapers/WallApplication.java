package com.yang.wallpapers;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;
import com.orhanobut.hawk.Parser;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.ScreenUtil;
import com.yang.wallpapers.utils.ServiceUtil;

import java.lang.reflect.Type;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class WallApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this);
        LogUtil.i("WallApplication onCreate");
        Hawk.init(this).build();
        Fresco.initialize(getApplicationContext());

        NetWorkUtils.init();
        ScreenUtil.init(this);

        ServiceUtil.startService(this);
    }
}
