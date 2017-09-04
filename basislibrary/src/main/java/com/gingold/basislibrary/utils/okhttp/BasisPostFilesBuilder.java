package com.gingold.basislibrary.utils.okhttp;

import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.bean.BasisFileInputBean;
import com.gingold.basislibrary.utils.BasisLogUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求参数为jsonStr数据
 */

public class BasisPostFilesBuilder extends BasisBaseUtils {
    private String url;//网址
    private String content = "";//jsonStr
    private MediaType mediaType = MediaType.parse("image/*");//默认上传图片
    private Map<String, String> params = new HashMap<>();//上传的参数
    private List<BasisFileInputBean> fileList = new ArrayList<>();//文件集合

    private OkHttpClient mOkHttpClient;
    private Call mCall;

    /**
     * 请求网址
     */
    public BasisPostFilesBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 请求的MediaType
     */
    public BasisPostFilesBuilder mediaType(MediaType mediaType) {
        if (mediaType != null) {
            this.mediaType = mediaType;
        }
        return this;
    }

    /**
     * 上传文件的集合
     */
    public BasisPostFilesBuilder addFile(String key, String name, File file) {
        BasisFileInputBean fileInputBean = new BasisFileInputBean(key, name, file);
        this.fileList.add(fileInputBean);
        return this;
    }

    /**
     * 添加参数
     */
    public BasisPostFilesBuilder addParams(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * 添加参数集合
     */
    public BasisPostFilesBuilder addParams(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.params.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 建立请求
     */
    public BasisPostFilesBuilder build() {
        mOkHttpClient = new OkHttpClient();

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (params != null) {//添加上传参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
                content = content + entry.getKey() + " = " + entry.getValue() + " , ";//记录参数
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
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        mCall = mOkHttpClient.newCall(request);
        BasisLogUtils.e("url: " + url + " , jsonStr: " + content);
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
                BasisLogUtils.e("failure: " + message);
                if (basisCallback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            basisCallback.failure(call, e, message);
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
                BasisLogUtils.e("success: " + message);
                if (basisCallback != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            basisCallback.success(call, response, message);
                        }
                    });
                }
            }
        });
    }
}
