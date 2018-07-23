package com.yang.wallpapers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yang.wallpapers.adapter.AImageDataAdapter;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.entity.ADeskRequestEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.NetWorkUtils;

import java.io.IOException;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ADeskImageActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_desk_image);

        initView();
        refreshData();
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
    }

    private void refreshData() {
        NetWorkUtils.GET(new ADeskRequestEntity(AppConfigConst.homePage), new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                try {
                    ADeskImageResponse response = JsonParser.json2Bean(str, ADeskImageResponse.class);
                    recyclerView.setAdapter(new AImageDataAdapter(response.getRes().getVertical()));
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
}
