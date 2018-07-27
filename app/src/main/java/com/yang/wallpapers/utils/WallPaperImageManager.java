package com.yang.wallpapers.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.orhanobut.hawk.Hawk;
import com.yang.wallpapers.MyWallPaperService;
import com.yang.wallpapers.entity.ADeskImageResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by
 * yangshuang on 2018/7/27.
 */

public class WallPaperImageManager {

    private static int preLoadCount = 3;
    private static int downLoadCount = 0;
    private static List<ADeskImageResponse.ResBean.VerticalBean> downLoadList;

    public static Bitmap getNewImage(Context context) {
        preLoadCount = Hawk.get(AppConfigConst.Key.WRITE_LOG, 3);

        return prepareImages(context);
    }

    public static Bitmap prepareImages(final Context context) {
        Bitmap bitmap = null;
        List<ADeskImageResponse.ResBean.VerticalBean> beans = getAllImages();
        if (beans == null || beans.size() == 0) {
            ServiceUtil.startService(context, MyWallPaperService.ACTION_GET_DATA_FOR_SET);
            return null;
        }
        ADeskImageResponse.ResBean.VerticalBean bean = new ADeskImageResponse.ResBean.VerticalBean();
        while (StringUtil.isEmpty(bean.getImg())) {
            bean = beans.get(new Random().nextInt(beans.size()));
        }
        bitmap = ImageUtil.getBitmapFromCache(bean.getImg(), context);
        if (bitmap == null) {
            ImageUtil.getImageFromInternet(context, bean.getImg(), new ImageUtil.ImageLoadListener() {
                @Override
                public void onResult(Bitmap bitmap) {
                    if (bitmap != null) WallPaperUtils.setWallPaper(bitmap, context);
                }
                @Override
                public void onFailed(String msg) {
                }
            });
        }
        return bitmap;
    }

    public static List<ADeskImageResponse.ResBean.VerticalBean> getAllImages() {
        return Hawk.get(AppConfigConst.Key.NEW_IMAGE_HOT_LIST, null);
    }

    private static List<ADeskImageResponse.ResBean.VerticalBean> getPreLoadImages(Context context, List<ADeskImageResponse.ResBean.VerticalBean> beans) {
        List<ADeskImageResponse.ResBean.VerticalBean> ps = Hawk.get(AppConfigConst.Key.PRELOAD_IMAGE_LIST, null);
        List<ADeskImageResponse.ResBean.VerticalBean> preLoadImages = new ArrayList<>();
        if (ps == null) {
            ps = new ArrayList<>();
        }
        for (int i = 0; i < preLoadCount; i++) {
            try {
                if (ImageUtil.getBitmapFromCache(ps.get(i).getImg(), context) != null) {
                    preLoadImages.add(ps.get(i));
                } else {
                    if (downLoadList == null) downLoadList = new ArrayList<>();
                    downLoadList.add(ps.get(i));
                }
            } catch (IndexOutOfBoundsException e) {
                ADeskImageResponse.ResBean.VerticalBean bean = null;
                while (StringUtil.isEmpty(bean.getImg())) {
                    bean = beans.get(new Random().nextInt(beans.size()));
                }
                if (downLoadList == null) downLoadList = new ArrayList<>();
                downLoadList.add(bean);
            }
        }
        return preLoadImages;
    }

    private static void downLoadImage(Context context) {
        downLoadCount = downLoadList.size();
        for (final ADeskImageResponse.ResBean.VerticalBean b : downLoadList) {
            ImageUtil.getImageFromInternet(context, b.getImg(), new ImageUtil.ImageLoadListener() {
                @Override
                public void onResult(Bitmap bitmap) {
                    downLoadCount--;
                    saveImage();
                }

                @Override
                public void onFailed(String msg) {
                    downLoadList.remove(b);
                    downLoadCount--;
                    saveImage();
                }
            });
        }
    }

    private static void saveImage() {
        if (downLoadCount == 0) {
            List<ADeskImageResponse.ResBean.VerticalBean> ps = Hawk.get(AppConfigConst.Key.PRELOAD_IMAGE_LIST, new ArrayList<ADeskImageResponse.ResBean.VerticalBean>());
            ps.addAll(downLoadList);
            Hawk.put(AppConfigConst.Key.PRELOAD_IMAGE_LIST, ps);
            downLoadList.clear();
        }
    }
}
