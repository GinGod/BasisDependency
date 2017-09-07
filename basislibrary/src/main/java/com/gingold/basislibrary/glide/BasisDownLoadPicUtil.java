package com.gingold.basislibrary.glide;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.utils.BasisFileUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Glide下载图片
 */
public class BasisDownLoadPicUtil {

    public static void downLoadPic(Context context, final Bitmap bmp, String fileName, final BasisCallBack callBack) {
        if (bmp == null) {
            failure(callBack);
            return;
        }

        //保存的文件夹位置(注意小米手机必须这样获得public绝对路径)
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        String dirName = "GlideDownload";
        File appDir = new File(file, dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //保存的文件位置
        if (TextUtils.isEmpty(fileName)) {
            fileName = System.currentTimeMillis() + "";
        }
        final File picFile = checkFile(appDir, fileName, fileName, ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(picFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);//保存原图
            fos.flush();

            success(bmp, callBack, picFile);//保存成功
            notifyFileChange(context, picFile);//通知更新
        } catch (Exception e) {
            e.printStackTrace();
            failure(callBack);//失败
        } finally {
            if (fos != null) {//关流, 释放资源
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void downLoadGif(Context context, File originFile, String fileName, final BasisCallBack callBack) {
        if (originFile == null || originFile.length() < 252) {//glide默认存在空缓存文件, 大小 52 byte
            failure(callBack);
        }

        //保存的文件夹位置(注意小米手机必须这样获得public绝对路径)
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        String dirName = "GlideDownload";
        File appDir = new File(file, dirName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }

        //保存的文件位置
        if (TextUtils.isEmpty(fileName)) {
            fileName = System.currentTimeMillis() + "";
        }
        final File picFile = checkFile(appDir, fileName, fileName, ".gif");

        //保存文件到本地
        final int i = BasisFileUtils.copyFile(originFile, picFile);

        //获取静态bitmap图片
        final Bitmap bitmap = BasisFileUtils.getDiskBitmap(picFile.getAbsolutePath());

        if (i == -1) {//储存失败
            failure(callBack);
        } else {
            success(bitmap, callBack, picFile);//成功
            notifyFileChange(context, picFile);//通知更新
        }
    }

    /**
     * 检测重复文件
     */
    private static File checkFile(File appDir, String fileName, String newFileName, String type) {
        File picFile = new File(appDir, newFileName + type);
        if (picFile.exists() && picFile.isFile() && picFile.length() > 0) {
            //已存在相同名字的文件时, 通过添加时间区分
            newFileName = fileName + "_" + BasisTimesUtils.getDeviceTime().replace(" ", "");
            return checkFile(appDir, fileName, newFileName, type);
        } else {
            return picFile;
        }
    }

    /**
     * 下载成功
     */
    private static void success(final Bitmap bmp, final BasisCallBack callBack, final File picFile) {
        BasisBaseUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.success(bmp, picFile, picFile.getAbsolutePath());
                }
            }
        }, 1000);
    }

    /**
     * 下载失败
     */
    public static void failure(final BasisCallBack callBack) {
        BasisBaseUtils.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.failure();
                }
            }
        }, 1000);
    }

    /**
     * 通知图库有文件更新
     */
    private static void notifyFileChange(Context context, File picFile) {
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(picFile.getPath()))));
//        MediaScannerConnection.scanFile(context,
//                new String[]{picFile.getAbsolutePath()}, null, null);
    }

}
