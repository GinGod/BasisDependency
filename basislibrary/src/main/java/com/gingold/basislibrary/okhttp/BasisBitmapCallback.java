package com.gingold.basislibrary.okhttp;

import android.graphics.Bitmap;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */

public interface BasisBitmapCallback {

    /**
     * 请求网络成功
     */
    void onSuccess(Call call, Response response, Bitmap bitmap, String filePath);

    /**
     * 下载Progress
     */
    void onProgress(long totalSize, long currentSize, long progress);

    /**
     * 请求网络失败
     */
    void onFailure(Call call, Exception e, String message);
}
