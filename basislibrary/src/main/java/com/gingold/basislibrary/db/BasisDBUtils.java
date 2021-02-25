package com.gingold.basislibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 数据库操作类
 */

public class BasisDBUtils {
    private BasisDBHelper mDBHelper;//数据库帮助类
    private BasisDBTable mDBTable;//更改数据的对象
    private SQLiteDatabase mReadableDatabase;//可读数据库操作
    private SQLiteDatabase mWritableDatabase;//可写数据库操作

    private BasisDBUtils(Context context, BasisDBVersion dbSupport, BasisDBTable dbTable) {
        mDBHelper = BasisDBManager.initDB(context, dbSupport);
        mReadableDatabase = mDBHelper.getReadableDatabase();
        mWritableDatabase = mDBHelper.getWritableDatabase();
        this.mDBTable = dbTable;
    }

    /**
     * 初始化数据库
     */
    public static BasisDBUtils init(Context context, BasisDBVersion dbSupport, BasisDBTable dbTable) {
        BasisDBUtils basisDBUtils = new BasisDBUtils(context, dbSupport, dbTable);
        return basisDBUtils;
    }

    /**
     * 开启数据库事务
     */
    public void beginTransaction() {
        mWritableDatabase.beginTransaction();
    }

    /**
     * 设置数据库事务成功
     */
    public void setTransactionSuccessful() {
        mWritableDatabase.setTransactionSuccessful();
    }

    /**
     * 保存数据
     *
     * @return true insert successfully
     */
    public boolean save() {
        try {
            ContentValues values = getContentValues();
            return insert(null, values);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将对象储存的值通过反射获取并添加进ContentValues中
     */
    @NonNull
    private ContentValues getContentValues() throws IllegalAccessException {
        ContentValues values = new ContentValues();
        Class clazz = mDBTable.getClass();//获取类
        Field[] fields = clazz.getDeclaredFields();//获取所有成员变量
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            //屏蔽额外的成员变量
            if (!"$change".equals(fieldName) && !"serialVersionUID".equals(fieldName)) {
                field.setAccessible(true);
                String value = (String) field.get(mDBTable);//获取成员变量的值
                if (!TextUtils.isEmpty(value)) {
                    values.put(field.getName(), value);
                }
            }
        }
        return values;
    }

    /**
     * @param nullColumnHack optional; may be <code>null</code>. SQL doesn't allow
     *                       inserting a completely empty row without naming at least one
     *                       column name. If your provided <code>values</code> is empty, no
     *                       column names are known and an empty row can't be inserted. If
     *                       not set to null, the <code>nullColumnHack</code> parameter
     *                       provides the name of nullable column name to explicitly insert
     *                       a NULL into in the case where your <code>values</code> is
     *                       empty.
     * @param values         this map contains the initial column values for the row. The
     *                       keys should be the column names and the values the column
     *                       values
     * @return true insert successfully
     */
    private boolean insert(String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        long result = db.insert(mDBTable.getTableName(), nullColumnHack, values);
        if (result != -1) {
            return true;
        }
        return false;
    }

    /**
     * 删除数据
     *
     * @return true delete successfully
     */
    public boolean delete(String whereClause, String[] whereArgs) {
        return deleteAll(whereClause, whereArgs);
    }

    /**
     * 删除全部数据
     *
     * @return true delete successfully
     */
    public boolean deleteAll() {
        return deleteAll(null, null);
    }

    /**
     * @param whereClause the optional WHERE clause to apply when deleting. Passing null
     *                    will delete all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced
     *                    by the values from whereArgs. The values will be bound as
     *                    Strings.
     * @return true insert successfully
     */
    private boolean deleteAll(String whereClause, String[] whereArgs) {
        int result = mWritableDatabase.delete(mDBTable.getTableName(), whereClause, whereArgs);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 更新数据
     *
     * @return true update successfully
     */
    public boolean update(String whereClause, String[] whereArgs) {
        try {
            ContentValues values = getContentValues();
            return update(values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param values      a map from column names to new column values. null is a valid
     *                    value that will be translated to NULL.
     * @param whereClause the optional WHERE clause to apply when updating. Passing null
     *                    will update all rows.
     * @param whereArgs   You may include ?s in the where clause, which will be replaced
     *                    by the values from whereArgs. The values will be bound as
     *                    Strings.
     * @return true insert successfully
     */
    private boolean update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int result = db.update(mDBTable.getTableName(), values, whereClause, whereArgs);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询所有
     */
    public ArrayList queryAll() {
        return query(null, null, null, null, null, null, null);
    }

    /**
     * 选择查询
     */
    public ArrayList querySelect(String selection, String[] selectionArgs) {
        return query(null, selection, selectionArgs, null, null, null, null);
    }

    /**
     * 选择排序查询
     */
    public ArrayList queryOrder(String selection, String[] selectionArgs, String orderBy, String limit) {
        return query(null, selection, selectionArgs, null, null, orderBy, limit);
    }

    /**
     * @param columns       A list of which columns to return. Passing null will return
     *                      all columns, which is discouraged to prevent reading data from
     *                      storage that isn't going to be used.
     * @param selection     A filter declaring which rows to return, formatted as an SQL
     *                      WHERE clause (excluding the WHERE itself). Passing null will
     *                      return all rows for the given table.
     * @param selectionArgs You may include ?s in selection, which will be replaced by the
     *                      values from selectionArgs, in order that they appear in the
     *                      selection. The values will be bound as Strings.
     * @param groupBy       A filter declaring how to group rows, formatted as an SQL
     *                      GROUP BY clause (excluding the GROUP BY itself). Passing null
     *                      will cause the rows to not be grouped.
     * @param having        A filter declare which row groups to include in the cursor, if
     *                      row grouping is being used, formatted as an SQL HAVING clause
     *                      (excluding the HAVING itself). Passing null will cause all row
     *                      groups to be included, and is required when row grouping is
     *                      not being used.
     * @param orderBy       How to order the rows, formatted as an SQL ORDER BY clause
     *                      (excluding the ORDER BY itself). Passing null will use the
     *                      default sort order, which may be unordered.(asc desc)
     * @param limit         Limits the number of rows returned by the queryAll, formatted as
     *                      LIMIT clause. Passing null denotes no LIMIT clause.
     */
    public ArrayList query(String[] columns, String selection,
                           String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        ArrayList list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mReadableDatabase.query(mDBTable.getTableName(), columns, selection, selectionArgs, groupBy, having, orderBy, limit);
            if (cursor == null) {
                return list;
            }
            while (cursor.moveToNext()) {
                Class clazz = mDBTable.getClass();//获取类
                Object item = clazz.newInstance();//创建对象
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    //屏蔽额外的成员变量
                    if (!"$change".equals(fieldName) && !"serialVersionUID".equals(fieldName)) {
                        field.setAccessible(true);
                        int index = cursor.getColumnIndex(field.getName());//根据名字获取列索引
                        if (index != -1) {//当前列索引存在
                            String value = cursor.getString(index);//根据索引查值
                            if (!TextUtils.isEmpty(value)) {//值不为空
                                field.set(item, value);
                            }
                        }
                    }
                }
                list.add(item);
            }
            cursor.close();//关闭流
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor != null) {//关闭流
                cursor.close();
            }
        }
        return list;
    }

}
