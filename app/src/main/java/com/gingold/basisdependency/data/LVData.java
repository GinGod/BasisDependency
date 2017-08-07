package com.gingold.basisdependency.data;

import java.util.ArrayList;

/**
 * MainActivity测试数据
 */

public class LVData {

    public final static String TEST = "test";
    public final static String SP = "sp";
    public final static String LOG = "log";
    public final static String LVADAPTER = "lvAdapter";
    public final static String RVADAPTER = "rvAdapter";

    public static ArrayList<LVBean> lvList = new ArrayList<>();

    static {
        lvList.add(new LVBean(SP, 1));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(RVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(RVADAPTER, 2));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(LVADAPTER, 3));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(LVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(RVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(RVADAPTER, 2));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(SP, 1));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(RVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(RVADAPTER, 2));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(LVADAPTER, 3));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(RVADAPTER, 2));
        lvList.add(new LVBean(TEST, 2));
        lvList.add(new LVBean(SP, 1));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LOG, 2));
        lvList.add(new LVBean(LVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
        lvList.add(new LVBean(RVADAPTER, 1));
        lvList.add(new LVBean(SP, 3));
    }

    public static class LVBean {
        public String des;
        public int status;

        public LVBean(String des, int status) {
            this.des = des;
            this.status = status;
        }
    }
}

