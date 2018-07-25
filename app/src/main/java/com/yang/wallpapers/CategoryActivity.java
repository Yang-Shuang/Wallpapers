package com.yang.wallpapers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yang.wallpapers.adapter.CategroyAdapter;
import com.yang.wallpapers.entity.ADeskCategoryResponse;
import com.yang.wallpapers.entity.ADeskRequestEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.StringUtil;
import com.yang.wallpapers.utils.ToastUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CategroyAdapter adapter;
    private List<ADeskCategoryResponse.ResBean.CategoryBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initView();
        refreshData();
    }

    private void initView() {
        mList = new ArrayList<>();
        refreshLayout = findViewById(R.id.category_refresh);
        recyclerView = findViewById(R.id.category_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshData();
            }
        });
        adapter = new CategroyAdapter(mList);
        recyclerView.setAdapter(adapter);
    }

    private void refreshData() {
        NetWorkUtils.GET(new ADeskRequestEntity(AppConfigConst.category), new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                if (!StringUtil.isEmpty(str)) {
                    try {
                        ADeskCategoryResponse response = JsonParser.json2Bean(str, ADeskCategoryResponse.class);
                        if (response.getRes().getCategory() == null || response.getRes().getCategory().size() == 0) {
                            ToastUtils.showShort("没有获取到分类", CategoryActivity.this);
                        } else {
                            List<ADeskCategoryResponse.ResBean.CategoryBean> oldDataList = Hawk.get(AppConfigConst.Key.CATEGORY_LIST);
                            mList.clear();
                            mList.addAll(response.getRes().getCategory());
                            if (oldDataList != null && oldDataList.size() > 0) {
                                List<String> checklist = new ArrayList<>();
                                for (ADeskCategoryResponse.ResBean.CategoryBean b : oldDataList) {
                                    if (b.isCheck()) checklist.add(b.getId());
                                }
                                for (ADeskCategoryResponse.ResBean.CategoryBean b : mList) {
                                    if (checklist.contains(b.getId())) {
                                        b.setCheck(true);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } catch (IOException e) {
                        ToastUtils.showShort("分类数据解析失败", CategoryActivity.this);
                    }
                } else {
                    ToastUtils.showShort("获取分类为空", CategoryActivity.this);
                }
                refreshLayout.finishRefresh();
            }

            @Override
            public void onError(String str) {
                ToastUtils.showShort(str, CategoryActivity.this);
                refreshLayout.finishRefresh();
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.category_back_iv:
                finish();
                break;
            case R.id.category_save_tv:
                Hawk.put(AppConfigConst.Key.CATEGORY_LIST, mList);
                finish();
                break;
        }
    }
}
