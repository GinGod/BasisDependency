package com.gingold.basislibrary.okhttp;

import com.gingold.basislibrary.Base.BasisBaseContants;

/**
 * OKHttp请求工具类
 */

public class BasisOkHttpUtils {

    /**
     * 请求参数为jsonStr数据
     */
    public static BasisOkHttpBuilder postString() {
        return new BasisPostStringBuilder();
    }

    /**
     * 上传文件(与上传多个文件相同)
     *
     * @see #postFiles()
     */
    public static BasisOkHttpBuilder postFile() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 上传多个文件
     */
    public static BasisOkHttpBuilder postFiles() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 下载文件
     */
    public static BasisOkHttpBuilder download() {
        return new BasisDownloadBuilder();
    }

    /**
     * 设置OKHttp全局Log日志打印状态, true 打印日志, false 屏蔽日志
     */
    public static BasisOkHttpUtils initLogState(boolean logState) {
        BasisBaseContants.OKHTTP_LOG_STATE = logState;
        return new BasisOkHttpUtils();
    }
}
