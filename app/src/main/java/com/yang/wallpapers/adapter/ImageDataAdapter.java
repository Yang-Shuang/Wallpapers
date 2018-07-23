package com.yang.wallpapers.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.wallpapers.R;
import com.yang.wallpapers.entity.BingDataEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.ImageUtil;
import com.yang.wallpapers.utils.ScreenUtil;

import java.util.List;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ImageDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<BingDataEntity.Images> mData;

    public ImageDataAdapter(List<BingDataEntity.Images> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDraweeView view = holder.getView(R.id.item_image_sdv);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ScreenUtil.screen_width / 2,(int) (ScreenUtil.screen_width / 2 /1080f * 1920f));
//        layoutParams.width = ScreenUtil.screen_width / 2;
//        layoutParams.height = (int) (layoutParams.width/1080f * 1920f);
        view.setLayoutParams(layoutParams);
        ImageUtil.setImage(view, AppConfigConst.BING_IMAGE_URL + mData.get(position).getUrlbase() + "_1080x1920.jpg");

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
