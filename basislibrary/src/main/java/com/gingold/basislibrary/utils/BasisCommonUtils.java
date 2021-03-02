package com.gingold.basislibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.gingold.basislibrary.BuildConfig;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 通用方法工具类
 */

public class BasisCommonUtils {

    /**
     * 打电话
     *
     * @param phone 电话号码
     */
    public static void callTel(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }

    /**
     * 发短信
     *
     * @param phone   电话号码
     * @param message 信息内容
     */
    public static void sendSMS(Context context, String phone, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        intent.putExtra("sms_body", message);
        context.startActivity(intent);
    }

    /**
     * 获取异常信息
     *
     * @param e 异常
     */
    public static String getExceptionInfo(Exception e) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.close();
        String info = writer.toString();
        return info;
    }

    /**
     * 安装本地.apk文件
     */
    public static void installApk(Context context, File apkFile) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判读版本是否在7.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //provider authorities
                Uri apkUri = FileProvider.getUriForFile(context.getApplicationContext(),
                        context.getPackageName() + ".fileProvider", apkFile);
                apkUri = BasisCommonUtils.getUri(context, apkFile);
                //Granting Temporary Permissions to a URI
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Uri
     *
     * @return
     */
    public static Uri getUri(Context context, File file) {
        try {
            Uri uri = null;
            //android 7.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, BuildConfig.LIBRARY_PACKAGE_NAME.concat(".provider"), file);
            } else {
                uri = Uri.fromFile(file);
            }
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
