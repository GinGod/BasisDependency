package com.gingold.basislibrary.utils.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.gingold.basislibrary.Base.BasisBaseViewUtils;

/**
 * 自定义Dialog 显示工具类
 */

public class BasisSelfDialogUtils {

    private static BasisSelfDialogUtils basisDialog;
    private static Dialog selfDialog;
    private static BasisDialogListenrer dialogListenrer;

    private BasisSelfDialogUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisSelfDialogUtils getInstance() {
        synchronized (BasisSelfDialogUtils.class) {
            if (basisDialog == null) {
                basisDialog = new BasisSelfDialogUtils();
            }
        }
        return basisDialog;
    }

    /**
     * 创建
     */
    public static BasisSelfDialogUtils build(final Context context, int layoutId, final BasisOnSelfDialogListener listener) {
        if (selfDialog != null) {//取消上一个dialog
            selfDialog.dismiss();
        }
        basisDialog = null;
        selfDialog = null;
        dialogListenrer = null;

        selfDialog = new AlertDialog.Builder(context).create();
        selfDialog.show();//dialog需显示后才能更改界面, 否则报错

//        Window window = selfDialog.getWindow();
//        window.setContentView(layoutId);
        selfDialog.setContentView(layoutId);

        if (listener != null) {//自定义逻辑处理
            listener.onSelfViewListener(selfDialog, new BasisBaseViewUtils(selfDialog));
        }

        // dismiss监听
        selfDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                }
                basisDialog = null;
                selfDialog = null;
                dialogListenrer = null;
            }
        });

        // 监听cancel事件
        selfDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onCancle();
                }
            }
        });

        return getInstance();
    }

    /**
     * @param cancelable   设置是否可以通过点击Back键取消
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog进度条
     */
    public BasisSelfDialogUtils setCancelable(boolean cancelable, boolean touchOutSide) {
        selfDialog.setCancelable(cancelable);// 设置是否可以通过点击Back键取消
        selfDialog.setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        return getInstance();
    }

    /**
     * 设置dismiss监听
     */
    public BasisSelfDialogUtils setListener(BasisDialogListenrer dialogListenrer) {
        this.dialogListenrer = dialogListenrer;
        return getInstance();
    }

    /**
     * 显示dialog
     */
    public Dialog show() {
        return selfDialog;
    }

    /**
     * dismiss dialog
     */
    public static void dismiss() {
        if (selfDialog != null) {
            selfDialog.dismiss();
        }
    }
}
