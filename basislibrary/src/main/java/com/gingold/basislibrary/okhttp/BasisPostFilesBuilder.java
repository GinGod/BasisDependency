package com.gingold.basislibrary.okhttp;

import com.gingold.basislibrary.utils.BasisCommonUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传文件
 * 类不公开
 */

class BasisPostFilesBuilder extends BasisOkHttpBuilder {

    /**
     * 上传文件的集合
     */
    public BasisPostFilesBuilder addFile(String key, String name, File file) {
        BasisFileInputBean fileInputBean = new BasisFileInputBean(BasisOkHttpUtils.showStr(key), BasisOkHttpUtils.showStr(name), file);
        this.fileList.add(fileInputBean);
        return this;
    }

    /**
     * 建立请求
     */
    @Override
    public BasisPostFilesBuilder build() {
        super.build();

        Request request = null;
        if (isGet) {
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            if (params != null) {//添加上传参数
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    builder.addFormDataPart(BasisOkHttpUtils.showStr(entry.getKey()), BasisOkHttpUtils.showStr(entry.getValue()));
                    content = content + BasisOkHttpUtils.showStr(entry.getKey()) + " : " + BasisOkHttpUtils.showStr(entry.getValue()) + " , ";//记录参数
                }
            }

            //添加文件
            for (int i = 0; i < fileList.size(); i++) {
                RequestBody fileBody = RequestBody.create(mediaType, fileList.get(i).file);
                builder.addFormDataPart(fileList.get(i).key, fileList.get(i).fileName, fileBody);
            }

//        builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\";filename=\"file.jpg\""),
//                RequestBody.create(MediaType.parse("image/png"), file));

            RequestBody body = builder.build();
            request = new Request.Builder()
                    .url(url)
                    .post(body)
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
                            basisCallback.onFailure(url, content, call, e, message);
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
