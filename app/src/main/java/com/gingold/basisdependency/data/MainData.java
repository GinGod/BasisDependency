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
    public final static String LVREFRESH = "lvRefresh";
    public final static String PHOTO = "Photo";
    public final static String DB = "DB";
    public final static String DIALOG = "DIALOG";
    public final static String EXCEPTIONINFO = "EXCEPTIONINFO";
    public final static String HTML5 = "HTML5";
    public final static String ANIMATION = "ANIMATION";
    public final static String DATEPICKER = "DATEPICKER";

    public static ArrayList<MainBean> mainList = new ArrayList<>();

    static {
        mainList.add(new MainBean(DATEPICKER, null));
        mainList.add(new MainBean(ANIMATION, null));
        mainList.add(new MainBean(HTML5, null));
        mainList.add(new MainBean(TEST, null));
        mainList.add(new MainBean(SP, null));
        mainList.add(new MainBean(LOG, null));
        mainList.add(new MainBean(LVADAPTER, null));
        mainList.add(new MainBean(LVREFRESH, null));
        mainList.add(new MainBean(PHOTO, null));
        mainList.add(new MainBean(DB, null));
        mainList.add(new MainBean(DIALOG, null));
        mainList.add(new MainBean(EXCEPTIONINFO, null));
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

