package com.gingold.basislibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类
 */
public class BasisSPUtils {
    private static final String preference = "SP";//默认sp名

    /**
     * 设置字符(采用默认sp名)
     */
    public static void setStringPreferences(Context context, String key, String value) {
        setStringPreferences(context, preference, key, value);
    }

    /**
     * 获取字符(采用默认sp名)
     */
    public static String getStringPreference(Context context, String key, String defaultValue) {
        return getStringPreference(context, preference, key, defaultValue);
    }

    /**
     * 设置长整型(采用默认sp名)
     */
    public static void setLongPreferences(Context context, String key, long value) {
        setLongPreferences(context, preference, key, value);
    }

    /**
     * 获取长整型(采用默认sp名)
     */
    public static long getLongPreferences(Context context, String key, long defaultValue) {
        return getLongPreferences(context, preference, key, defaultValue);
    }

    /**
     * 设置boolean类型(采用默认sp名)
     */
    public static void setBooleanPreferences(Context context, String key, boolean value) {
        setBooleanPreferences(context, preference, key, value);
    }

    /**
     * 获取长整型(采用默认sp名)
     */
    public static boolean getBooleanPreferences(Context context, String key, boolean defaultValue) {
        return getBooleanPreferences(context, preference, key, defaultValue);
    }

    /**
     * 设置int(采用默认sp名)
     */
    public static void setIntPreferences(Context context, String key, int value) {
        setIntPreferences(context, preference, key, value);
    }

    /**
     * 获取int(采用默认sp名)
     */
    public static int getIntPreference(Context context, String key, int defaultValue) {
        return getIntPreference(context, preference, key, defaultValue);
    }

    /**
     * 删除一个属性(采用默认sp名)
     */
    public static void deletePrefereceKey(Context context, String key) {
        deletePrefereceKey(context, preference, key);
    }

    /**
     * 清楚sp中的所有值(采用默认sp名)
     */
    public static void clearPreference(Context context) {
        clearPreference(context, preference);
    }

    /**
     * 设置字符(自定义sp名)
     */
    public static void setStringPreferences(Context context, String preference, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符(自定义sp名)
     */
    public static String getStringPreference(Context context, String preference, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 设置长整型(自定义sp名)
     */
    public static void setLongPreferences(Context context, String preference, String key, long value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取长整型(自定义sp名)
     */
    public static long getLongPreferences(Context context, String preference, String key, long defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 设置boolean类型(自定义sp名)
     */
    public static void setBooleanPreferences(Context context, String preference, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取长整型(自定义sp名)
     */
    public static boolean getBooleanPreferences(Context context, String preference, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 设置int(自定义sp名)
     */
    public static void setIntPreferences(Context context, String preference, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取int(自定义sp名)
     */
    public static int getIntPreference(Context context, String preference, String key, int defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 删除一个属性(自定义sp名)
     */
    public static void deletePrefereceKey(Context context, String preference, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清楚sp中的所有值(自定义sp名)
     */
    public static void clearPreference(Context context, String preference) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preference, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
