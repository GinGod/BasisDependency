package com.gingold.basislibrary.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.gingold.basislibrary.R;

import java.util.ArrayList;

/**
 * ProgressBarLoading 显示工具类
 * dissmiss监听 {@link #setListener(BasisDialogListenrer)}
 *
 * @see BasisDialogUtils dismiss注意事项
 */

public class BasisPBLoadingUtils {

    private static BasisPBLoadingUtils basisPBLoadingUtils;
    private static ArrayList<Dialog> dialogLists = new ArrayList<>();//dialog集合
    private static BasisDialogListenrer dialogListenrer;//最后一个显示的dialog的监听器才起作用

    private BasisPBLoadingUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisPBLoadingUtils getInstance() {
        synchronized (BasisPBLoadingUtils.class) {
            if (basisPBLoadingUtils == null) {
                basisPBLoadingUtils = new BasisPBLoadingUtils();
            }
        }
        return basisPBLoadingUtils;
    }

    /**
     * 创建
     */
    public static BasisPBLoadingUtils build(final Context context) {
        dialogListenrer = null;//将前面dialog的监听器置空

        Dialog pbLoadingDialog = new Dialog(context, R.style.dialog);
        pbLoadingDialog.setContentView(R.layout.pb_loading);
        pbLoadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // dismiss监听
        pbLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dialogListenrer != null) {
                    dialogListenrer.onDismiss();
                    dialogListenrer = null;//只允许最后一个显示的dialog的监听器起作用
                }
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

        dialogLists.add(pbLoadingDialog);//将dialog添加进集合
        return getInstance();
    }

    /**
     * 设置显示信息
     */
    public BasisPBLoadingUtils setMessage(String message) {
        if (dialogLists.size() > 0) {//操作最后显示的Dialog
            TextView msg = (TextView) dialogLists.get(dialogLists.size() - 1).findViewById(R.id.tv_pb_loading_message);
            msg.setText(message);
        }
        return getInstance();
    }

    /**
     * @param cancelable   设置是否可以通过点击Back键取消
     * @param touchOutSide 设置在点击Dialog外是否取消Dialog进度条
     */
    public BasisPBLoadingUtils setCancelable(boolean cancelable, boolean touchOutSide) {
        if (dialogLists.size() > 0) {
            dialogLists.get(dialogLists.size() - 1).setCancelable(cancelable);// 设置是否可以通过点击Back键取消
            dialogLists.get(dialogLists.size() - 1).setCanceledOnTouchOutside(touchOutSide);// 设置在点击Dialog外是否取消Dialog进度条
        }
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
        if (dialogLists.size() > 0) {
            dialogLists.get(dialogLists.size() - 1).show();
        }
        return dialogLists.get(dialogLists.size() - 1);
    }

    /**
     * dismiss dialog
     */
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
