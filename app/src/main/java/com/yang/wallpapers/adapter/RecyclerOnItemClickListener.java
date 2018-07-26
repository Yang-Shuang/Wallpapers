package com.yang.wallpapers.adapter;

/**
 * Created by
 * yangshuang on 2018/7/26.
 */

public interface RecyclerOnItemClickListener<T> {

    void onItemClick(ViewHolder holder, T data);
}
