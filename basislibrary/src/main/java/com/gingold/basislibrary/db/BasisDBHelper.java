package com.gingold.basislibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gingold.basislibrary.utils.BasisLogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 数据库创建帮组类
 */

public class BasisDBHelper extends SQLiteOpenHelper {
    private ArrayList<BasisDBTable> mTableList;//表名和表中对应字段的类的集合

    public BasisDBHelper(Context context, String name, int version, ArrayList<BasisDBTable> tableList) {
        super(context, name, null, 1);
        this.mTableList = tableList;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (int j = 0; j < mTableList.size(); j++) {
            BasisDBTable basisDBTable = mTableList.get(j);
            String tableName = basisDBTable.getTableName();//表名
            Class tableClass = basisDBTable.getClass();//表对应的类
            Field[] fields = tableClass.getDeclaredFields();//类中所有成员变量
            //拼接sql语句
            StringBuffer buffer = new StringBuffer();
            buffer.append("CREATE TABLE IF NOT EXISTS " + tableName + "(_id integer not null primary key autoincrement,");
            for (int i = 0; i < fields.length; i++) {
                String fieldName = fields[i].getName();
                if (!"$change".equals(fieldName) && !"serialVersionUID".equals(fieldName)) {
                    buffer.append(fields[i].getName() + " varchar(252),");
                }
            }
            buffer.deleteCharAt(buffer.length() - 1);
            buffer.append(")");
            BasisLogUtils.e(buffer.toString());

            db.execSQL(buffer.toString());//执行创建表sql
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
