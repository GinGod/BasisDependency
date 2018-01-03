package com.gingold.basislibrary.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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
}
