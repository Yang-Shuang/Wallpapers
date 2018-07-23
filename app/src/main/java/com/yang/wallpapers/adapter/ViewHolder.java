package com.yang.wallpapers.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> viewArray;

    public ViewHolder(View itemView) {
        super(itemView);
    }

    public <V extends View> V getView(int id) {
        return (V) this.findView(id);
    }

    private View findView(int id) {
        if (this.viewArray == null) {
            this.viewArray = new SparseArray();
        }

        View view = (View) this.viewArray.get(id);
        if (view == null) {
            view = this.itemView.findViewById(id);
            this.viewArray.put(id, view);
        }
        return view;
    }

}
