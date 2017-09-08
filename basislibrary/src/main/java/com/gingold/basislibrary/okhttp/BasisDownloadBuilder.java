package com.gingold.basislibrary.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.text.TextUtils;

import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求参数为jsonStr数据
 */

public class BasisDownloadBuilder extends BasisBaseUtils {
    private String url;//网址
    private String fileName;//文件名

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
     * 储存的文件名
     */
    public BasisDownloadBuilder fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * 建立请求
     */
    public BasisDownloadBuilder build() {
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        mCall = mOkHttpClient.newCall(request);
        BasisLogUtils.e("url: " + url);
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

    /**
     * 执行请求(默认下载的是图片)
     */
    public void execute(final BasisBitmapCallback basisCallback) {
        enqueue(basisCallback);
    }

    private void enqueue(final Object basisCallback) {
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                String message = "";
                if (e != null) {
                    message = e.getMessage();
                }
                BasisLogUtils.e("failure: " + message);
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
                    String dirPath = Environment.getExternalStorageDirectory() + "/Download";
                    File fileDir = new File(dirPath);
                    if (!fileDir.exists()) {// 判断文件目录是否存在,不存在则创建该目录
                        fileDir.mkdir();
                    }

                    //下载文件名
                    if (TextUtils.isEmpty(fileName)) {
                        if (TextUtils.isEmpty(url)) {//网址为空
                            failure(call, new IllegalArgumentException("网址为空"), "网址为空", basisCallback);
                            return;
                        } else {
                            //截取网址最后15位作为下载的文件名
                            String replace = url.replace("\\", "").replace("/", "");
                            if (replace.length() > 15) {
                                fileName = replace.substring(replace.length() - 15);
                            } else {
                                fileName = replace;
                            }
                        }
                    }

                    //下载的文件
                    File file = checkFile(fileDir, fileName, fileName, "");
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
    private void success(final Call call, final Response response, final String filePath, final Object basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (basisCallback != null) {
                    if (basisCallback instanceof BasisDownloadCallback) {
                        ((BasisDownloadCallback) basisCallback).success(call, response, filePath);
                    } else if (basisCallback instanceof BasisBitmapCallback) {
                        ((BasisBitmapCallback) basisCallback).success(call, response, getBitmap(filePath), filePath);
                    }
                }
            }
        });
    }

    /**
     * 下载进度
     */
    private void process(final long totalSize, final long process, final Object basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                //下载进度
                if (basisCallback != null) {
                    if (basisCallback instanceof BasisDownloadCallback) {
                        ((BasisDownloadCallback) basisCallback).progress(totalSize, process, process * 100 / totalSize);
                    } else if (basisCallback instanceof BasisBitmapCallback) {
                        ((BasisBitmapCallback) basisCallback).progress(totalSize, process, process * 100 / totalSize);
                    }
                }
            }
        });
    }

    /**
     * 下载失败
     */
    private void failure(final Call call, final Exception e, final String message, final Object basisCallback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (basisCallback != null) {
                    if (basisCallback instanceof BasisDownloadCallback) {
                        ((BasisDownloadCallback) basisCallback).failure(call, e, message);
                    } else if (basisCallback instanceof BasisBitmapCallback) {
                        ((BasisBitmapCallback) basisCallback).failure(call, e, message);
                    }
                }
            }
        });
    }

    /**
     * 检测重复文件
     */
    private static File checkFile(File appDir, String fileName, String newFileName, String type) {
        File picFile = new File(appDir, newFileName + type);
        if (picFile.exists() && picFile.isFile() && picFile.length() > 0) {
            //已存在相同名字的文件时, 通过设备时间区分
            newFileName = BasisTimesUtils.getDeviceTime().replace(" ", "") + "_" + fileName;
            return checkFile(appDir, fileName, newFileName, type);
        } else {
            return picFile;
        }
    }

    /**
     * 获取图片
     */
    private Bitmap getBitmap(String filePath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        newOpts.inSampleSize = 5;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, newOpts);
        int degree = readPictureDegree(filePath);
        if (degree <= 0) {
            return bmp;
        } else {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0,
                    bmp.getWidth(), bmp.getHeight(), matrix, true);
            return resizedBitmap;
        }
    }

    /**
     * 读取照片exif信息中的旋转角度
     * http://www.eoeandroid.com/thread-196978-1-1.html
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
