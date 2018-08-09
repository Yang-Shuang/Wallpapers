package com.yang.wallpapers;

import android.app.Activity;
import android.content.Context;

import com.orhanobut.hawk.Hawk;
import com.yang.wallpapers.entity.ADeskCategoryResponse;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.entity.ADeskRequestEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.ImageInfoComparator;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.StringUtil;
import com.yang.wallpapers.utils.ToastUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by
 * yangshuang on 2018/7/27.
 */

public class ImageInfoManager {

    private Context context;
    private int asynLoadCount = 0;
    private ImageInfoDataLoadListener listener;
    private boolean isLoading = false;
    private Set<ADeskImageResponse.ResBean.VerticalBean> tempList;

    public interface ImageInfoDataLoadListener {
        void onSuceess(List<ADeskImageResponse.ResBean.VerticalBean> beans);

        void onFailed(String msg);

    }

    private ImageInfoManager(Context context) {
        this.context = context;
    }

    public static ImageInfoManager getInstance(Context context) {
        return new ImageInfoManager(context);
    }

    public void getImageInfo(ImageInfoDataLoadListener loadListener) {
        if (loadListener == null) return;
        this.listener = loadListener;
        ArrayList<ADeskCategoryResponse.ResBean.CategoryBean> categoryBeans = Hawk.get(AppConfigConst.Key.CATEGORY_LIST);
        if (categoryBeans == null || categoryBeans.size() == 0) {
            getAllHot();
        } else {
            List<ADeskCategoryResponse.ResBean.CategoryBean> categoryIds = new ArrayList<>();
            String cName = "";
            for (ADeskCategoryResponse.ResBean.CategoryBean bean : categoryBeans) {
                if (bean.isCheck()) {
                    categoryIds.add(bean);
                    cName = cName + bean.getName() + "  ";
                }
            }
            LogUtil.e("ImageInfoManager---当前一设置偏好分类为：" + cName);
            if (categoryIds.size() == 0) {
                getAllHot();
            } else {
                getCategoryHot(categoryIds);
            }
        }
    }

    private void getAllHot() {
        LogUtil.i("ImageInfoManager---获取所有最热图片预览");
        ADeskRequestEntity request = new ADeskRequestEntity(AppConfigConst.homePage);
        request.setLimit("50");
        isLoading = true;
        NetWorkUtils.GET(request, new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                try {
                    ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                    if (response.getRes() != null && response.getRes().getVertical() != null && response.getRes().getVertical().size() > 0) {
                        Hawk.put(AppConfigConst.Key.NEW_IMAGE_HOT_LIST, response.getRes().getVertical());
                        String updateTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis());
                        Hawk.put(AppConfigConst.Key.HOT_LIST_UPDATE_TIME, updateTime);
                        LogUtil.e("保存每日最热数据---" + updateTime);
                        listener.onSuceess(response.getRes().getVertical());
                    } else {
                        writeMsg("获取最热数据为空");
                        listener.onFailed("获取最热数据为空");
                    }
                } catch (IOException e) {
                    writeMsg("解析最热数据异常");
                    listener.onFailed("解析最热数据异常");
                }
                isLoading = false;
            }

            @Override
            public void onError(String str) {
                isLoading = false;
                listener.onFailed("解析最热数据异常");
            }
        });
    }

    private void getCategoryHot(List<ADeskCategoryResponse.ResBean.CategoryBean> categoryIds) {
        LogUtil.i("首页---获取已设置分类最热图片预览");
        asynLoadCount = 0;
        tempList = new TreeSet<>(new ImageInfoComparator());
        isLoading = true;
        for (final ADeskCategoryResponse.ResBean.CategoryBean bean : categoryIds) {
            if (!StringUtil.isEmpty(bean.getId())) {
                ADeskRequestEntity request = new ADeskRequestEntity(AppConfigConst.category + "/" + bean.getId() + AppConfigConst.vertical);
                String count = Hawk.get(AppConfigConst.Key.CATEGORY_IMAGE_COUNT, "20");
                request.setLimit(count);
                NetWorkUtils.GET(request, new NetWorkUtils.RequestListener() {
                    @Override
                    public void onSuccess(String str) {
                        isLoading = false;
                        List<ADeskImageResponse.ResBean.VerticalBean> beans = new ArrayList<>();
                        if (!StringUtil.isEmpty(str)) {
                            try {
                                ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                                if (response.getRes() == null || response.getRes().getVertical() == null || response.getRes().getVertical().size() == 0) {
                                    writeMsg(bean.getName() + "--没有获取到图片");
                                } else {
                                    beans = response.getRes().getVertical();
                                }
                            } catch (IOException e) {
                                writeMsg(bean.getName() + "--图片数据解析失败");
                            }
                        } else {
                            writeMsg(bean.getName() + "--获取图片数据为空");
                        }
                        responseData(beans);
                    }

                    @Override
                    public void onError(String str) {
                        isLoading = false;
                        writeMsg(str);
                        responseData(null);
                    }
                });
                asynLoadCount++;
            }
        }
    }

    private void responseData(List<ADeskImageResponse.ResBean.VerticalBean> list) {
        asynLoadCount--;
        if (list != null && list.size() > 0) {
            tempList.addAll(list);
        }
        if (asynLoadCount == 0) {
            if (tempList.size() == 0) {
                listener.onFailed("获取最热分类数据为空");
            } else {
                List<ADeskImageResponse.ResBean.VerticalBean> list1 = new ArrayList<>();
                list1.addAll(tempList);
                Hawk.put(AppConfigConst.Key.NEW_IMAGE_HOT_LIST, list1);
                String updateTime = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(System.currentTimeMillis());
                Hawk.put(AppConfigConst.Key.HOT_LIST_UPDATE_TIME, updateTime);
                LogUtil.e("保存每日最热数据---" + updateTime);
                listener.onSuceess(list1);
            }
        }
    }

    private void writeMsg(String msg) {
        if (context instanceof Activity) {
            ToastUtils.showShort(msg, context);
        }
        LogUtil.e(msg);
    }
}
