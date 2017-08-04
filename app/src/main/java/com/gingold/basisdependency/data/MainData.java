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

    public static ArrayList<MainBean> mainList = new ArrayList<>();

    static {
        mainList.add(new MainBean(SP, null));
        mainList.add(new MainBean(LOG, null));
        mainList.add(new MainBean(LVADAPTER, null));
        mainList.add(new MainBean(RVADAPTER, null));
        mainList.add(new MainBean(TEST, null));
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

