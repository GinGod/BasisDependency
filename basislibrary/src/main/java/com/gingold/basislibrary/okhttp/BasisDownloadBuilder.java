package com.gingold.basislibrary.okhttp;

import android.text.TextUtils;

import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.utils.BasisFileUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class BasisDownloadBuilder extends BasisBaseUtils {
    private String url;//网址
    private boolean isGet = false;//使用get请求
    private String fileDirName = "Download";//文件夹名(默认Download)
    private String fileName;//文件名

    private String content = "";//jsonStr
    //    private MediaType mediaType = MediaType.parse("text/plain;charset=utf-8");//默认MediaType
    private MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//默认MediaType

    private Map<String, String> params = new HashMap<>();//参数集合

    private OkHttpClient mOkHttpClient;
    private Call mCall;

    /**
     * 请求网址
     */
    public BasisDownloadBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 请求网址
     */
    public BasisDownloadBuilder get() {
        this.isGet = true;
        return this;
    }

    /**
     * 储存的文件夹
     */
    public BasisDownloadBuilder fileDirName(String fileDirName) {
        this.fileDirName = fileDirName;
        return this;
    }

    /**
     * 储存的文件名
     */
    public BasisDownloadBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * 请求String的MediaType
     */
    public BasisDownloadBuilder mediaType(MediaType mediaType) {
        if (mediaType != null) {
            this.mediaType = mediaType;
        }
        return this;
    }

    /**
     * 请求参数
     */
    public BasisDownloadBuilder content(Object object) {
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
    public BasisDownloadBuilder addParams(String key, String value) {
        this.params.put(key, value);
        return this;
    }

    /**
     * 添加参数集合
     */
    public BasisDownloadBuilder addParams(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.params.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 建立请求
     */
    public BasisDownloadBuilder build() {
        mOkHttpClient = new OkHttpClient();

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
                    builder.add(entry.getKey(), entry.getValue());
                    content = content + entry.getKey() + " : " + entry.getValue() + " , ";//记录参数
                }
                requestBody = builder.build();
            }

            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        }
        
        mCall = mOkHttpClient.newCall(request);
        BasisLogUtils.e("url: " + url + " , jsonStr: " + content);
        return this;
    }

    /**
     * 执行请求(只下载, 不对结果进行回调)
     */
    public void execute() {
        enqueue(null);
    }

    /**
     * 执行请求(默认下载的是文件)
     */
    public void execute(final BasisDownloadCallback basisCallback) {
        enqueue(basisCallback);
    }

    private void enqueue(final BasisDownloadCallback basisCallback) {
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                String message = "";
                if (e != null) {
                    message = e.getMessage();
                }
                BasisLogUtils.e("onFailure: " + message);
                failure(call, e, message, basisCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                //获取返回的输入流
                InputStream inputStream = response.body().byteStream();
                final long totalSize = response.body().contentLength();//文件总大小

                FileOutputStream OutputStream = null;

                try {
                    //下载储存文件夹
                    String dirPath = BasisFileUtils.mkdir(fileDirName);
                    File fileDir = new File(dirPath);

                    //下载文件名
                    if (TextUtils.isEmpty(fileName)) {
                        if (TextUtils.isEmpty(url)) {//网址为空
                            failure(call, new IllegalArgumentException("网址为空"), "网址为空", basisCallback);
                            return;
                        } else {
                            //截取网址最后15位作为下载的文件名
                            String replace = url.replace("\\", "").replace("/", "").replace(":", "");
                            if (replace.length() > 15) {
                                fileName = replace.substring(replace.length() - 15);
                            } else {
                                fileName = replace;
                            }
                        }
                    }

                    //下载的文件
                    File file = checkFile(fileDir, fileName, fileName);
                    OutputStream = new FileOutputStream(file);
                    final String filePath = file.getAbsolutePath();

                    //开始下载
                    byte[] buffer = new byte[1024 * 2];
                    int len = 0;
                    long currentSize = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        OutputStream.write(buffer, 0, len);
                        currentSize = currentSize + len;
                        process(totalSize, currentSize, basisCallback);//进度
                    }
                    OutputStream.flush();

                    success(call, response, filePath, basisCallback);//下载完成

                    BasisLogUtils.e("文件下载成功: " + filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                    failure(call, e, e.getMessage(), basisCallback);
                } finally {
                    if (inputStream != null) {//关流释放资源
                        try {
                            inputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (OutputStream != null) {
                        try {
                            OutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    /**
     * 下载成功
     */
    private void success(final Call call, final Response response, final String filePath, final BasisDownloadCallback basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (basisCallback != null) {
                    try {
                        basisCallback.onSuccess(call, response, filePath);
                    } catch (Exception e) {
                        basisCallback.onException(url, content, filePath, e, e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 下载进度
     */
    private void process(final long totalSize, final long process, final BasisDownloadCallback basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //下载进度
                if (basisCallback != null) {
                    basisCallback.onProgress(totalSize, process, process * 100 / totalSize);
                }
            }
        });
    }

    /**
     * 下载失败
     */
    private void failure(final Call call, final Exception e, final String message, final BasisDownloadCallback basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (basisCallback != null) {
                    basisCallback.onFailure(url, content, call, e, message);
                }
            }
        });
    }

    /**
     * 检测重复文件
     */
    private static File checkFile(File appDir, String fileName, String newFileName) {
        File picFile = new File(appDir, newFileName);
        if (picFile.exists() && picFile.isFile() && picFile.length() > 0) {
            //已存在相同名字的文件时, 通过设备时间区分
            newFileName = BasisTimesUtils.getDeviceTime()
                    .replace(" ", "_")
                    .replace(":", "-")
                    + "_" + fileName;
            return checkFile(appDir, fileName, newFileName);
        } else {
            return picFile;
        }
    }

}
