package com.gingold.basislibrary.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */

public interface BasisDownloadCallback {

    /**
     * 请求网络成功
     */
    void success(Call call, Response response, String filePath);

    /**
     * 下载Progress
     */
    void progress(long totalSize, long currentSize, long progress);

    /**
     * 请求网络失败
     */
    void failure(Call call, IOException e, String message);
}
