package com.gingold.basislibrary.utils.dialog;

/**
 * SimpleDialog 确认取消监听
 */
public abstract class BasisDSClickListener {
    /**
     * 确认按钮监听
     */
    public abstract void onConfirm();

    /**
     * 取消按钮监听
     */
    public void onCancel() {
    }
}
