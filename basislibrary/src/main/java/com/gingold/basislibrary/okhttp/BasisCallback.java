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
    void onFailure(Call call, IOException e, String message);
}
