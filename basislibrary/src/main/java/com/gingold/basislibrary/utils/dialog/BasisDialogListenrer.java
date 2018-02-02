package com.gingold.basislibrary.utils.dialog;

/**
 * Dialog dismiss监听
 */

public abstract class BasisDialogListenrer {

    /**
     * Dialog 点击外部或返回键消失时才会回调, 同时会回调{@link #onDismiss()} 方法
     */
    public void onCancle() {

    }

    /**
     * Dialog消失后回调该方法
     * note: (Dialog消失是些微的耗时操作, 若紧接下个Dialog的show方法, 一般会回调下个Dialog的dismiss监听)
     */
    public abstract void onDismiss();
}
