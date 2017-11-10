package com.gingold.basislibrary.db;

import java.util.ArrayList;

/**
 * 数据库版本
 */

public interface BasisDBVersion {
    ArrayList<BasisDBTable> list = new ArrayList<>();

    String getDBName();//数据库名

    /**
     * 暂时不支持版本号更新
     */
    int getVersion();//版本号

    ArrayList<BasisDBTable> getTableList();//表名和表中对应字段的类
}
