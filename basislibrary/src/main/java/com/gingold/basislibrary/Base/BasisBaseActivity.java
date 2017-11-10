package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


/**
 * BasisBaseActivity
 */
public abstract class BasisBaseActivity extends Activity implements OnClickListener {
    //自定义的标题栏布局
    /**
     * 标题左返回
     */
    public ImageView iv_base_back;
    /**
     * 标题中
     */
    public TextView tv_base_title;
    /**
     * 标题右
     */
    public TextView tv_base_right;

    public Application app;//application
    public BasisBaseActivity mActivity;// 上下文
    public Context context;// 上下文
    public Handler mHandler;//Handler
    public Gson gson;//Gson

    private BroadcastReceiver mReceiver;//广播
    private IntentFilter mFilter;//广播接受过滤器
    private ArrayList<BroadcastReceiver> mReceiverList = new ArrayList<>();//广播集合

    /**
     * 类名日志TAG
     */
    public final String TAG = this.getClass().getSimpleName() + "-TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        initData();// 初始化数据
        setupViewLayout();// 初始化布局
        additionalInitData();// 附加初始化数据
        initView();// 初始化UI
        listener();// 事件监听
        logicDispose();// 逻辑
        additionalLogic();//附加逻辑
    }

    /**
     * 初始化数据
     */
    public void initData() {
        app = getApplication();
        mActivity = BasisBaseActivity.this;
        context = BasisBaseActivity.this;
        mHandler = new Handler();
        gson = new Gson();
    }

    /**
     * 初始化布局
     */
    public abstract void setupViewLayout();

    /**
     * 附加初始化数据
     */
    public void additionalInitData() {
    }

    /**
     * 初始化UI
     */
    public abstract void initView();

    /**
     * 事件监听
     */
    public abstract void listener();

    /**
     * 逻辑
     */
    public abstract void logicDispose();

    /**
     * 附加逻辑
     */
    public void additionalLogic() {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        destory();//activity destory前的操作
        super.onDestroy();
    }

    /**
     * activity destory前的操作(默认取消注册广播并清空所有mHander消息)
     */
    public void destory() {
        try {
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);//取消注册广播
            }

            removeAllReceiver();//取消所有已经添加注册的广播

            mHandler.removeCallbacksAndMessages(null);//清空所有消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化标题栏
     */
    public void initTitle(String title, String right, int iv_base_back, int tv_base_title, int tv_base_right) {
        this.iv_base_back = (ImageView) findViewById(iv_base_back);//标题左返回
        this.tv_base_title = (TextView) findViewById(tv_base_title);//标题中
        this.tv_base_right = (TextView) findViewById(tv_base_right);//标题右

        this.tv_base_title.setText(showStr(title));// 标题显示
        this.tv_base_right.setText(showStr(right));// 右边显示

        setClickableTrue(this.iv_base_back, this.tv_base_title, this.tv_base_right);//设置标题可点击

        // 设置title左边监听
        this.iv_base_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doTitleLeftListener();
            }
        });

        // 设置title中间监听
        this.tv_base_title.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doTitleMiddleListener();
            }
        });

        // 设置title右边监听
        this.tv_base_right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doTitleRightListener();
            }
        });
    }

    /**
     * title左边监听(默认关闭当前页面)
     */
    public void doTitleLeftListener() {
        finish();
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
     * 判断多个字符串是否为空
     *
     * @return true 至少有一个为空; false 全部不为空
     */
    public boolean areEmpty(CharSequence... strs) {
        return BasisBaseUtils.areEmpty(strs);
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 提示信息
     * @return true 为空; false 不为空
     */
    public boolean isEmpty(CharSequence str, String message) {
        return BasisBaseUtils.isEmpty(mActivity, str, message);
    }

    /**
     * 判断多个字符串是否为空
     *
     * @return true 全不为空; false 至少有一个为空
     */
    public boolean areNotEmpty(CharSequence... strs) {
        return BasisBaseUtils.areNotEmpty(strs);
    }

    /**
     * 判断List不为空&&size>0
     *
     * @return true 不为空&&size>0
     */
    public static boolean areNotEmpty(List list) {
        return BasisBaseUtils.areNotEmpty(list);
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 为空时的提示信息
     * @return true 不为空; false 为空
     */
    public boolean isNotEmpty(CharSequence str, String message) {
        return BasisBaseUtils.isNotEmpty(mActivity, str, message);
    }

    /**
     * 判断对象是否为空
     *
     * @return true 不为空; false 为空
     */
    public boolean areNotNull(Object... objs) {
        return BasisBaseUtils.areNotNull(objs);
    }

    /**
     * 设置控件可以触摸编辑
     */
    public void setEnabledTrue(View... views) {
        BasisBaseUtils.setEnabledTrue(views);
    }

    /**
     * 设置控件可以触摸编辑
     */
    public void setEnabledTrue(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setEnabled(true);
        }
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public void setEnabledFalse(View... views) {
        BasisBaseUtils.setEnabledFalse(views);
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public void setEnabledFalse(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setEnabled(false);
        }
    }

    /**
     * 设置控件可以点击
     */
    public void setClickableTrue(View... views) {
        BasisBaseUtils.setClickableTrue(views);
    }

    /**
     * 设置控件可以点击
     */
    public void setClickableTrue(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setClickable(true);
        }
    }

    /**
     * 设置控件不可以点击
     */
    public void setClickableFalse(View... views) {
        setClickableFalse(views);
    }

    /**
     * 设置控件不可以点击
     */
    public void setClickableFalse(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setClickable(false);
        }
    }

    /**
     * 设置控件可见
     */
    public void setVisible(View... views) {
        BasisBaseUtils.setVisible(views);
    }

    /**
     * 设置控件可见
     */
    public void setVisible(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public void setGone(View... views) {
        BasisBaseUtils.setGone(views);
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public void setGone(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.GONE);
        }
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public void setInVisible(View... views) {
        BasisBaseUtils.setInVisible(views);
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public void setInVisible(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getView(ids[i]).setVisibility(View.INVISIBLE);
        }
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
     * 将字符串转为int(异常时返回-1)
     */
    public int parseInt(String str) {
        return BasisBaseUtils.parseInt(str);
    }

    /**
     * 将字符串转为int(抛出转换异常)
     */
    public int parseIntWithE(String str) throws Exception {
        return BasisBaseUtils.parseIntWithE(str);
    }

    /**
     * 将字符串转为long(异常时返回-1)
     */
    public long parseLong(String str) {
        return BasisBaseUtils.parseLong(str);
    }

    /**
     * 将字符串转为long(抛出转换异常)
     */
    public long parseLongWithE(String str) throws Exception {
        return BasisBaseUtils.parseLongWithE(str);
    }

    /**
     * 将字符串转为Double(异常时返回-1)
     */
    public Double parseDouble(String str) {
        return BasisBaseUtils.parseDouble(str);
    }

    /**
     * 将字符串转为Double(抛出转换异常)
     */
    public Double parseDoubleWithE(String str) throws Exception {
        return BasisBaseUtils.parseDoubleWithE(str);
    }

    /**
     * 判断对象是否相等(对象需重写equals方法)
     */
    public boolean isequals(Object obj1, Object obj2) {
        return BasisBaseUtils.isequals(obj1, obj2);
    }

    /**
     * 获取文本框的字符串(与{@link #getText(TextView)}方法完全一样)
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
     * 执行延迟消息
     */
    public void postDelayed(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
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
        finish();
    }

    /**
     * 获取控件, 并设置可点击
     */
    public <T extends View> T getView(int id) {
        View view = findViewById(id);
        view.setClickable(true);
        view.setOnClickListener(this);
        return (T) view;
    }

    /**
     * 获取控件, 不设置可点击
     */
    public <T extends View> T getViewNoClickable(int id) {
        View view = findViewById(id);
        return (T) view;
    }

    /**
     * TextView查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public TextView findTextView(int id) {
        TextView view = (TextView) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * EditText查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public EditText findEditText(int id) {
        EditText view = (EditText) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * ImageView查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public ImageView findImageView(int id) {
        ImageView view = (ImageView) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * RelativeLayout查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public RelativeLayout findRelativeLayout(int id) {
        RelativeLayout view = (RelativeLayout) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * LinearLayout查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public LinearLayout findLinearLayout(int id) {
        LinearLayout view = (LinearLayout) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * Button查找并设置监听
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public Button findButton(int id) {
        Button view = (Button) findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * Spinner查找
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public Spinner findSpinner(int id) {
        return (Spinner) findViewById(id);
    }

    /**
     * ListView查找
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public ListView findListView(int id) {
        return (ListView) findViewById(id);
    }

    /**
     * GridView查找
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public GridView findGridView(int id) {
        return (GridView) findViewById(id);
    }

    /**
     * RecyclerView查找
     * <p>(使用{@link #getView(int)}或者{@link #getViewNoClickable(int)} (int)}方法替代)
     */
    @Deprecated
    public RecyclerView findRecyclerView(int id) {
        return (RecyclerView) findViewById(id);
    }

    /**
     * 设置text
     */
    public TextView setTvText(int id, String text) {
        TextView view = getView(id);
        view.setText(showStr(text));
        return view;
    }

    /**
     * 设置text
     */
    public EditText setEtText(int id, String text) {
        EditText view = getView(id);
        view.setText(showStr(text));
        return view;
    }

    /**
     * 延迟获取焦点
     */
    public void requestFocusDelayed(final TextView view, long delayMillis) {
        postDelayed(new Runnable() {

            @Override
            public void run() {
                view.requestFocus();
            }
        }, delayMillis);
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
        View view = getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取string
     *
     * @param id
     */
    public String getStringById(int id) {
        return this.getResources().getString(id);
    }

    /**
     * 获取demin
     *
     * @param id
     */
    protected int getDimensionById(int id) {
        return (int) this.getResources().getDimension(id);
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(String action) {
        Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    /**
     * 注册广播(只能注册一次)
     *
     * @deprecated 使用 {@link #addReceiver(OnReceiverListener, String...)} 可重复注册添加多条广播
     */
    @Deprecated
    public void registerReceiver(String... action) {
//        if (action != null) {//不做非空判断, 异常时需暴露异常, 便于查错
        mFilter = new IntentFilter();
        for (int i = 0; i < action.length; i++) {
            mFilter.addAction(action[i]);
        }
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                handlerReceiver(context, action, intent);
            }
        };
        registerReceiver(mReceiver, mFilter);
//        }
    }

    /**
     * 根据接受到的action处理不同消息, 与注册广播方法关联使用
     * <p>{@link #registerReceiver(String...)}
     */
    @Deprecated
    public void handlerReceiver(Context context, String action, Intent intent) {
    }

    /**
     * 注册添加广播, 可添加多条广播, 用于替代 {@link #registerReceiver(String...)} 方法
     */
    public BroadcastReceiver addReceiver(final OnReceiverListener receiverListener, String... action) {
        IntentFilter filter = new IntentFilter();
        for (int i = 0; i < action.length; i++) {
            filter.addAction(action[i]);
        }
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (receiverListener != null) {
                    receiverListener.onReceiver(context, action, intent);
                }
            }
        };
        registerReceiver(receiver, filter);
        mReceiverList.add(receiver);
        return receiver;
    }

    /**
     * 取消注册广播
     */
    public void removeReceiver(BroadcastReceiver receiver) {
        if (receiver != null) {
            unregisterReceiver(receiver);
            mReceiverList.remove(receiver);
        }
    }

    /**
     * 取消所有已经添加注册的广播
     */
    public void removeAllReceiver() {
        for (int i = 0; i < mReceiverList.size(); i++) {
            BroadcastReceiver receiver = mReceiverList.get(i);
            unregisterReceiver(receiver);
        }
        mReceiverList.clear();
    }

    /**
     * 广播接受自定义监听处理
     */
    interface OnReceiverListener {
        void onReceiver(Context context, String action, Intent intent);
    }

}
