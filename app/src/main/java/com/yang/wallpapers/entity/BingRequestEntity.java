package com.yang.wallpapers.entity;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class BingRequestEntity extends BaseRequestEntity {

    private String format = "js";
    private String idx = "0"; // 不存在或者等于0时，输出当天的图片，-1为已经预备用于明天显示的信息，1则为昨天的图片，idx最多获取到前16天的图片信息。
    private String n = "8"; // biggest is 8

    public BingRequestEntity(String url) {
        super(url);
    }

    public BingRequestEntity(String url, String format, String idx, String n) {
        super(url);
        this.format = format;
        this.idx = idx;
        this.n = n;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
