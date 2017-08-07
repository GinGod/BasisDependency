package com.gingold.basisdependency.data;

import java.util.ArrayList;

/**
 * MainActivity测试数据
 */

public class SPData {

    public final static String TEST = "test";
    public final static String STRING = "String";
    public final static String INT = "int";
    public final static String LONG = "long";
    public final static String BOOLEAN = "boolean";

    public static ArrayList<String> SPList = new ArrayList<>();

    static {
        SPList.add(STRING);
        SPList.add(INT);
        SPList.add(LONG);
        SPList.add(BOOLEAN);
    }

}

