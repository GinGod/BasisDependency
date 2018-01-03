package com.gingold.basislibrary.okhttp;

import android.text.TextUtils;

import com.gingold.basislibrary.Base.BasisBaseContants;
import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求参数为jsonStr数据
 */

public class BasisPostStringBuilder extends BasisBaseUtils {
    private boolean isLogState = true;//日志打印状态, 默认true
    private String url = "";//网址
    private String content = "";//jsonStr
    //    private MediaType mediaType = MediaType.parse("text/plain;charset=utf-8");//默认MediaType
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//默认MediaType

    private Map<String, String> params = new HashMap<>();//参数集合

    private OkHttpClient mOkHttpClient;
    private Call mCall;

    /**
     * 本次请求日志打印状态, 默认true, 打印日志
     */
    public BasisPostStringBuilder setLogState(boolean logState) {
        isLogState = logState;
        return this;
    }

    /**
     * 请求网址
     */
    public BasisPostStringBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 请求String的MediaType
     */
    public BasisPostStringBuilder mediaType(MediaType mediaType) {
        if (mediaType != null) {
            this.mediaType = mediaType;
        }
        return this;
    }

    /**
     * 请求参数
     */
    public BasisPostStringBuilder content(Object object) {
        if (object != null && object instanceof String) {
            this.content = (String) object;
        } else {
            this.content = new Gson().toJson(object);
        }
        return this;
    }

    /**
     * 添加参数
     */
    public BasisPostStringBuilder addParams(String key, String value) {
        this.params.put(BasisBaseUtils.showStr(key), BasisBaseUtils.showStr(value));
        return this;
    }

    /**
     * 添加参数集合
     */
    public BasisPostStringBuilder addParams(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.params.put(BasisBaseUtils.showStr(entry.getKey()), BasisBaseUtils.showStr(entry.getValue()));
        }
        return this;
    }

    /**
     * 建立请求
     */
    public BasisPostStringBuilder build() {
        mOkHttpClient = new OkHttpClient();

        RequestBody requestBody = null;
        if (!TextUtils.isEmpty(content)) {//提交的是json串
            requestBody = RequestBody.create(mediaType, content);
        } else {//提交键值对
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : this.params.entrySet()) {
                builder.add(BasisBaseUtils.showStr(entry.getKey()), BasisBaseUtils.showStr(entry.getValue()));
                content = content + BasisBaseUtils.showStr(entry.getKey()) + " : " + BasisBaseUtils.showStr(entry.getValue()) + " , ";//记录参数
            }
            requestBody = builder.build();
        }

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mCall = mOkHttpClient.newCall(request);

        if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
            BasisLogUtils.e("url: " + url + " , jsonStr: " + content);
        }
        return this;
    }

    /**
     * 执行请求
     */
    public void execute() {
        enqueue(null);
    }

    /**
     * 执行请求
     */
    public void execute(final BasisCallback basisCallback) {
        enqueue(basisCallback);
    }

    private void enqueue(final BasisCallback basisCallback) {
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                final String message;
                if (e != null) {
                    message = e.getMessage();
                } else {
                    message = "";
                }
                if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
                    BasisLogUtils.e("onFailure: " + message);
                }
                if (basisCallback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                basisCallback.onFailure(url, content, call, e, message);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String message;
                if (response != null && response.body() != null) {
                    message = response.body().string();
                } else {
                    message = "";
                }
                if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
                    BasisLogUtils.e("onSuccess: " + message);
                }
                if (basisCallback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                basisCallback.onSuccess(call, response, message);
                            } catch (Exception e) {
                                basisCallback.onException(url, content, message, e, e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
