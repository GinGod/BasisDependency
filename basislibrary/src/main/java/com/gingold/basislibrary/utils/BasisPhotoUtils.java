package com.gingold.basislibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.gingold.basislibrary.Base.BasisBaseUtils;

import java.io.File;

/**
 *
 */

public class BasisPhotoUtils {
    /**
     * 打开手机相机拍照, 需要重写onActivityResult, 并根据请求码处理结果
     *
     * @param activity    传当前页面的Activity对象
     * @param requestCode 请求码
     * @return 拍照储存照片的路径
     */
    public static String takePhoto(Activity activity, int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!outDir.exists()) {
                outDir.mkdir();
            }
            File outFile = new File(outDir, BasisTimesUtils.getDeviceTime()
                    .replace(" ", "_").replace("-", "_").replace(":", "_") + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            activity.startActivityForResult(intent, requestCode);
            return outFile.getAbsolutePath();
        } else {
            BasisBaseUtils.toast(activity, "请检查储存状态!");
            return "";
        }
    }

    /**
     * 选取手机相册图片, 需要重写onActivityResult, 并根据请求码处理结果
     *
     * @param activity    传当前页面的Activity对象
     * @param requestCode 请求码
     */
    public static void pickedPhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 解析返回的数据, 获取图片路径
     *
     * @return 图片的路径
     */
    public static String pickedPhotoResult(Activity activity, Intent data) {
        Uri uri = data.getData();
        if (uri != null) {
            return getRealPathFromURI(activity, uri);
        } else {
            return "";
        }
    }

    /**
     * 根据uri获取文件路径
     */
    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            // Do not call Cursor.close() on a cursor obtained using this method,
            // because the activity will do that for you at the appropriate time
            Cursor cursor = activity.managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }
}
