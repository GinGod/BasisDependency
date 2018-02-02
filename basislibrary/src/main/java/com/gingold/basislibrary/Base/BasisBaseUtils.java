package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * 常用方法工具类
 */

public class BasisBaseUtils {

    public static final Handler mHandler = new Handler(Looper.getMainLooper());

    /**
     * 判断多个字符串是否为空
     *
     * @return true 至少有一个为空; false 全部不为空
     */
    public static boolean areEmpty(CharSequence... strs) {
        if (strs == null) {
            return true;
        }

        for (int i = 0; i < strs.length; i++) {
            if (TextUtils.isEmpty(strs[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 提示信息
     * @return true 为空; false 不为空
     */
    public static boolean isEmpty(Context context, CharSequence str, String message) {
        if (TextUtils.isEmpty(str)) {
            toast(context, message);
            return true;
        }
        return false;
    }

    /**
     * 判断多个字符串是否为空
     *
     * @return true 全不为空; false 至少有一个为空
     */
    public static boolean areNotEmpty(CharSequence... strs) {
        if (strs == null) {
            return false;
        }

        for (int i = 0; i < strs.length; i++) {
            if (TextUtils.isEmpty(strs[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断List不为空&&size>0
     *
     * @return true 不为空&&size>0
     */
    public static boolean areNotEmptyList(List list) {
        if (list != null && list.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 为空时的提示信息
     * @return true 不为空; false 为空
     */
    public static boolean isNotEmpty(Context context, CharSequence str, String message) {
        if (TextUtils.isEmpty(str)) {
            toast(context, message);
            return false;
        }
        return true;
    }

    /**
     * 判断对象是否为空
     *
     * @return true 不为空; false 为空
     */
    public static boolean areNotNull(Object... objs) {
        if (objs == null) {
            return false;
        }

        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置控件可以触摸编辑
     */
    public static void setEnabledTrue(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setEnabled(true);
        }
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public static void setEnabledFalse(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setEnabled(false);
        }
    }

    /**
     * 设置控件可以点击
     */
    public static void setClickableTrue(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setClickable(true);
        }
    }

    /**
     * 设置控件不可以点击
     */
    public static void setClickableFalse(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setClickable(false);
        }
    }

    /**
     * 设置控件可见
     */
    public static void setVisible(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public static void setGone(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public static void setInVisible(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置控件背景资源
     */
    public static void setBGResource(int resource, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setBackgroundResource(resource);
        }
    }

    /**
     * 设置下划线
     */
    public static void setUnderline(TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    /**
     * 设置删除线
     */
    public static void setStrike(TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    /**
     * Toast
     */
    public static void toast(Context context, String message) {
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将字符串转为int
     */
    public static int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将字符串转为int(抛出转换异常)
     */
    public static int parseIntWithE(String str) throws Exception {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将字符串转为long
     */
    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 将字符串转为long(抛出转换异常)
     */
    public static long parseLongWithE(String str) throws Exception {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将字符串转为Double
     */
    public static Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1d;
        }
    }

    /**
     * 将字符串转为Double(抛出转换异常)
     */
    public static Double parseDoubleWithE(String str) throws Exception {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 判断对象是否相等(对象需重写equals方法)
     */
    public static boolean isequals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj1 == obj2;
        } else {
            if (obj1.equals(obj2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 获取输入框的字符串
     */
    public static String getText(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * 执行延迟消息
     */
    public static void postDelayed(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
    }

    /**
     * 延迟获取焦点
     */
    public static void requestFocusDelayed(final TextView view, long delayMillis) {
        postDelayed(new Runnable() {

            @Override
            public void run() {
                view.requestFocus();
            }
        }, delayMillis);
    }

    /**
     * 显示String字符串,为空时返回""
     */
    public static String showStr(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 隐藏键盘
     */
    public static void hideInputMethod(Activity activity) {
        View view = activity.getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取string
     *
     * @param id
     */
    public static String getStringById(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 获取demin
     *
     * @param id
     */
    public static int getDimensionById(Context context, int id) {
        return (int) context.getResources().getDimension(id);
    }
}
