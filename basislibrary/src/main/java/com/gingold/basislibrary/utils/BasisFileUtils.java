package com.gingold.basislibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 */

public class BasisFileUtils {

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
     * @return 1 成功; -1 失败
     */
    public static int copyFile(File fromFile, File toFile) {
        InputStream fosfrom = null;
        OutputStream fosto = null;
        try {
            fosfrom = new FileInputStream(fromFile);
            fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024 * 2];
            int length;
            while ((length = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, length);
            }
            fosfrom.close();
            fosto.close();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
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
}
