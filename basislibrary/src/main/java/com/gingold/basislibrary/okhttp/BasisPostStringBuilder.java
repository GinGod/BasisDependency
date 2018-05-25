package com.gingold.basislibrary.okhttp;

import android.text.TextUtils;

import com.gingold.basislibrary.utils.BasisCommonUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求参数为jsonStr数据
 * 类不公开
 */

class BasisPostStringBuilder extends BasisOkHttpBuilder {

    /**
     * 建立请求
     */
    @Override
    public BasisPostStringBuilder build() {
        super.build();

        Request request = null;
        if (isGet) {
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        } else {
            RequestBody requestBody = null;
            if (!TextUtils.isEmpty(content)) {//提交的是json串
                requestBody = RequestBody.create(mediaType, content);
            } else {//提交键值对
                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : this.params.entrySet()) {
                    builder.add(BasisOkHttpUtils.showStr(entry.getKey()), BasisOkHttpUtils.showStr(entry.getValue()));
                    content = content + BasisOkHttpUtils.showStr(entry.getKey()) + " : " + BasisOkHttpUtils.showStr(entry.getValue()) + " , ";//记录参数
                }
                requestBody = builder.build();
            }

            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        }

        mCall = mOkHttpClient.newCall(request);

        if (BasisOkHttpUtils.OKHTTP_LOG_STATE && isLogState) {
            BasisLogUtils.e("url: " + url + " , jsonStr: " + content);
        }
        return this;
    }

    @Override
    public void enqueue(final BasisCallback basisCallback) {
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                BasisPBLoadingUtils.dismiss();
                final String message;
                if (e != null) {
                    message = BasisCommonUtils.getExceptionInfo(e);
                } else {
                    message = "";
                }
                if (BasisOkHttpUtils.OKHTTP_LOG_STATE && isLogState) {
                    BasisLogUtils.e("onFailure: " + message);
                }
                if (basisCallback != null) {
                    BasisOkHttpUtils.mHandler.post(new Runnable() {
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
                BasisPBLoadingUtils.dismiss();
                final String message;
                if (response != null && response.body() != null) {
                    message = response.body().string();
                } else {
                    message = "";
                }
                if (BasisOkHttpUtils.OKHTTP_LOG_STATE && isLogState) {
                    BasisLogUtils.e("onSuccess: " + message);
                }
                if (basisCallback != null) {
                    BasisOkHttpUtils.mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                basisCallback.onSuccess(call, response, message);
                            } catch (Exception e) {
                                basisCallback.onException(url, content, message, e, BasisCommonUtils.getExceptionInfo(e));
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
