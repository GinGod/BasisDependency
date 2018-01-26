package com.gingold.basislibrary.okhttp;

/**
 * OKHttp 下载文件请求回调
 */

public interface BasisDownloadCallback extends BasisCallback {

    /**
     * 下载Progress(下载文件时专用)
     */
    void onProgress(long totalSize, long currentSize, long progress);
}
