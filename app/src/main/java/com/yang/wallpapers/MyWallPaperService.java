package com.yang.wallpapers;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yang.wallpapers.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class MyWallPaperService extends Service {

    public static MyWallPaperService instance;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this);
        instance = this;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LogUtil.e("Service----正在运行中");
                }
            }, 500, 10000);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("Service----onDestroy");
    }
}
