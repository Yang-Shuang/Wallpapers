package com.yang.wallpapers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yang.wallpapers.adapter.ImageDataAdapter;
import com.yang.wallpapers.entity.BingDataEntity;
import com.yang.wallpapers.entity.BingRequestEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.JsonParser;
import com.yang.wallpapers.utils.NetWorkUtils;
import com.yang.wallpapers.utils.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BingImageActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_image);


        refreshLayout = findViewById(R.id.main_refresh);
        recyclerView = findViewById(R.id.main_rv);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshData();
            }
        });
        refreshData();
    }

    private void refreshData() {
        NetWorkUtils.GET(new BingRequestEntity(AppConfigConst.HPImageArchive), new NetWorkUtils.RequestListener() {
            @Override
            public void onSuccess(String str) {
                if (StringUtil.isEmpty(str)) return;
                try {
                    BingDataEntity entity = JsonParser.json2Bean(str, BingDataEntity.class);
                    List<BingDataEntity.Images> images = entity.getImages();
                    recyclerView.setAdapter(new ImageDataAdapter(images));
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
