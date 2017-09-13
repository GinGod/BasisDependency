package com.gingold.basislibrary.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by MJG on 2017/9/13.
 */

public class BasisProgressDialog {

    private static ProgressDialog mProgressDialog;
    private static BasisProgressDialog basisProgressDialog;

    private BasisProgressDialog() {
    }

    /**
     * 单例模式
     */
    private static BasisProgressDialog getInstance() {
        synchronized (BasisProgressDialog.class) {
            if (basisProgressDialog == null) {
                basisProgressDialog = new BasisProgressDialog();
            }
        }
        return basisProgressDialog;
    }

    /**
     * 创建ProgressDialog
     */
    public static BasisProgressDialog build(final Context context) {
        if (mProgressDialog != null) {//取消上一个dialog
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }

        mProgressDialog = new ProgressDialog(context);

        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条

        /*// dismiss监听
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                BasisBaseUtils.toast(context, "dismiss");
            }
        });

        // 监听Key事件被传递给dialog
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
                BasisBaseUtils.toast(context, "Key");
                return false;
            }
        });

        // 监听cancel事件
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                BasisBaseUtils.toast(context, "cancel");
            }
        });

        //设置可点击的按钮，最多有三个(默认情况下)
        mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BasisBaseUtils.toast(context, "确定");
                    }
                });

        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BasisBaseUtils.toast(context, "取消");
                    }
                });
        mProgressDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "中立",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BasisBaseUtils.toast(context, "中立");
                    }
                });*/
        mProgressDialog.setMessage("加载中...");
        return getInstance();
    }

    /**
     * @param cancelable   设置是否可以通过点击Back键取消
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog进度条
     */
    public BasisProgressDialog setCancelable(boolean cancelable, boolean touchOutSide) {
        mProgressDialog.setCancelable(cancelable);// 设置是否可以通过点击Back键取消
        mProgressDialog.setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        return getInstance();
    }

    public BasisProgressDialog setTitle(int drawableId, String title) {
        mProgressDialog.setIcon(drawableId);
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        mProgressDialog.setTitle(title);
        return getInstance();
    }

    public BasisProgressDialog setMessage(String message) {
        mProgressDialog.setMessage(message);
        return getInstance();
    }

    public AlertDialog show() {
        mProgressDialog.show();
        return mProgressDialog;
    }

    public static void dismiss() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
