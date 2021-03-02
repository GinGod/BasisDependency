package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gingold.basislibrary.utils.BasisLogUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * BasisBaseActivity
 *
 * @author
 */
public abstract class BasisBaseActivity extends AppCompatActivity implements OnClickListener {
    //自定义的标题栏布局
    public View iv_base_back;//标题左返回(允许是任意布局)
    public TextView tv_base_title;//标题中
    public TextView tv_base_right;//标题右

    public Application app;//application
    public BasisBaseActivity mActivity;// 上下文
    public Context context;// 上下文
    public Handler mBasisHandler;//Handler

    public final String TAG = this.getClass().getSimpleName() + "-TAG: ";//类名日志TAG
    public boolean isOnResumeState = true;//当前界面resume状态
    public boolean isOnStartState = true;//当前界面start状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            beforeSetContentView();// 初始化页面设置
            setupViewLayout();// 初始化布局
            initView();// 初始化UI
            initListener();// 事件监听
            initData();// 逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     */
    public void beforeSetContentView() {
        try {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
            app = getApplication();
            mActivity = BasisBaseActivity.this;
            context = BasisBaseActivity.this;
            mBasisHandler = new Handler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化布局
     */
    public abstract void setupViewLayout();

    /**
     * 初始化UI
     */
    public void initView() {
    }

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

    @Override
    protected void onResume() {
        super.onResume();
        isOnResumeState = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isOnStartState = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isOnStartState = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnResumeState = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isOnStartState = false;
    }

    /**
     * 初始化标题栏
     */
    public void initTitle(String title, String right, int iv_base_back, int tv_base_title, int tv_base_right) {
        try {
            this.iv_base_back = findViewById(iv_base_back);//标题左返回
            this.tv_base_title = findViewById(tv_base_title);//标题中
            this.tv_base_right = findViewById(tv_base_right);//标题右

            this.tv_base_title.setText(showStr(title));// 标题显示
            this.tv_base_right.setText(showStr(right));// 右边显示

            // 设置title左边监听
            this.iv_base_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        doTitleLeftListener();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // 设置title中间监听
            this.tv_base_title.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        doTitleMiddleListener();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // 设置title右边监听
            this.tv_base_right.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        doTitleRightListener();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * title左边监听(默认关闭当前页面)
     */
    public void doTitleLeftListener() {
        try {
            onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * title中间监听
     */
    public void doTitleMiddleListener() {
    }

    /**
     * title右边监听
     */
    public void doTitleRightListener() {
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
        onBackPressed();
    }

    /**
     * Toast
     */
    public void toast(String message) {
        BasisBaseUtils.toast(mActivity, message);
    }

    /**
     * 判断对象是否相等(对象需重写equals方法)
     *
     * @return
     */
    public boolean isequals(Object obj1, Object obj2) {
        return BasisBaseUtils.isequals(obj1, obj2);
    }

    /**
     * 获取文本框的字符串
     *
     * @return
     */
    public String getTvText(TextView view) {
        return view.getText().toString().trim();
    }

    /**
     * 获取文本框的字符串
     *
     * @return
     */
    public String getTvText(int id) {
        return ((TextView) getViewNoClickable(id)).getText().toString().trim();
    }

    /**
     * 获取控件, 并设置可点击(ListView, GridView等控件使用{@link #getViewNoClickable(int)})
     *
     * @return
     */
    public <T extends View> T getView(int id) {
        View view = findViewById(id);
        view.setClickable(true);
        view.setOnClickListener(this);
        return (T) view;
    }

    /**
     * 获取控件, 不设置可点击(针对ListView, GridView等)
     *
     * @return
     */
    public <T extends View> T getViewNoClickable(int id) {
        View view = findViewById(id);
        return (T) view;
    }

    /**
     * 设置text
     */
    public void setTVText(String text, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setText(showStr(text));
        }
    }

    /**
     * 设置text
     */
    public void setTVText(String text, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getViewNoClickable(ids[i]))).setText(showStr(text));
        }
    }

    /**
     * 显示String字符串,为空时返回""
     *
     * @return
     */
    public String showStr(String str) {
        return BasisBaseUtils.showStr(str);
    }

    /**
     * 隐藏键盘
     */
    public void hideInputMethod() {
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
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
