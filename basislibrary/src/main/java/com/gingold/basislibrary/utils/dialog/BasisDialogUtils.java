package com.gingold.basislibrary.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.gingold.basislibrary.R;

/**
 * SimpleDialog 显示工具类
 */

public class BasisDialogUtils {

    private static BasisDialogUtils basisDialog;
    private static Dialog simpleDialog;
    private static BasisDialogListenrer dialogListenrer;

    private BasisDialogUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisDialogUtils getInstance() {
        synchronized (BasisDialogUtils.class) {
            if (basisDialog == null) {
                basisDialog = new BasisDialogUtils();
            }
        }
        return basisDialog;
    }

    /**
     * 创建
     */
    public static BasisDialogUtils build(final Context context, String title, String message, final BasisDSClickListener listener) {
        if (simpleDialog != null) {//取消上一个dialog
            simpleDialog.dismiss();
            simpleDialog = null;
        }

        simpleDialog = new Dialog(context, R.style.dialog);
        simpleDialog.setContentView(R.layout.dialog_simple);
        simpleDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ((TextView) simpleDialog.findViewById(R.id.tv_dialog_simple_title)).setText(title);
        ((TextView) simpleDialog.findViewById(R.id.tv_dialog_simple_message)).setText(message);

        ((TextView) simpleDialog.findViewById(R.id.tv_dialog_simple_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simpleDialog != null) {
                    simpleDialog.dismiss();
                }
                if (listener != null) {
                    listener.onConfirm();
                }
            }
        });

        ((TextView) simpleDialog.findViewById(R.id.tv_dialog_simple_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simpleDialog != null) {
                    simpleDialog.dismiss();
                }
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        dialogListenrer = null;//初始化监听
        // dismiss监听
        simpleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                }
            }
        });

        // 监听Key事件被传递给dialog
        simpleDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
//                BasisBaseUtils.toast(context, "Key" + keyCode);
                return false;
            }
        });

        // 监听cancel事件
        simpleDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

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
    public BasisDialogUtils setCancelable(boolean cancelable, boolean touchOutSide) {
        simpleDialog.setCancelable(cancelable);// 设置是否可以通过点击Back键取消
        simpleDialog.setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        return getInstance();
    }

    /**
     * 设置dismiss监听
     */
    public BasisDialogUtils setListener(BasisDialogListenrer dialogListenrer) {
        this.dialogListenrer = dialogListenrer;
        return getInstance();
    }

    /**
     * 显示dialog
     */
    public Dialog show() {
        simpleDialog.show();
        return simpleDialog;
    }

    /**
     * dismiss dialog
     */
    public static void dismiss() {
        if (simpleDialog != null) {
            simpleDialog.dismiss();
        }
    }
}
