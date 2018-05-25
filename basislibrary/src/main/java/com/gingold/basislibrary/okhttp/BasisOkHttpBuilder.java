package com.gingold.basislibrary.okhttp;

import android.content.Context;

import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Okhttp请求基类Builder
 */

public class BasisOkHttpBuilder {
    public OkHttpClient mOkHttpClient;

    public boolean isLogState = true;//日志打印状态, 默认true
    public String url = "";//网址
    public boolean isGet = false;//使用get请求(默认不使用)
    public String content = "";//jsonStr
    //    private MediaType mediaType = MediaType.parse("text/plain;charset=utf-8");//默认MediaType
    public MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//默认MediaType

    public Map<String, String> params = new HashMap<>();//参数集合

    public List<BasisFileInputBean> fileList = new ArrayList<>();//文件集合

    public Call mCall;
    public Context context;//是否显示进度dialog

    /**
     * 不允许直接创建类对象操作
     */
    protected BasisOkHttpBuilder() {
    }

    /**
     * 本次请求日志打印状态, 默认true, 打印日志
     */
    public BasisOkHttpBuilder setLogState(boolean logState) {
        isLogState = logState;
        return this;
    }

    /**
     * 请求网址
     */
    public BasisOkHttpBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 更改请求为get请求, 设置参数等方法无效
     */
    public BasisOkHttpBuilder get() {
        this.isGet = true;
        return this;
    }

    /**
     * 请求String的MediaType
     */
    public BasisOkHttpBuilder mediaType(MediaType mediaType) {
        if (mediaType != null) {
            this.mediaType = mediaType;
        }
        return this;
    }

    /**
     * 请求参数
     */
    public BasisOkHttpBuilder content(Object object) {
        if (object != null && object instanceof String) {
            this.content = (String) object;
        } else {
            this.content = new Gson().toJson(object);
        }
        return this;
    }

    /**
     * 添加参数
     */
    public BasisOkHttpBuilder addParams(String key, String value) {
        this.params.put(BasisOkHttpUtils.showStr(key), BasisOkHttpUtils.showStr(value));
        return this;
    }

    /**
     * 添加参数集合
     */
    public BasisOkHttpBuilder addParams(Map<String, String> map) {
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.params.put(BasisOkHttpUtils.showStr(entry.getKey()), BasisOkHttpUtils.showStr(entry.getValue()));
            }
        }
        return this;
    }

    /**
     * 显示请求dialog
     */
    public BasisOkHttpBuilder dialog(Context context) {
        this.context = context;
        if (context != null) {
            BasisPBLoadingUtils.build(context).show();//显示进度
        }
        return this;
    }

    /**
     * 连接超时时间(单位默认 s-秒)
     */
    public BasisOkHttpBuilder connectTimeout(long connectTimeout) {
        BasisOkHttpUtils.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读取超时时间(单位默认 s-秒)
     */
    public BasisOkHttpBuilder readTimeout(long readTimeout) {
        BasisOkHttpUtils.readTimeout = readTimeout;
        return this;
    }

    /**
     * 建立请求
     */
    public BasisOkHttpBuilder build() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(BasisOkHttpUtils.connectTimeout, TimeUnit.SECONDS)
                .readTimeout(BasisOkHttpUtils.readTimeout, TimeUnit.SECONDS).build();
        return this;
    }

    /**
     * 执行请求
     */
    public void execute() {
        enqueue(null);
    }

    /**
     * 执行请求
     */
    public void execute(BasisCallback basisCallback) {
        enqueue(basisCallback);
    }

    public void enqueue(final BasisCallback basisCallback) {
    }

    /**
     * 储存的文件夹(下载时专用)
     */
    public BasisOkHttpBuilder fileDirName(String fileDirName) {
        return this;
    }

    /**
     * 储存的文件名(下载时专用)
     */
    public BasisOkHttpBuilder fileName(String fileName) {
        return this;
    }

    /**
     * 上传文件的集合(上传时专用)
     */
    public BasisOkHttpBuilder addFile(String key, String name, File file) {
        return this;
    }

    /**
     * 上传文件的集合
     */
    public BasisOkHttpBuilder addFile(BasisFileInputBean fileInputBean) {
        this.fileList.add(fileInputBean);
        return this;
    }

    /**
     * 上传文件的集合
     */
    public BasisOkHttpBuilder addFile(ArrayList<BasisFileInputBean> fileList) {
        this.fileList.addAll(fileList);
        return this;
    }
}
