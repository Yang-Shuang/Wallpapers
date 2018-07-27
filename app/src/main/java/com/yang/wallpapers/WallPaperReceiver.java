package com.yang.wallpapers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.orhanobut.hawk.Hawk;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.ServiceUtil;
import com.yang.wallpapers.utils.StringUtil;
import com.yang.wallpapers.utils.WallPaperImageManager;
import com.yang.wallpapers.utils.WallPaperUtils;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class WallPaperReceiver extends BroadcastReceiver {
    public static final String ACTION_CHANGE_WALL = "WallPaperReceiver.ACTION_CHANGE_WALL";
    public static final String ACTION_GET_IMAGE_LIST = "WallPaperReceiver.ACTION_GET_IMAGE_LIST";
    public static final String ACTION_CHANGE_SET_WITH_BITMAP = "WallPaperReceiver.ACTION_CHANGE_SET_WITH_BITMAP";

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.init(context);
        LogUtil.e("WallPaperReceiver---" + intent.getAction());
        String action = intent.getAction();
        if (StringUtil.isEmpty(action)) {
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            LogUtil.e("WallPaperReceiver---屏幕解锁");
            if (Hawk.get(AppConfigConst.Key.UNLOCK_CHANGE_WALL, false)) {
                Bitmap bitmap = WallPaperImageManager.getNewImage(context);
                if (bitmap != null) {
                    WallPaperUtils.setWallPaper(bitmap, context);
                }
            }
        } else if (action.equals(ACTION_CHANGE_SET_WITH_BITMAP)) {
            Bitmap bitmap = intent.getParcelableExtra("Bitmap");
            if (bitmap != null) {
                WallPaperUtils.setWallPaper(bitmap, context);
            }
        } else if (action.equals(ACTION_GET_IMAGE_LIST)) {
            intent.setAction(MyWallPaperService.ACTION_GET_DATA);
        }

        ServiceUtil.startService(context, action);
    }
}
