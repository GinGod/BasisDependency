package com.gingold.basislibrary.utils;

import android.util.Log;

/**
 * BasisLogUtils
 */
public class BasisLogUtils {

    /**
     * 日志级别
     */
    public final static int LOG_VERBOSE = 1; // verbose
    public final static int LOG_DEBUG = 2; // debug
    public final static int LOG_INFO = 3; // info
    public final static int LOG_WARN = 4; // warn
    public final static int LOG_ERROR = 5; // error
    public final static int LOG_WTF = 6; // wtf

    /**
     * 日志开关 LOG_ALL-打印所有日志 LOG_CLOSED-关闭所有日志
     */
    public final static int LOG_ALL = 0;
    public final static int LOG_CLOSED = 7;

    /**
     * 当前日志级别(默认打印全部)
     */
    public static int LOG_LEVEL = LOG_ALL;

    /**
     * 当前日志分段打印长度
     */
    public static int LOG_LENGTH = 2000;

    /**
     * 取消分段打印
     */
    public final static int LOG_LENGTH_MAX = Integer.MAX_VALUE;

    /**
     * 当前日志标识tag
     */
    public static String TAG = "TAG";

    /**
     * 设置日志分段打印长度
     */
    public static void setLogLength(int length) {
        LOG_LENGTH = length;
    }

    /**
     * 设置日志标识
     */
    public static void setLogTag(String tag) {
        TAG = tag;
    }

    /**
     * 设置日志打印级别 1-verbose; 2-debug; 3-info; 4-warn; 5-error; 6-wtf
     */
    public static void setLogLevel(int level) {
        LOG_LEVEL = level;
    }

    /**
     * verbose
     */
    public static void v(String message) {
        if (LOG_LEVEL <= LOG_VERBOSE)
            v(TAG, message);
    }

    /**
     * debug
     */
    public static void d(String message) {
        if (LOG_LEVEL <= LOG_DEBUG)
            d(TAG, message);
    }

    /**
     * info
     */
    public static void i(String message) {
        if (LOG_LEVEL <= LOG_INFO)
            i(TAG, message);
    }

    /**
     * warn
     */
    public static void w(String message) {
        if (LOG_LEVEL <= LOG_WARN)
            w(TAG, message);

    }

    /**
     * error
     */
    public static void e(String message) {
        if (LOG_LEVEL <= LOG_ERROR)
            e(TAG, message);
    }

    /**
     * wtf
     */
    public static void wtf(String message) {
        if (LOG_LEVEL <= LOG_WTF)
            wtf(TAG, message);
    }

    public static void v(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_VERBOSE);
    }

    public static void d(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_DEBUG);
    }

    public static void i(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_INFO);
    }

    public static void w(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_WARN);

    }

    public static void e(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_ERROR);
    }

    public static void wtf(String tag, String message) {
        print(tag + "-" + TAG, message, LOG_LENGTH, LOG_WTF);
    }

    public static void print(String tag, String message, int length, int which) {
        int strLength = message.length();
        int start = 0;
        int end = length;
        for (int i = 0; i < 252 * 10; i++) {
            if (strLength > end) {
                print(tag + "-" + i, message.substring(start, end), which);
                start = end;
                end = end + length;
            } else {
                print(tag + "-end", message.substring(start, strLength), which);
                break;
            }
        }
    }

    public static void print(String tag, String message, int which) {
        switch (which) {
            case LOG_VERBOSE:
                Log.v(tag, message);
                break;
            case LOG_DEBUG:
                Log.d(tag, message);
                break;
            case LOG_INFO:
                Log.i(tag, message);
                break;
            case LOG_WARN:
                Log.w(tag, message);
                break;
            case LOG_ERROR:
                Log.e(tag, message);
                break;
            case LOG_WTF:
                Log.wtf(tag, message);
                break;
        }
    }
}
