package com.gingold.basislibrary.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.widget.TextView;

import com.gingold.basislibrary.R;

/**
 * ProgressBarLoading 显示工具类
 * dissmiss监听 {@link #setListener(BasisDialogListenrer)}
 * @see BasisDialogUtils dismiss注意事项
 */

public class BasisPBLoadingUtils {

    private static BasisPBLoadingUtils basisDialog;
    private static Dialog pbLoadingDialog;
    private static BasisDialogListenrer dialogListenrer;

    private BasisPBLoadingUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisPBLoadingUtils getInstance() {
        synchronized (BasisPBLoadingUtils.class) {
            if (basisDialog == null) {
                basisDialog = new BasisPBLoadingUtils();
            }
        }
        return basisDialog;
    }

    /**
     * 创建
     */
    public static BasisPBLoadingUtils build(final Context context) {
        if (pbLoadingDialog != null) {//取消上一个dialog
            pbLoadingDialog.dismiss();
        }
        basisDialog = null;
        pbLoadingDialog = null;
        dialogListenrer = null;

        pbLoadingDialog = new Dialog(context, R.style.dialog);
        pbLoadingDialog.setContentView(R.layout.pb_loading);
        pbLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // dismiss监听
        pbLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                }
                basisDialog = null;
                pbLoadingDialog = null;
                dialogListenrer = null;
            }
        });

        // 监听Key事件被传递给dialog
        pbLoadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {
//                BasisBaseUtils.toast(context, "Key" + keyCode);
                return false;
            }
        });

        // 监听cancel事件
        pbLoadingDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

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
     * 设置显示信息
     */
    public BasisPBLoadingUtils setMessage(String message) {
        TextView msg = (TextView) pbLoadingDialog.findViewById(R.id.tv_pb_loading_message);
        msg.setText(message);
        return getInstance();
    }

    /**
     * @param cancelable   设置是否可以通过点击Back键取消
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog进度条
     */
    public BasisPBLoadingUtils setCancelable(boolean cancelable, boolean touchOutSide) {
        pbLoadingDialog.setCancelable(cancelable);// 设置是否可以通过点击Back键取消
        pbLoadingDialog.setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        return getInstance();
    }

    /**
     * 设置dismiss监听
     */
    public BasisPBLoadingUtils setListener(BasisDialogListenrer dialogListenrer) {
        this.dialogListenrer = dialogListenrer;
        return getInstance();
    }

    /**
     * 显示dialog
     */
    public Dialog show() {
        pbLoadingDialog.show();
        return pbLoadingDialog;
    }

    /**
     * dismiss dialog
     */
    public static void dismiss() {
        if (pbLoadingDialog != null) {
            pbLoadingDialog.dismiss();
        }
    }
}
