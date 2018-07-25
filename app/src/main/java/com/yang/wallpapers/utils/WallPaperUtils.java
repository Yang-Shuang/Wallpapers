package com.yang.wallpapers.utils;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by
 * yangshuang on 2018/7/25.
 */

public class WallPaperUtils {

    public static void setWallPaper(Bitmap bitmap, Context context) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        try {
            mWallManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLockWallPaper(Bitmap bitmap, Context context) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        Class class1 = mWallManager.getClass();//获取类名
        try {
            Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);//获取设置锁屏壁纸的函数
            setWallPaperMethod.invoke(mWallManager, bitmap);//调用锁屏壁纸的函数
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
