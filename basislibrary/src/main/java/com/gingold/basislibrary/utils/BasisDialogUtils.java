package com.gingold.basislibrary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gingold.basislibrary.R;

/**
 * Dialog 显示工具类
 */

public class BasisDialogUtils {

    private static BasisDialogUtils basisDialog;
    public static AlertDialog alertDialog;

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
     * 创建Dialog
     */
    public static BasisDialogUtils show(final Context context, String title, String message, final BasisClickListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_simple, null);
        ((TextView) view.findViewById(R.id.tv_dialog_simple_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_dialog_simple_message)).setText(message);

        alertDialog = new AlertDialog.Builder(context).setView(view).show();

        ((TextView) view.findViewById(R.id.tv_dialog_simple_confirm)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (listener != null) {
                    listener.onConfirm();
                }
            }
        });

        ((TextView) view.findViewById(R.id.tv_dialog_simple_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (listener != null) {
                    listener.onCancel();
                }
            }
        });

        return getInstance();
    }
}
