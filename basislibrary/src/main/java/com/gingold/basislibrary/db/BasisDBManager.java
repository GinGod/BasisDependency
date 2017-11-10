package com.gingold.basislibrary.db;

import android.content.Context;

/**
 * 数据可创建等管理类
 */

public class BasisDBManager {
    private static BasisDBManager mDBManager;

    private BasisDBManager() {
    }

    /**
     * 单例模式
     */
    public synchronized static BasisDBManager getInstance() {
        if (mDBManager == null) {
            mDBManager = new BasisDBManager();
        }
        return mDBManager;
    }

    /**
     * 数据库初始化
     */
    public static void initDBs(Context context, BasisDBVersion... dbSupports) {
        if (dbSupports == null || dbSupports.length == 0) {
            throw new IllegalArgumentException("数据库初始化参数为空");
        }
        getInstance().createOrUpdateDBs(context, dbSupports);//创建或更新数据库
    }

    /**
     * 单个数据库初始化
     */
    public static BasisDBHelper initDB(Context context, BasisDBVersion dbSupport) {
        return getInstance().createOrUpdateDB(context, dbSupport);//创建或更新数据库
    }

    /**
     * 创建或更新所有数据库
     */
    private void createOrUpdateDBs(Context context, BasisDBVersion... dbSupports) {
        for (int i = 0; i < dbSupports.length; i++) {
            createOrUpdateDB(context, dbSupports[i]);
        }
    }

    /**
     * 创建或更新单个数据库
     */
    private BasisDBHelper createOrUpdateDB(Context context, BasisDBVersion dbSupport) {
        return new BasisDBHelper(context, dbSupport.getDBName(), dbSupport.getVersion(), dbSupport.getTableList());
    }
}
