package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gingold.basislibrary.utils.BasisLogUtils;

import java.util.List;

/**
 * BasisBaseFragment
 *
 * @author
 */
public abstract class BasisBaseFragment extends Fragment implements OnClickListener {
    public View mBaseView;//Fragmnet根部局

    public Application app;//application
    public Activity mActivity;// 上下文
    public Context context;// 上下文
    public Handler mBasisHandler;//Handler

    public final String TAG = this.getClass().getSimpleName() + "TAG: ";//类名日志tag

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            beforeCreateView();// 初始化设置
            mBaseView = setupViewLayout(inflater);// 初始化布局
            initView(mBaseView);// 初始化UI
            initListener();// 事件监听
            initData();// 逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBaseView;
    }

    /**
     * 初始化数据
     */
    public void beforeCreateView() {
        try {
            mActivity = getActivity();
            app = mActivity.getApplication();
            context = mActivity;
            mBasisHandler = new Handler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化根部局
     *
     * @return mBaseView(根部局View)
     */
    public abstract View setupViewLayout(LayoutInflater inflater);


    /**
     * 初始化UI
     */
    public abstract void initView(View mBaseView);

    /**
     * 事件监听
     */
    public void initListener() {
    }

    /**
     * 逻辑
     */
    public void initData() {
    }

    /**
     * 获取控件, 并设置可点击
     */
    public <T extends View> T getView(int id) {
        View view = mBaseView.findViewById(id);
        view.setClickable(true);
        view.setOnClickListener(this);
        return (T) view;
    }

    /**
     * 获取控件, 不设置可点击
     */
    public <T extends View> T getViewNoClickable(int id) {
        View view = mBaseView.findViewById(id);
        return (T) view;
    }

    /**
     * 设置默认监听
     */
    public void setOnClickListener(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]);
        }
    }

    /**
     * Toast
     */
    public void toast(String message) {
        BasisBaseUtils.toast(mActivity, message);
    }

    /**
     * 获取文本框的字符串
     */
    public String getTvText(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * 获取文本框的字符串
     */
    public String getTvText(int id) {
        return ((TextView) getViewNoClickable(id)).getText().toString().trim();
    }

    /**
     * 打开新页面
     */
    public void startActivity(Class<?> cls) {
        startActivity(new Intent(mActivity, cls));
    }

    /**
     * 打开新页面并关闭当前页面
     */
    public void startActivityAndFinish(Class<?> cls) {
        startActivity(new Intent(mActivity, cls));
        mActivity.finish();
    }

    /**
     * 设置text
     */
    public void setTVText(String text, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setText(text);
        }
    }

    /**
     * 设置text
     */
    public void setTVText(String text, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getViewNoClickable(ids[i]))).setText(text);
        }
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
     * 隐藏键盘
     */
    public void hideInputMethod() {
        View view = mActivity.getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 日志打印
     */
    public void loge(String message) {
        BasisLogUtils.e(TAG, message);
    }

    @Override
    public void onClick(View v) {
        try {
            onClick(v.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击事件
     */
    public void onClick(int id) {
    }
}
