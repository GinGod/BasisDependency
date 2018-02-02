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
 * 标题和内容设置 {@link #build(Context, String, String, BasisDSClickListener)}
 * 确定和取消监听 {@link #build(Context, String, String, BasisDSClickListener)}
 * dissmiss监听 {@link #setListener(BasisDialogListenrer)}
 * note: 在一个页面连续创建一个以上dialog时, dialog消失是些微的耗时操作, 若紧接下个Dialog的show方法,
 *          一般会回调下个Dialog的dismiss监听, 这样会造成dismiss监听会发生错乱, 建议这种情况
 *          不要使用dissmiss监听 {@link #setListener(BasisDialogListenrer)}
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
        }
        basisDialog = null;
        simpleDialog = null;
        dialogListenrer = null;

        simpleDialog = new Dialog(context, R.style.dialog);//自定义主题, 可设置背景是否变暗
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

        // dismiss监听
        simpleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                }
                basisDialog = null;
                simpleDialog = null;
                dialogListenrer = null;
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
     * @param cancelable   设置是否可以通过点击Back键取消Dialog
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog
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
