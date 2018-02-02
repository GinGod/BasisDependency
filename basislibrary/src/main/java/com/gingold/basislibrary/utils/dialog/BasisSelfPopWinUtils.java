package com.gingold.basislibrary.utils.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.gingold.basislibrary.Base.BasisBaseViewUtils;

/**
 * 自定义 PopupWindow 显示工具类
 */

public class BasisSelfPopWinUtils {

    private static BasisSelfPopWinUtils basisPopWin;
    private static PopupWindow selfPopWin;
    private static BasisPopWinListener popWinListenrer;

    private BasisSelfPopWinUtils() {
    }

    /**
     * 单例模式
     */
    private static BasisSelfPopWinUtils getInstance() {
        synchronized (BasisSelfPopWinUtils.class) {
            if (basisPopWin == null) {
                basisPopWin = new BasisSelfPopWinUtils();
            }
        }
        return basisPopWin;
    }

    /**
     * 创建
     */
    public static BasisSelfPopWinUtils build(final Context context, int layoutId, final BasisOnSelfViewListener listener) {
        dismissP();//取消上一个popWin
        basisPopWin = null;
        selfPopWin = null;
        popWinListenrer = null;

        final View popupView = LayoutInflater.from(context).inflate(layoutId, null);

        selfPopWin = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, true);//允许获取焦点
        //默认点击外部不消失
//        selfPopWin.setBackgroundDrawable(new BitmapDrawable());
//        selfPopWin.setOutsideTouchable(false);//允许触摸外部或者返回键消失
        selfPopWin.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        selfPopWin.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        selfPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popWinListenrer != null) {
                    popWinListenrer.onDismiss();
                }
                basisPopWin = null;
                selfPopWin = null;
                popWinListenrer = null;
            }
        });

        //监听返回键, dismiss PopWin
        popupView.setFocusable(true);
        popupView.setFocusableInTouchMode(true);

        popupView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismissP();
                    return true;
                }
                return false;
            }
        });

        if (listener != null) {//自定义逻辑处理
            listener.onSelfViewListener(popupView, new BasisBaseViewUtils(popupView));
        }

        return getInstance();
    }

    /**
     * @param touchable 设置是否接收触摸事件 true 接收; false 不接收, 点击事件不会生效, 传递给下层控件
     */
    public BasisSelfPopWinUtils setTouchable(boolean touchable) {
        selfPopWin.setTouchable(touchable);//
        return getInstance();
    }

    /**
     * 设置在点击PopWin外取消PopWin
     */
    public BasisSelfPopWinUtils setOutsideTouchEnable() {
        selfPopWin.setBackgroundDrawable(new BitmapDrawable());
        selfPopWin.setOutsideTouchable(true);// 设置在点击PopWin外是否取消PopWin
        return getInstance();
    }

    /**
     * 设置dismiss监听
     */
    public BasisSelfPopWinUtils setListener(BasisPopWinListener dialogListenrer) {
        this.popWinListenrer = dialogListenrer;
        return getInstance();
    }

    /**
     * 显示PopWin
     */
    public PopupWindow showAsDropDown(View anchor, int xoff, int yoff) {
        selfPopWin.showAsDropDown(anchor, xoff, yoff);
        return selfPopWin;
    }

    public PopupWindow showAtLocation(View parent, int gravity, int x, int y) {
        selfPopWin.showAtLocation(parent, gravity, x, y);
        return selfPopWin;
    }

    /**
     * dismiss PopWin
     */
    public static void dismiss() {
        dismissP();
    }

    private static void dismissP() {
        try {
            if (selfPopWin != null) {
                selfPopWin.dismiss();
            }
        } catch (Exception e) {//一些特殊情况下dismiss方法会报异常
            e.printStackTrace();
        }
    }
}
