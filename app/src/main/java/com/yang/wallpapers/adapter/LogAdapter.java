package com.yang.wallpapers.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yang.wallpapers.R;
import com.yang.wallpapers.utils.LogUtil;
import com.yang.wallpapers.utils.ScreenUtil;
import com.yang.wallpapers.utils.StringUtil;

import java.util.List;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public class LogAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<String> mData;

    public LogAdapter(List<String> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_log, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConstraintLayout layout = holder.getView(R.id.log_item_cy);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ScreenUtil.screen_width, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        TextView time = holder.getView(R.id.time_tv);
        TextView msg = holder.getView(R.id.msg_tv);
        String data = mData.get(position);
        if (StringUtil.isEmpty(data)) {
            msg.setText("null");
        } else if (data.contains(LogUtil.divideStr)) {
            String log[] = data.split(LogUtil.divideStr);
            if (log.length == 1) {
                msg.setText(log[0]);
            } else if (log.length == 2) {
                time.setText(log[0]);
                msg.setText(log[1]);
            } else {
                msg.setText("_:_");
            }
        } else {
            msg.setText(data);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
