package com.yang.wallpapers;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.orhanobut.hawk.Hawk;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


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
        String action = intent.getAction();
        if (StringUtil.isEmpty(action)) {
            executeTask();
        } else if (action.equals(ACTION_GET_DATA_FOR_SET)) {
            LogUtil.e("onStartCommand------ACTION_GET_DATA_FOR_SET");
            getImageData(true);
        } else {
            executeTask();
        }

        return START_REDELIVER_INTENT;
    }

    private void executeTask() {
        String updateTime = Hawk.get(AppConfigConst.Key.HOT_LIST_UPDATE_TIME, "no");
        if (!updateTime.equals(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis()))) {
            LogUtil.e("Service更新数据--" + updateTime);
            getImageData(false);
        } else {
            LogUtil.e("今日已更新数据" + updateTime);
        }
//        if (manager == null) {
//            manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        }
//        Intent i = new Intent(this, WallPaperReceiver.class);
//        i.setAction(WallPaperReceiver.ACTION_CHANGE_WALL);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000 * 60 * 10, pi);
    }

    private boolean onLoading = false;

    private void getImageData(final boolean setWallPaper) {
        if (onLoading == true) return;
        onLoading = true;
        ImageInfoManager manager = ImageInfoManager.getInstance(this);
        manager.getImageInfo(new ImageInfoManager.ImageInfoDataLoadListener() {
            @Override
            public void onSuceess(List<ADeskImageResponse.ResBean.VerticalBean> beans) {
                onLoading = false;
                if (setWallPaper) {
                    Intent intent = new Intent(MyWallPaperService.this, WallPaperReceiver.class);
                    intent.setAction(WallPaperReceiver.ACTION_CHANGE_WALL);
                    sendBroadcast(intent);
                }
//                executeTask();
            }

            @Override
            public void onFailed(String msg) {
                onLoading = false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.e("Service----onDestroy");
    }
}
