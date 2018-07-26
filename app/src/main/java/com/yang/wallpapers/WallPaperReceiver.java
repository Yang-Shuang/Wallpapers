package com.yang.wallpapers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.ServiceUtil;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class WallPaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.init(context);
        LogUtil.e("WallPaperReceiver---" + intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
            LogUtil.e("WallPaperReceiver---屏幕解锁");
            ServiceUtil.startService(context);
        }
    }
}
