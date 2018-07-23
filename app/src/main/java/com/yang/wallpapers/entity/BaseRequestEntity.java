package com.yang.wallpapers.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class BaseRequestEntity implements Serializable{

    @JsonIgnore
    private String url;

    public BaseRequestEntity(String url) {
        this.url = url;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
