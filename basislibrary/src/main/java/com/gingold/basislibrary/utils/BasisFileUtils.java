package com.gingold.basislibrary.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * 文件工具类
 */

public class BasisFileUtils {
    public static final Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 在SD卡创建文件夹, 并返回其绝对路径
     */
    public static String mkdir(String dirName) {
        String dirPath = Environment.getExternalStorageDirectory() + "/" + dirName;
        File fileDir = new File(dirPath);
        if (!fileDir.exists()) {// 判断文件目录是否存在,不存在则创建该目录
            fileDir.mkdir();
        }
        return dirPath;
    }

    /**
     * 在图片文件夹中创建文件夹, 并返回其绝对路径
     */
    public static String mkPicDir(String dirName) {
        File picDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        File fileDir = new File(picDir, dirName);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return fileDir.getAbsolutePath();
    }

    /**
     * 写日志
     *
     * @param file   日志文件
     * @param record 日志内容
     * @param append false: 新日志; true: 追加日志
     */
    public static void writeRecord(File file, String record, boolean append) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(record);
            bw.newLine();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件拷贝
     *
     * @param copyFileCallBack 监听中的方法已处理, 默认在主线程中执行
     */
    public static void copyFile(File fromFile, File toFile, onCopyFileCallBack copyFileCallBack) {
        try {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            copy(fosfrom, fosto, copyFileCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            failure(e, copyFileCallBack);
        }
    }

    /**
     * 拷贝assets文件到本地
     *
     * @param copyFileCallBack 监听中的方法已处理, 默认在主线程中执行
     */
    public static void copyAssetsFile(Context context, String assetsFileName, File toFile, final onCopyFileCallBack copyFileCallBack) {
        try {
            InputStream fosfrom = context.getAssets().open(assetsFileName);
            OutputStream fosto = new FileOutputStream(toFile);
            copy(fosfrom, fosto, copyFileCallBack);
        } catch (Exception e) {
            e.printStackTrace();
            failure(e, copyFileCallBack);
        }
    }

    /**
     * 复制拷贝
     *
     * @param copyFileCallBack 监听中的方法已处理, 默认在主线程中执行
     */
    public static void copy(final InputStream fosfrom, final OutputStream fosto, final onCopyFileCallBack copyFileCallBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    int totalSize = fosfrom.available();
                    int currentSize = 0;
                    int len = 1024 * 8;
                    byte bt[] = new byte[len];
                    int length;
                    while ((length = fosfrom.read(bt)) > 0) {
                        fosto.write(bt, 0, length);
//                        SystemClock.sleep(10);//测试使用
                        currentSize = currentSize + len;
                        process(totalSize, currentSize, copyFileCallBack);
                    }
                    fosfrom.close();
                    fosto.close();
                    success(copyFileCallBack);
                } catch (final Exception e) {
                    e.printStackTrace();
                    failure(e, copyFileCallBack);
                } finally {
                    if (fosfrom != null) {
                        try {
                            fosfrom.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    if (fosto != null) {
                        try {
                            fosto.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private static void failure(final Exception e, final onCopyFileCallBack copyFileCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (copyFileCallBack != null) {
                    copyFileCallBack.onFailure(BasisCommonUtils.getExceptionInfo(e));
                }
            }
        });
    }

    private static void success(final onCopyFileCallBack copyFileCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (copyFileCallBack != null) {
                    copyFileCallBack.onSuccess();
                }
            }
        });
    }

    private static void process(final int totalSize, final int currentSize, final onCopyFileCallBack copyFileCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (copyFileCallBack != null && totalSize > 0) {
                    copyFileCallBack.onProgress(totalSize, currentSize, currentSize * 100 / totalSize);
                }
            }
        });
    }

    /**
     * 获取SD卡图片(原图)
     *
     * @return Bitmap
     */
    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
        }
        return bitmap;
    }

    /**
     * 文件复制监听器
     */
    public interface onCopyFileCallBack {
        void onSuccess();

        void onFailure(String errorMessage);

        void onProgress(long totalSize, long currentSize, long progress);
    }

    /**
     * 关闭流
     */
    public static void close(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputStream = null;
    }

    /**
     * 关闭流
     */
    public static void close(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        outputStream = null;
    }

    /**
     * 关闭流
     */
    public static void close(Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        reader = null;
    }

    /**
     * 关闭流
     */
    public static void close(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = null;
    }

    /**
     * 关闭流
     */
    public static void close(Cursor cursor) {
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor = null;//置空
    }

}
