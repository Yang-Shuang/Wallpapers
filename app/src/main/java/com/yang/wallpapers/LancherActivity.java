package com.yang.wallpapers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yang.wallpapers.entity.ADeskCategoryResponse;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.entity.ADeskRequestEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.StringUtil;
import com.yang.wallpapers.utils.ToastUtils;

import java.io.IOException;

public class LancherActivity extends AppCompatActivity {

    private boolean hasDelay = false;
    private boolean hasCheckApi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancher);
        initData();
    }

    private void initData() {
        delay();
        checkApi();
    }

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hasDelay = true;
                toHome();
            }
        }, 2000);
    }

    private void checkApi() {
        getCategory();
    }

    private void getCategory() {
        LogUtil.i("App启动---检查获取分类信息api");
        NetWorkUtils.GET(new ADeskRequestEntity(AppConfigConst.category), new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                if (!StringUtil.isEmpty(str)) {
                    try {
                        ADeskCategoryResponse response = JsonParser.json2Bean(str, ADeskCategoryResponse.class);
                        if (response.getRes().getCategory() == null || response.getRes().getCategory().size() == 0) {
                            ToastUtils.showShort("没有获取到分类", LancherActivity.this);
                            hasCheckApi = true;
                            toHome();
                        } else {
                            getFistCategoryImage(response.getRes().getCategory().get(0));
                        }
                    } catch (IOException e) {
                        ToastUtils.showShort("分类数据解析失败", LancherActivity.this);
                        hasCheckApi = true;
                        toHome();
                    }
                } else {
                    ToastUtils.showShort("获取分类为空", LancherActivity.this);
                    hasCheckApi = true;
                    toHome();
                }
            }

            @Override
            public void onError(String str) {
                ToastUtils.showShort(str, LancherActivity.this);
                hasCheckApi = true;
            }
        });
    }

    private void getFistCategoryImage(ADeskCategoryResponse.ResBean.CategoryBean bean) {
        LogUtil.i("App启动---获取分类(" + bean.getName() +")图片信息");
        NetWorkUtils.GET(new ADeskRequestEntity(AppConfigConst.category + "/" + bean.getId() + AppConfigConst.vertical), new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                if (!StringUtil.isEmpty(str)) {
                    try {
                        ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                        if (response.getRes() == null || response.getRes().getVertical() == null || response.getRes().getVertical().size() == 0) {
                            ToastUtils.showShort("没有获取到图片", LancherActivity.this);
                        }
                    } catch (IOException e) {
                        ToastUtils.showShort("图片数据解析失败", LancherActivity.this);
                    }
                    hasCheckApi = true;
                } else {
                    ToastUtils.showShort("获取图片数据为空", LancherActivity.this);
                    hasCheckApi = true;
                }
                toHome();
            }

            @Override
            public void onError(String str) {
                ToastUtils.showShort(str, LancherActivity.this);
                hasCheckApi = true;
                toHome();
            }
        });
    }

    private void toHome() {
        if (hasDelay && hasCheckApi) {
            Class activity = AppConfigConst.HOST_TYPE == 0 ? BingImageActivity.class : ADeskImageActivity.class;
            Intent intent = new Intent(this, activity);
            startActivity(intent);
            finish();
        }
    }
}
