package com.gingold.basislibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件工具类
 */

public class BasisFileUtils {

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
     * 获取SD卡图片
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
