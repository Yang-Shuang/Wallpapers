package com.yang.wallpapers.utils;

import com.yang.wallpapers.entity.ADeskImageResponse;

import java.util.Comparator;

/**
 * Created by
 * yangshuang on 2018/7/25.
 */

public class ImageInfoComparator implements Comparator<ADeskImageResponse.ResBean.VerticalBean> {
    @Override
    public int compare(ADeskImageResponse.ResBean.VerticalBean o1, ADeskImageResponse.ResBean.VerticalBean o2) {
        return o2.getRank() - o1.getRank();
    }
}
