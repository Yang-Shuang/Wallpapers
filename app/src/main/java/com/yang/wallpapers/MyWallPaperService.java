package com.yang.wallpapers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.StringUtil;

import java.util.List;


/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class MyWallPaperService extends Service {

    public static MyWallPaperService instance;
    private AlarmManager manager;
    public static final String ACTION_GET_DATA = "ACTION_GET_DATA";
    public static final String ACTION_GET_DATA_FOR_SET = "ACTION_GET_DATA_FOR_SET";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.init(this);
        instance = this;
        LogUtil.e("Service----onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.e("onStartCommand------" + FakeService.instance);
        String action = intent.getAction();
        if (StringUtil.isEmpty(action)) {
//            executeTask();
        } else if (action.equals(ACTION_GET_DATA_FOR_SET)) {
            getImageData();
        }

        return START_REDELIVER_INTENT;
    }

    private void executeTask() {
        if (manager == null) {
            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }
        Intent i = new Intent(this, WallPaperReceiver.class);
        i.setAction(WallPaperReceiver.ACTION_CHANGE_WALL);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000 * 60 * 10, pi);
    }

    private void getImageData() {
        ImageInfoManager manager = ImageInfoManager.getInstance(this);
        manager.getImageInfo(new ImageInfoManager.ImageInfoDataLoadListener() {
            @Override
            public void onSuceess(List<ADeskImageResponse.ResBean.VerticalBean> beans) {
                Intent intent = new Intent(MyWallPaperService.this, WallPaperReceiver.class);
                intent.setAction(WallPaperReceiver.ACTION_CHANGE_WALL);
                sendBroadcast(intent);
//                executeTask();
            }

            @Override
            public void onFailed(String msg) {
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("Service----onDestroy");
    }
}
