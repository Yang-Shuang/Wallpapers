package com.yang.wallpapers.entity;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class ADeskRequestEntity extends BaseRequestEntity{
    public ADeskRequestEntity(String url) {
        super(url);
    }

    private String limit = "30";
    private String adult = "false";
    private String first = "1";
    private String order = "hot";

    public ADeskRequestEntity(String url, String limit, String adult, String first, String order) {
        super(url);
        this.limit = limit;
        this.adult = adult;
        this.first = first;
        this.order = order;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String isAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
