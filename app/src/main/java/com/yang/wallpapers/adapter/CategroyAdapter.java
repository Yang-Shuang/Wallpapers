package com.yang.wallpapers.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.yang.wallpapers.R;
import com.yang.wallpapers.entity.ADeskCategoryResponse;
import com.yang.wallpapers.utils.ScreenUtil;

import java.util.List;

/**
 * Created by
 * yangshuang on 2018/7/25.
 */

public class CategroyAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ADeskCategoryResponse.ResBean.CategoryBean> mData;
    private int checkboxWidth, space;

    public CategroyAdapter(List<ADeskCategoryResponse.ResBean.CategoryBean> mData) {
        this.mData = mData;
        space = ScreenUtil.dp2px(12);
        checkboxWidth = (int) ((ScreenUtil.screen_width - space * 6) / 5f);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_category, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ADeskCategoryResponse.ResBean.CategoryBean bean = mData.get(position);
        CheckBox box = holder.getView(R.id.category_item_cb);
        ViewGroup.MarginLayoutParams p = new ViewGroup.MarginLayoutParams(checkboxWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        p.leftMargin = space;
        p.topMargin = space * 2;
        box.setLayoutParams(p);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bean.setCheck(isChecked);
            }
        });
        box.setChecked(bean.isCheck());
        box.setText(bean.getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
