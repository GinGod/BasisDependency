package com.gingold.basislibrary.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 *
 */

public interface BasisCallback {

    /**
     * 请求网络成功
     */
    void onSuccess(Call call, Response response, String result);

    /**
     * 请求网络失败
     */
    void onFailure(String url, String content, Call call, IOException e, String message);

    /**
     * 处理成功结果异常
     */
    void onException(String url, String content, String result, Exception e, String errorMessage);
}
