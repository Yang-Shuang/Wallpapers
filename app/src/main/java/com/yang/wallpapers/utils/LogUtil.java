package com.yang.wallpapers.utils;

import android.content.Context;
import android.util.Log;

import com.yang.wallpapers.BuildConfig;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class LogUtil {

    private static SimpleDateFormat format;
    private static String LOG_FILE_PATH;
    public static final String divideStr = "_:_";
    private static final int MAX_LINE = 200;

    public static void e(String msg) {
        e("LogUtil", msg);
    }

    public static void init(Context context) {
        if (LOG_FILE_PATH == null)
            LOG_FILE_PATH = context.getFilesDir().getPath() + "/log/log.txt";
        if (format == null)
            format = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss ~ ", Locale.getDefault());
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.log)
            Log.e(tag, msg);
        String time = format.format(System.currentTimeMillis());
        FileIOUtils.writeFileFromString(LOG_FILE_PATH, time + divideStr + msg + "\n", true);
    }

    public static void w(String msg) {
        w("LogUtil", msg);
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.log)
            Log.w(tag, msg);
        String time = format.format(System.currentTimeMillis());
        FileIOUtils.writeFileFromString(LOG_FILE_PATH, time + divideStr + msg + "\n", true);
    }

    public static void i(String msg) {
        i("LogUtil", msg);
    }

    public static void i(String tag, String msg) {
        if (BuildConfig.log)
            Log.i(tag, msg);
        String time = format.format(System.currentTimeMillis());
        FileIOUtils.writeFileFromString(LOG_FILE_PATH, time + divideStr + msg + "\n", true);
    }

    public static List<String> getLog() {
        long count = FileIOUtils.getFileLine(LOG_FILE_PATH);
        List<String> logs = FileIOUtils.readFile2List(LOG_FILE_PATH);
        if (count > MAX_LINE) {
            int dif = logs.size() - MAX_LINE;
            logs = logs.subList(dif, logs.size() - 1);
            StringBuilder builder = new StringBuilder();
            for (String s : logs) {
                builder.append(s);
            }
            FileIOUtils.writeFileFromString(LOG_FILE_PATH, builder.toString());
        }

        return logs;
    }
}
