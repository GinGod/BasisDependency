package com.gingold.basislibrary.okhttp;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */

public interface BasisDownloadCallback {

    /**
     * 请求网络成功
     */
    void onSuccess(Call call, Response response, String filePath);

    /**
     * 下载Progress
     */
    void onProgress(long totalSize, long currentSize, long progress);

    /**
     * 请求网络失败
     */
    void onFailure(String url, String content, Call call, Exception e, String message);

    /**
     * 处理成功结果异常
     */
    void onException(String url, String content, String result, Exception e, String errorMessage);
}
