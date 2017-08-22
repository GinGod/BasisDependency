package com.gingold.basislibrary.utils.okhttp;

import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */

public interface BasisBitmapCallback {

    /**
     * 请求网络成功
     */
    void success(Call call, Response response, Bitmap bitmap, String filePath);

    /**
     * 请求网络失败
     */
    void failure(Call call, IOException e, String message);
}
