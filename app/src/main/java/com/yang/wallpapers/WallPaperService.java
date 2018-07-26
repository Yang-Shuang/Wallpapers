package com.yang.wallpapers;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yang.wallpapers.utils.LogUtil;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class WallPaperService extends Service{

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
