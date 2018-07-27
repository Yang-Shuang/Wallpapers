package com.yang.wallpapers.utils;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;

import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by
 * yangshuang on 2018/7/25.
 */

public class WallPaperUtils {

    public static void setWallPaper(Bitmap bitmap, Context context) {
        int screenWidth = ScreenUtil.screen_width;
        int screenHeight = ScreenUtil.screen_height;
        float scale = screenHeight * 1f / screenWidth;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        if (bitmapHeight * 1f / bitmapWidth == scale) {
            setWallPaperBitmap(bitmap, context);
        } else {
            String imagehandle = Hawk.get(AppConfigConst.Key.IMAGE_HANDLE, AppConfigConst.Value.IMAGE_AUTO);
            Bitmap bitmap1 = null;
            if (imagehandle.equals(AppConfigConst.Value.IMAGE_AUTO)) {
//                float imageScale = bitmapHeight * 1f / bitmapWidth;
//                if (Math.abs(scale - imageScale) <= 0.24) {
//                    bitmap1 = stretchBitmap(bitmap, scale);
//                } else {
//                    bitmap1 = clicpBitmap(bitmap, scale);
//                }
                bitmap1 = bitmap;
            } else if (imagehandle.equals(AppConfigConst.Value.IMAGE_CLICP)) {
                bitmap1 = clicpBitmap(bitmap, scale);
            } else if (imagehandle.equals(AppConfigConst.Value.IMAGE_STRETCH)) {
                bitmap1 = stretchBitmap(bitmap, scale);
            }
            setWallPaperBitmap(bitmap1, context);
        }
        if (context instanceof Activity) {
            ToastUtils.showShort("设置成功", context);
        }
    }

    public static void setLockWallPaper(Bitmap bitmap, Context context) {
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        Class class1 = mWallManager.getClass();//获取类名
        try {
            Method setWallPaperMethod = class1.getMethod("setBitmapToLockWallpaper", Bitmap.class);//获取设置锁屏壁纸的函数
            setWallPaperMethod.invoke(mWallManager, bitmap);//调用锁屏壁纸的函数
            if (context instanceof Activity) {
                ToastUtils.showShort("设置成功", context);
            }
            LogUtil.i("WallPaperUtils---设置锁屏壁纸成功");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap clicpBitmap(Bitmap bitmap, float scale) {
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        Bitmap b = null;
        if (bitmapHeight * 1f / bitmapWidth < scale) {
            int dif = (int) (bitmapWidth - bitmapHeight / scale);
            b = Bitmap.createBitmap(bitmap, dif / 2, 0, (int) (bitmapHeight / scale), bitmapHeight);
        } else {
            int dif = (int) (bitmapHeight - bitmapWidth * scale);
            b = Bitmap.createBitmap(bitmap, 0, dif / 2, bitmapWidth, (int) (bitmapWidth * scale));
        }
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return b;
    }

    public static Bitmap stretchBitmap(Bitmap bitmap, float scale) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        float scaleWidth = 0.0f;
        float scaleHeight = 0.0f;
        if (width * scale > height) {
            height = (int) (width * scale);
        } else {
            width = (int) (height / scale);
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth * 1.0f, scaleHeight * 1.0f);// 使用后乘
//        Bitmap newBM = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        Bitmap newBM = Bitmap.createScaledBitmap(bitmap, width, height, false);

        if (!bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return newBM;
    }

    public static void setWallPaperBitmap(Bitmap bitmap, Context context) {
        LogUtil.e("正在设置壁纸......");
        WallpaperManager mWallManager = WallpaperManager.getInstance(context);
        try {
            if (Build.VERSION.SDK_INT >= 24) {
                mWallManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK | WallpaperManager.FLAG_SYSTEM);
            } else {
                mWallManager.setBitmap(bitmap);
                setLockWallPaper(bitmap, context);
            }
            LogUtil.i("WallPaperUtils---设置壁纸成功");
        } catch (IOException e) {
            LogUtil.i("WallPaperUtils---设置壁纸失败" + e.getMessage());
        }
    }
}
