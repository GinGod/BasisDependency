package com.gingold.basislibrary.okhttp;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Map;

/**
 * OKHttp请求工具类
 */

public class BasisOkHttpUtils {
    /**
     * OKHttp日志打印全局状态
     */
    public static boolean OKHTTP_LOG_STATE = true;

    /**
     * 连接超时(默认10s)(全局变量)
     */
    public static long connectTimeout = 10;//连接超时(默认10s)(全局变量)

    /**
     * 读取超时(默认10s)(全局变量)
     */
    public static long readTimeout = 10;//读取超时(默认10s)(全局变量)

    /**
     * 运行在主线程的Handler
     */
    public static final Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 不允许直接创建类对象操作
     */
    private BasisOkHttpUtils() {
    }

    /**
     * 显示String字符串,为空时返回""
     */
    public static String showStr(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 设置连接超时时间(全局使用)
     */
    public static void setConnectTimeout(long connectTimeout) {
        BasisOkHttpUtils.connectTimeout = connectTimeout;
    }

    /**
     * 设置读取超时时间(全局使用)
     */
    public static void setReadTimeout(long readTimeout) {
        BasisOkHttpUtils.readTimeout = readTimeout;
    }

    /**
     * 请求参数为jsonStr数据
     * <p>(使用{@link #postString(String, Object, BasisCallback)}或者
     * {@link #getString(String, BasisCallback)}方法替代)
     */
    @Deprecated
    public static BasisOkHttpBuilder postString() {
        return new BasisPostStringBuilder();
    }

    /**
     * 上传文件(与上传多个文件相同)
     * <p>(使用{@link #postFiles(String, BasisFileInputBean, Map, BasisCallback)}
     * 或者{@link #postFiles(String, ArrayList, Map, BasisCallback)}
     * 或者{@link #getFiles(String, BasisFileInputBean, BasisCallback)}
     * 或者{@link #getFiles(String, ArrayList, BasisCallback)}
     * 方法替代)
     */
    @Deprecated
    public static BasisOkHttpBuilder postFile() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 上传多个文件
     * <p>(使用{@link #postFiles(String, BasisFileInputBean, Map, BasisCallback)}
     * 或者{@link #postFiles(String, ArrayList, Map, BasisCallback)}
     * 或者{@link #getFiles(String, BasisFileInputBean, BasisCallback)}
     * 或者{@link #getFiles(String, ArrayList, BasisCallback)}
     * 方法替代)
     */
    @Deprecated
    public static BasisOkHttpBuilder postFiles() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 下载文件
     * <p>(使用{@link #postDownload(String, Object, BasisCallback)}
     * 或者{@link #postDownload(String, Object, String, String, BasisCallback)}
     * 或者{@link #getDownload(String, BasisCallback)}
     * 或者{@link #getDownload(String, String, String, BasisCallback)}
     * 方法替代)
     */
    @Deprecated
    public static BasisOkHttpBuilder download() {
        return new BasisDownloadBuilder();
    }

    /**
     * 设置OKHttp全局Log日志打印状态, true 打印日志, false 屏蔽日志
     */
    public static BasisOkHttpUtils initLogState(boolean logState) {
        BasisOkHttpUtils.OKHTTP_LOG_STATE = logState;
        return new BasisOkHttpUtils();
    }

    /**
     * get请求
     */
    public static void getString(String url, BasisCallback basisCallback) {
        postString().get().url(url).build().enqueue(basisCallback);
    }

    /**
     * 请求参数为jsonStr数据
     */
    public static void postString(String url, Object content, BasisCallback basisCallback) {
        postString().url(url).content(content).build().enqueue(basisCallback);
    }

    /**
     * get请求
     */
    public static void getDownload(String url, BasisCallback basisCallback) {
        download().get().url(url).build().enqueue(basisCallback);
    }

    /**
     * 请求参数为jsonStr数据
     */
    public static void postDownload(String url, Object content, BasisCallback basisCallback) {
        download().url(url).content(content).build().enqueue(basisCallback);
    }

    /**
     * get请求
     */
    public static void getDownload(String url, String fileDirName, String fileName, BasisCallback basisCallback) {
        download().get().url(url).fileDirName(fileDirName).fileName(fileName).build().enqueue(basisCallback);
    }

    /**
     * 请求参数为jsonStr数据
     */
    public static void postDownload(String url, Object content, String fileDirName, String fileName, BasisCallback basisCallback) {
        download().url(url).fileDirName(fileDirName).fileName(fileName).content(content).build().enqueue(basisCallback);
    }

    /**
     * 请求参数包含文件
     */
    public static void getFiles(String url, BasisFileInputBean fileInputBean, BasisCallback basisCallback) {
        postFiles().get().url(url).addFile(fileInputBean).build().enqueue(basisCallback);
    }

    /**
     * 请求参数包含多个文件
     */
    public static void getFiles(String url, ArrayList<BasisFileInputBean> fileList, BasisCallback basisCallback) {
        postFiles().get().url(url).addFile(fileList).build().enqueue(basisCallback);
    }

    /**
     * 请求参数包含文件
     */
    public static void postFiles(String url, BasisFileInputBean fileInputBean, Map<String, String> params, BasisCallback basisCallback) {
        postFiles().url(url).addFile(fileInputBean).addParams(params).build().enqueue(basisCallback);
    }

    /**
     * 请求参数包含多个文件
     */
    public static void postFiles(String url, ArrayList<BasisFileInputBean> fileList, Map<String, String> params, BasisCallback basisCallback) {
        postFiles().url(url).addFile(fileList).addParams(params).build().enqueue(basisCallback);
    }

}
