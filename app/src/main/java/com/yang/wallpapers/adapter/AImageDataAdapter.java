package com.yang.wallpapers.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yang.wallpapers.R;
import com.yang.wallpapers.entity.ADeskImageResponse;
import com.yang.wallpapers.entity.BingDataEntity;
import com.yang.wallpapers.utils.AppConfigConst;
import com.yang.wallpapers.utils.ImageUtil;
import com.yang.wallpapers.utils.ScreenUtil;

import java.util.List;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class AImageDataAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<ADeskImageResponse.ResBean.VerticalBean> mData;
    private int imageWidth, imageHeight, space;
    private Context context;

    public AImageDataAdapter(List<ADeskImageResponse.ResBean.VerticalBean> mData,Context context) {
        this.mData = mData;
        this.context = context;
        space = ScreenUtil.dp2px(2);
        imageWidth = (ScreenUtil.screen_width - space * 4) / 3;
        imageHeight = (int) (imageWidth / 1080f * 1920);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SimpleDraweeView view = holder.getView(R.id.item_image_sdv);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(imageWidth, imageHeight);
        layoutParams.leftMargin = space;
        layoutParams.bottomMargin = space;
        view.setLayoutParams(layoutParams);
        ImageUtil.setImage(view, mData.get(position).getThumb());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
