package com.gingold.basisdependency.data;

import java.util.ArrayList;
import java.util.Random;

/**
 * MainActivity测试数据
 */

public class LVRVData {

    public final static String TEST = "test";
    public final static String SP = "sp";
    public final static String LOG = "log";
    public final static String LVADAPTER = "lvAdapter";
    public final static String RVADAPTER = "rvAdapter";
    public final static String[] strs = new String[]{TEST, SP, LOG, LVADAPTER, RVADAPTER};

    public static ArrayList<LVBean> lvrvList = getData();

    public static ArrayList<LVBean> getData() {
        ArrayList<LVBean> lvrvList = new ArrayList<>();
        lvrvList.add(new LVBean("start", 1));
        for (int i = 0; i < 52; i++) {
            lvrvList.add(new LVBean(strs[new Random().nextInt(5)], new Random().nextInt(3) + 1));
        }
        lvrvList.add(new LVBean("end", 1));
        return lvrvList;
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

