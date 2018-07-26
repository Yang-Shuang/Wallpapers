package com.yang.wallpapers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yang.wallpapers.utils.LogUtil;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class WallPaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.init(context);
    }
}
