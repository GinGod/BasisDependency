package com.gingold.basislibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 程序版本工具类
 */
public class BasisVersionUtils {

    /**
     * 程序版本名
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                versionName = "";
            }
        } catch (Exception e) {
        }
        return versionName;
    }

    /**
     * 程序版本号
     */
    public static int getVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {
        }
        return versioncode;
    }

    /**
     * 设备信息
     */
    public static String getDeviceInfo() {
        return "设备型号: " + android.os.Build.MODEL + "\n"
                + "Android版本: " + android.os.Build.VERSION.RELEASE + "\n"
                + "Android API: " + android.os.Build.VERSION.SDK_INT + "\n";
    }
}
