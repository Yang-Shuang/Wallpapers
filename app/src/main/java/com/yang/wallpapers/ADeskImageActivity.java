package com.yang.wallpapers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yang.wallpapers.adapter.AImageDataAdapter;
import com.yang.wallpapers.adapter.RecyclerOnItemClickListener;
import com.yang.wallpapers.adapter.ViewHolder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ADeskImageActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private int checkCategoryCount;
    private List<ADeskImageResponse.ResBean.VerticalBean> mList;
    private Set<ADeskImageResponse.ResBean.VerticalBean> tempList;
    private AImageDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_desk_image);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    private void initView() {
        refreshLayout = findViewById(R.id.main_refresh);
        recyclerView = findViewById(R.id.main_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshData();
            }
        });
        mList = new ArrayList<>();
        adapter = new AImageDataAdapter(mList, this);
        adapter.setOnItemClickListener(new RecyclerOnItemClickListener<ADeskImageResponse.ResBean.VerticalBean>() {
            @Override
            public void onItemClick(ViewHolder holder, ADeskImageResponse.ResBean.VerticalBean data) {
                Intent intent = new Intent(ADeskImageActivity.this, ImageActivity.class);
                if (data != null) {
                    intent.putExtra(AppConfigConst.Intent.IMAGE_DATA, data);
                }
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

    }

    private void refreshData() {
        LogUtil.i("首页---获取个人最热图片预览");
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
            LogUtil.e("首页---当前一设置偏好分类为：" + cName);
            if (categoryIds.size() == 0) {
                getAllHot();
            } else {
                getCategoryHot(categoryIds);
            }
        }
    }

    private void getAllHot() {
        LogUtil.i("首页---获取所有最热图片预览");
        ADeskRequestEntity request = new ADeskRequestEntity(AppConfigConst.homePage);
        request.setLimit("50");
        NetWorkUtils.GET(request, new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                try {
                    ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                    if (response.getRes() != null && response.getRes().getVertical() != null && response.getRes().getVertical().size() > 0) {
                        mList.clear();
                        mList.addAll(response.getRes().getVertical());
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort("获取最热数据失败", ADeskImageActivity.this);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String str) {
                refreshLayout.finishRefresh();
            }
        });
    }

    private void getCategoryHot(List<ADeskCategoryResponse.ResBean.CategoryBean> categoryIds) {
        LogUtil.i("首页---获取已设置分类最热图片预览");
        checkCategoryCount = 0;
        tempList = new TreeSet<>(new ImageInfoComparator());
        for (final ADeskCategoryResponse.ResBean.CategoryBean bean : categoryIds) {
            if (!StringUtil.isEmpty(bean.getId())) {
                ADeskRequestEntity request = new ADeskRequestEntity(AppConfigConst.category + "/" + bean.getId() + AppConfigConst.vertical);
                String count = Hawk.get(AppConfigConst.Key.CATEGORY_IMAGE_COUNT, "20");
                request.setLimit(count);
                NetWorkUtils.GET(request, new NetWorkUtils.RequestListener() {
                    @Override
                    public void onSuccess(String str) {
                        List<ADeskImageResponse.ResBean.VerticalBean> beans = new ArrayList<>();
                        if (!StringUtil.isEmpty(str)) {
                            try {
                                ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                                if (response.getRes() == null || response.getRes().getVertical() == null || response.getRes().getVertical().size() == 0) {
                                    ToastUtils.showShort(bean.getName() + "--没有获取到图片", ADeskImageActivity.this);
                                } else {
                                    beans = response.getRes().getVertical();
                                }
                            } catch (IOException e) {
                                ToastUtils.showShort(bean.getName() + "--图片数据解析失败", ADeskImageActivity.this);
                            }
                        } else {
                            ToastUtils.showShort(bean.getName() + "--获取图片数据为空", ADeskImageActivity.this);
                        }
                        addData(beans);
                    }

                    @Override
                    public void onError(String str) {
                        ToastUtils.showShort(bean.getName() + str, ADeskImageActivity.this);
                        addData(null);
                    }
                });
                checkCategoryCount++;
            }
        }
    }

    private void addData(List<ADeskImageResponse.ResBean.VerticalBean> list) {
        if (list != null && list.size() > 0) {
            tempList.addAll(list);
        }
        checkCategoryCount--;
        if (checkCategoryCount == 0) {
            mList.clear();
            mList.addAll(tempList);
            adapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
        }
    }

    public void onClick(View view) {
        startActivity(new Intent(this, SettingActivity.class));
    }
}
