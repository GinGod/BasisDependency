package com.gingold.basisdependency.data;

import java.util.ArrayList;

/**
 * MainActivity测试数据
 */

public class MainData {

    public final static String TEST = "test";
    public final static String SP = "sp";
    public final static String LOG = "log";
    public final static String LVADAPTER = "lvAdapter";
    public final static String RVADAPTER = "rvAdapter";
    public final static String RVREFRESH = "rvRefresh";
    public final static String LVREFRESH = "lvRefresh";
    public final static String OKHTTP = "okhttp";
    public final static String OKHTTPPIC = "okhttpPic";
    public final static String GLIDE = "Glide";
    public final static String PHOTO = "Photo";

    public static ArrayList<MainBean> mainList = new ArrayList<>();

    static {
        mainList.add(new MainBean(TEST, null));
        mainList.add(new MainBean(SP, null));
        mainList.add(new MainBean(LOG, null));
        mainList.add(new MainBean(LVADAPTER, null));
        mainList.add(new MainBean(RVADAPTER, null));
        mainList.add(new MainBean(RVREFRESH, null));
        mainList.add(new MainBean(LVREFRESH, null));
        mainList.add(new MainBean(OKHTTP, null));
        mainList.add(new MainBean(OKHTTPPIC, null));
        mainList.add(new MainBean(GLIDE, null));
        mainList.add(new MainBean(PHOTO, null));
    }

    public static class MainBean {
        public String des;
        public Class cls;

        public MainBean(String des, Class cls) {
            this.des = des;
            this.cls = cls;
        }
    }
}

