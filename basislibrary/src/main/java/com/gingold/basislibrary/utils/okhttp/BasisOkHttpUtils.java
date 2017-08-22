package com.gingold.basislibrary.utils.okhttp;

/**
 *
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
}
