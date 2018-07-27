package com.yang.wallpapers.utils;

import android.content.Context;
import android.content.Intent;

import com.yang.wallpapers.FakeService;
import com.yang.wallpapers.MyWallPaperService;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class ServiceUtil {

    public static void startService(Context context) {
        startService(context, null);
    }

    public static void startService(Context context, String action) {
//        if (MyWallPaperService.instance == null) {
//            Intent i = new Intent(context, FakeService.class);
//            Intent intent = new Intent(context, MyWallPaperService.class);
//            if (!StringUtil.isEmpty(action)) intent.setAction(action);
//            context.startService(i);
//            context.startService(intent);
            LogUtil.e("ServiceUtil-----startService");
//        } else {
            Intent intent = new Intent(context, MyWallPaperService.class);
            if (!StringUtil.isEmpty(action)) intent.setAction(action);
            context.startService(intent);
//        }
    }
}
