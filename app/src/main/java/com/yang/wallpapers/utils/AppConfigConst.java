package com.yang.wallpapers.utils;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class AppConfigConst {

    public static final int HOST_TYPE = 1; // 0 bing  1 aibizhi

    public static final String BING_HOST = "http://cn.bing.com";

    public static final String BING_IMAGE_URL = "http://s.cn.bing.net";

    public static final String ADESK_HOST = "http://service.aibizhi.adesk.com/";

    public static final String HPImageArchive = "/HPImageArchive.aspx";

    public static final String category = "/v1/vertical/category";
    public static final String homePage = "/v3/homepage";
    public static final String vertical = "/vertical";

    public static final class Key {
        public static final String CATEGORY_LIST = "CATEGORY_LIST";
        public static final String CATEGORY_IMAGE_COUNT = "CATEGORY_IMAGE_COUNT";
        public static final String IMAGE_HANDLE = "IMAGE_HANDLE";
        public static final String UNLOCK_CHANGE_WALL = "UNLOCK_CHANGE_WALL";
        public static final String WRITE_LOG = "WRITE_LOG";
        public static final String IS_RONDOM = "IS_RONDOM";
        public static final String PRELOAD_COUNT = "PRELOADCOUNT";
        public static final String CURRENT_IMAGE_INDEX = "CURRENT_IMAGE_INDEX";
        public static final String NEW_IMAGE_HOT_LIST = "NEW_IMAGE_HOT_LIST";
        public static final String PRELOAD_IMAGE_LIST = "PRELOAD_IMAGE_LIST";
    }

    public static final class Value{
        public static final String IMAGE_STRETCH = "适应拉伸";
        public static final String IMAGE_CLICP = "适应裁剪";
        public static final String IMAGE_AUTO = "自动";
    }

    public static final class Intent {
        public static final String IMAGE_DATA = "IMAGE_DATA";
    }
}
