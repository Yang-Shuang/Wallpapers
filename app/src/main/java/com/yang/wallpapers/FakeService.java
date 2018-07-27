package com.yang.wallpapers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yang.wallpapers.utils.LogUtil;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class FakeService extends Service {
    public static FakeService instance = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        fake();
    }

    private void fake() {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.icon_launcher)
                .setContentTitle("Wall")
                .setContentText("Check Service....")
                .setWhen(System.currentTimeMillis());
        startForeground(101, builder.build());
        stopSelf();
    }

    @Override
    public void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
