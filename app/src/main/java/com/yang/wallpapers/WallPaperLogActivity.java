package com.yang.wallpapers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yang.wallpapers.adapter.LogAdapter;
import com.yang.wallpapers.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class WallPaperLogActivity extends AppCompatActivity {

    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<String> mList;
    private LogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_paper_log);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.log_back_iv:
                finish();
                break;
        }
    }

    private void initView() {
        refreshLayout = findViewById(R.id.log_refresh);
        recyclerView = findViewById(R.id.log_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mList = new ArrayList<>();
        adapter = new LogAdapter(mList);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                List<String> list = LogUtil.getLog();
                if (list != null && list.size() > 0) {
                    mList.clear();
                    mList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                refreshLayout.finishRefresh();
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }
}
