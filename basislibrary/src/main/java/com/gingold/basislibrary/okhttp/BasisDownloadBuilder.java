package com.gingold.basislibrary.okhttp;

import android.text.TextUtils;

import com.gingold.basislibrary.Base.BasisBaseContants;
import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.utils.BasisFileUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;
import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 请求参数为jsonStr数据
 */

public class BasisDownloadBuilder extends BasisOkHttpBuilder {
    private String fileDirName = "Download";//文件夹名(默认Download)
    private String fileName = "";//文件名

    /**
     * 储存的文件夹
     */
    public BasisDownloadBuilder fileDirName(String fileDirName) {
        this.fileDirName = BasisBaseUtils.showStr(fileDirName);
        return this;
    }

    /**
     * 储存的文件名
     */
    public BasisDownloadBuilder fileName(String fileName) {
        this.fileName = BasisBaseUtils.showStr(fileName);
        return this;
    }

    /**
     * 建立请求
     */
    @Override
    public BasisDownloadBuilder build() {
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
                    builder.add(BasisBaseUtils.showStr(entry.getKey()), BasisBaseUtils.showStr(entry.getValue()));
                    content = content + BasisBaseUtils.showStr(entry.getKey()) + " : " + BasisBaseUtils.showStr(entry.getValue()) + " , ";//记录参数
                }
                requestBody = builder.build();
            }

            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        }

        mCall = mOkHttpClient.newCall(request);
        if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
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
                String message = "";
                if (e != null) {
                    message = e.getMessage();
                }
                if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
                    if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
                        BasisLogUtils.e("onFailure: " + message);
                    }
                }
                failure(call, e, message, basisCallback);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                BasisPBLoadingUtils.dismiss();
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
                        if (basisCallback instanceof BasisDownloadCallback) {
                            process(totalSize, currentSize, (BasisDownloadCallback) basisCallback);//进度
                        }
                    }
                    OutputStream.flush();

                    success(call, response, filePath, basisCallback);//下载完成
                    if (BasisBaseContants.OKHTTP_LOG_STATE && isLogState) {
                        BasisLogUtils.e("文件下载成功: " + filePath);
                    }
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
    private void success(final Call call, final Response response, final String filePath, final BasisCallback basisCallback) {
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
    private void failure(final Call call, final Exception e, final String message, final BasisCallback basisCallback) {
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
