package com.gingold.basislibrary.utils.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

/**
 * ProgressDialog 显示工具类
 * dissmiss监听 {@link #setListener(BasisDialogListenrer)}
 *
 * @see BasisDialogUtils dismiss注意事项
 */

public class BasisProgressDialogUtils {

    private static BasisProgressDialogUtils basisProgressDialog;
    private static ArrayList<ProgressDialog> dialogLists = new ArrayList<>();//dialog集合
    private static BasisDialogListenrer dialogListenrer;//最后一个显示的dialog的监听器才起作用

    private BasisProgressDialogUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisProgressDialogUtils getInstance() {
        synchronized (BasisProgressDialogUtils.class) {
            if (basisProgressDialog == null) {
                basisProgressDialog = new BasisProgressDialogUtils();
            }
        }
        return basisProgressDialog;
    }

    /**
     * 创建ProgressDialog
     */
    public static BasisProgressDialogUtils build(final Context context) {
        dialogListenrer = null;

        ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条

        // dismiss监听
        mProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                    dialogListenrer = null;
                }
            }
        });

        // 监听cancel事件
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onCancle();
                }
            }
        });

        mProgressDialog.setMessage("加载中...");
        dialogLists.add(mProgressDialog);
        return getInstance();
    }

    /**
     * @param cancelable   设置是否可以通过点击Back键取消
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog进度条
     */
    public BasisProgressDialogUtils setCancelable(boolean cancelable, boolean touchOutSide) {
        if (dialogLists.size() > 0) {
            dialogLists.get(dialogLists.size() - 1).setCancelable(cancelable);// 设置是否可以通过点击Back键取消
            dialogLists.get(dialogLists.size() - 1).setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        }
        return getInstance();
    }

    /**
     * 设置进度条标题和图标
     */
    public BasisProgressDialogUtils setTitle(int drawableId, String title) {
        if (dialogLists.size() > 0) {
            // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
            dialogLists.get(dialogLists.size() - 1).setIcon(drawableId);
            dialogLists.get(dialogLists.size() - 1).setTitle(title);
        }
        return getInstance();
    }

    /**
     * 设置进度条显示信息
     */
    public BasisProgressDialogUtils setMessage(String message) {
        if (dialogLists.size() > 0) {
            dialogLists.get(dialogLists.size() - 1).setMessage(message);
        }
        return getInstance();
    }

    /**
     * 设置dismiss监听
     */
    public BasisProgressDialogUtils setListener(BasisDialogListenrer dialogListenrer) {
        this.dialogListenrer = dialogListenrer;
        return getInstance();
    }

    public AlertDialog show() {
        if (dialogLists.size() > 0) {
            dialogLists.get(dialogLists.size() - 1).show();
        }
        return dialogLists.get(dialogLists.size() - 1);
    }

    public static void dismiss() {
        for (int i = dialogLists.size() - 1; i >= 0; i--) {
            Dialog dialog = dialogLists.get(i);
            if (dialog != null) {
                dialog.dismiss();
            }
        }
        dialogLists.clear();
    }
}
