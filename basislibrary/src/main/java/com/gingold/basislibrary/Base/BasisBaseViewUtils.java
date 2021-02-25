package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 与Activity相关常用工具类
 */

public class BasisBaseViewUtils {
    private View mView;
    private Activity mActivity;
    private Dialog mDialog;
    private int state = 0;

    public BasisBaseViewUtils(View view) {
        state = 1;
        mView = view;
    }

    public BasisBaseViewUtils(Activity activity) {
        state = 2;
        mActivity = activity;
    }

    public BasisBaseViewUtils(Dialog dialog) {
        state = 3;
        mDialog = dialog;
    }

    /**
     * 设置控件可以触摸编辑
     */
    public BasisBaseViewUtils setEnabledTrue(View... views) {
        BasisBaseUtils.setEnabledTrue(views);
        return this;
    }

    /**
     * 设置控件可以触摸编辑
     */
    public BasisBaseViewUtils setEnabledTrue(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setEnabled(true);
        }
        return this;
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public BasisBaseViewUtils setEnabledFalse(View... views) {
        BasisBaseUtils.setEnabledFalse(views);
        return this;
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public BasisBaseViewUtils setEnabledFalse(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setEnabled(false);
        }
        return this;
    }

    /**
     * 设置控件可以点击
     */
    public BasisBaseViewUtils setClickableTrue(View... views) {
        BasisBaseUtils.setClickableTrue(views);
        return this;
    }

    /**
     * 设置控件可以点击
     */
    public BasisBaseViewUtils setClickableTrue(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setClickable(true);
        }
        return this;
    }

    /**
     * 设置控件不可以点击
     */
    public BasisBaseViewUtils setClickableFalse(View... views) {
        setClickableFalse(views);
        return this;
    }

    /**
     * 设置控件不可以点击
     */
    public BasisBaseViewUtils setClickableFalse(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setClickable(false);
        }
        return this;
    }

    /**
     * 设置控件可见
     */
    public BasisBaseViewUtils setVisible(View... views) {
        BasisBaseUtils.setVisible(views);
        return this;
    }

    /**
     * 设置控件可见
     */
    public BasisBaseViewUtils setVisible(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public BasisBaseViewUtils setGone(View... views) {
        BasisBaseUtils.setGone(views);
        return this;
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public BasisBaseViewUtils setGone(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public BasisBaseViewUtils setInVisible(View... views) {
        BasisBaseUtils.setInVisible(views);
        return this;
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public BasisBaseViewUtils setInVisible(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.INVISIBLE);
        }
        return this;
    }

    /**
     * 设置控件背景颜色
     */
    public BasisBaseViewUtils setBGColor(int color, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setBackgroundColor(getColorById(color));
        }
        return this;
    }

    /**
     * 设置控件背景颜色
     */
    public BasisBaseViewUtils setBGColor(int color, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setBackgroundColor(getColorById(color));
        }
        return this;
    }

    /**
     * 设置控件背景资源
     */
    public BasisBaseViewUtils setBGResource(int resource, View... views) {
        BasisBaseUtils.setBGResource(resource, views);
        return this;
    }

    /**
     * 设置控件背景资源
     */
    public BasisBaseViewUtils setBGResource(int resource, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setBackgroundResource(resource);
        }
        return this;
    }

    /**
     * 设置控件背景Drawable资源
     */
    public BasisBaseViewUtils setBGDrawable(int resource, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setBackgroundDrawable(getDrawableById(resource));
        }
        return this;
    }

    /**
     * 设置控件背景Drawable资源
     */
    public BasisBaseViewUtils setBGDrawable(int resource, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setBackgroundDrawable(getDrawableById(resource));
        }
        return this;
    }

    /**
     * 设置文本框字体颜色
     */
    public BasisBaseViewUtils setTVTextColor(int color, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setTextColor(getColorById(color));
        }
        return this;
    }

    /**
     * 设置文本框字体颜色
     */
    public BasisBaseViewUtils setTVTextColor(int color, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getView(ids[i]))).setTextColor(getColorById(color));
        }
        return this;
    }

    /**
     * 设置下划线
     */
    public BasisBaseViewUtils setUnderline(TextView... views) {
        BasisBaseUtils.setUnderline(views);
        return this;
    }

    /**
     * 设置下划线
     */
    public BasisBaseViewUtils setUnderline(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getView(ids[i]))).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
        return this;
    }

    /**
     * 设置删除线
     */
    public BasisBaseViewUtils setStrike(TextView... views) {
        BasisBaseUtils.setStrike(views);
        return this;
    }

    /**
     * 设置删除线
     */
    public BasisBaseViewUtils setStrike(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getView(ids[i]))).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
        return this;
    }

    /**
     * 获取文本框的字符串(与{@link #getTvText(TextView)}方法完全一样)
     */
    public String getText(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * 获取文本框的字符串(与{@link #getText(TextView)}方法完全一样)
     */
    public String getTvText(TextView view) {
        return getText(view);
    }

    /**
     * 获取文本框的字符串
     */
    public String getTvText(int id) {
        return ((TextView) getView(id)).getText().toString().trim();
    }

    /**
     * 获取控件, 不设置可点击(针对ListView, GridView等)
     */
    public <T extends View> T getView(int id) {
        View view = null;
        if (state == 1) {
            view = mView.findViewById(id);
        } else if (state == 2) {
            view = mActivity.findViewById(id);
        } else if (state == 3) {
            view = mDialog.findViewById(id);
        }
        return (T) view;
    }

    /**
     * TextView查找并设置监听
     */
    @Deprecated
    public TextView findTextView(int id) {
        TextView view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * EditText查找并设置监听
     */
    @Deprecated
    public EditText findEditText(int id) {
        EditText view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * ImageView查找并设置监听
     */
    @Deprecated
    public ImageView findImageView(int id) {
        ImageView view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * RelativeLayout查找并设置监听
     */
    @Deprecated
    public RelativeLayout findRelativeLayout(int id) {
        RelativeLayout view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * LinearLayout查找并设置监听
     */
    @Deprecated
    public LinearLayout findLinearLayout(int id) {
        LinearLayout view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * Button查找并设置监听
     */
    @Deprecated
    public Button findButton(int id) {
        Button view = getView(id);
        setClickableTrue(view);
        return view;
    }

    /**
     * Spinner查找
     */
    @Deprecated
    public Spinner findSpinner(int id) {
        return getView(id);
    }

    /**
     * ListView查找
     */
    @Deprecated
    public ListView findListView(int id) {
        return getView(id);
    }

    /**
     * GridView查找
     */
    @Deprecated
    public GridView findGridView(int id) {
        return getView(id);
    }

    /**
     * RecyclerView查找
     */
    @Deprecated
    //public RecyclerView findRecyclerView(int id) {
    //    return getView(id);
    //}

    /**
     * 设置text
     */
    public BasisBaseViewUtils setTVText(String text, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setText(text);
        }
        return this;
    }

    /**
     * 设置text
     */
    public BasisBaseViewUtils setTVText(String text, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getView(ids[i]))).setText(text);
        }
        return this;
    }

    /**
     * 设置text
     * <p>(使用{@link #setTVText(String, int...)} (int)}方法替代)
     */
    @Deprecated
    public BasisBaseViewUtils setTvText(int id, String text) {
        TextView view = getView(id);
        view.setText(showStr(text));
        return this;
    }

    /**
     * 设置text
     * <p>(使用{@link #setTVText(String, int...)} (int)}方法替代)
     */
    @Deprecated
    public BasisBaseViewUtils setEtText(int id, String text) {
        EditText view = getView(id);
        view.setText(showStr(text));
        return this;
    }

    /**
     * 设置点击事件
     */
    public BasisBaseViewUtils setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 设置触摸事件
     */
    public BasisBaseViewUtils setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 设置长按事件
     */
    public BasisBaseViewUtils setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * 显示String字符串,为空时返回""
     */
    public String showStr(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 获取Color
     *
     * @param id
     */
    public int getColorById(int id) {
        int colorId = 0;
        if (state == 1) {
            colorId = mView.getResources().getColor(id);
        } else if (state == 2) {
            colorId = mActivity.getResources().getColor(id);
        } else if (state == 3) {
            colorId = mDialog.getContext().getResources().getColor(id);
        }
        return colorId;
    }

    /**
     * 获取Drawable
     *
     * @param id
     */
    public Drawable getDrawableById(int id) {
        Drawable drawableId = null;
        if (state == 1) {
            drawableId = mView.getResources().getDrawable(id);
        } else if (state == 2) {
            drawableId = mActivity.getResources().getDrawable(id);
        } else if (state == 3) {
            drawableId = mDialog.getContext().getResources().getDrawable(id);
        }
        return drawableId;
    }

}
