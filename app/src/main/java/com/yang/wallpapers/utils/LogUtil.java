package com.yang.wallpapers.utils;

import android.util.Log;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class LogUtil {

    public static void e(String msg) {
        e("LogUtil", msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void w(String msg) {
        w("LogUtil", msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void i(String msg) {
        i("LogUtil", msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
