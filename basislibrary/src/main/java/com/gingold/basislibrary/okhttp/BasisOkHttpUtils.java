package com.gingold.basislibrary.okhttp;

import com.gingold.basislibrary.Base.BasisBaseContants;

/**
 * OKHttp上传下载工具类
 */

public class BasisOkHttpUtils {

    /**
     * 请求参数为jsonStr数据
     */
    public static BasisPostStringBuilder postString() {
        return new BasisPostStringBuilder();
    }

    /**
     * 上传文件
     */
    public static BasisPostFilesBuilder postFile() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 上传多个文件
     */
    public static BasisPostFilesBuilder postFiles() {
        return new BasisPostFilesBuilder();
    }

    /**
     * 下载文件
     */
    public static BasisDownloadBuilder download() {
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
