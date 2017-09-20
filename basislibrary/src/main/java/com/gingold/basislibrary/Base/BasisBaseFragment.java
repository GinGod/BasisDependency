package com.gingold.basislibrary.Base;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

/**
 * BasisBaseFragment
 */
public abstract class BasisBaseFragment extends Fragment implements OnClickListener {
    public View mBaseView;//Fragmnet根部局

    public Application app;//application
    public Activity mActivity;// 上下文
    public Context context;// 上下文
    public Handler mHandler;//Handler
    public Gson gson;//Gson

    private BroadcastReceiver mReceiver;//广播
    private IntentFilter mFilter;//广播接受过滤器

    private final String TAG = this.getClass().getSimpleName() + "TAG";//类名日志tag

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();// 初始化数据
        mBaseView = setupViewLayout(inflater);// 初始化布局
        initView(mBaseView);// 初始化UI
        additionalInitData();// 附加初始化数据
        listener();// 事件监听
        logicDispose();// 逻辑
        additionalLogic();//附加逻辑
        return mBaseView;
    }

    /**
     * 初始化数据
     */
    public void initData() {
        mActivity = getActivity();
        app = mActivity.getApplication();
        context = mActivity;
        mHandler = new Handler();
        gson = new Gson();
    }

    /**
     * 初始化根部局
     *
     * @return mBaseView(根部局View)
     */
    public abstract View setupViewLayout(LayoutInflater inflater);

    /**
     * 附加初始化数据
     */
    public void additionalInitData() {

    }

    /**
     * 初始化UI
     */
    public abstract void initView(View view);

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
    public void onDestroyView() {
        destory();
        super.onDestroyView();
    }

    /**
     * fragment destory前的操作
     */
    public void destory() {
        if (mReceiver != null) {
            mActivity.unregisterReceiver(mReceiver);//取消注册广播
        }

        mHandler.removeCallbacksAndMessages(null);//清空所有消息
    }

    /**
     * 判断多个字符串是否为空
     *
     * @return true 至少有一个为空; false 全部不为空
     */
    public boolean areEmpty(CharSequence... strs) {
        if (strs == null) {
            return true;
        }

        for (int i = 0; i < strs.length; i++) {
            if (TextUtils.isEmpty(strs[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 提示信息
     * @return true 为空; false 不为空
     */
    public boolean isEmpty(CharSequence str, String message) {
        if (TextUtils.isEmpty(str)) {
            toast(message);
            return true;
        }
        return false;
    }

    /**
     * 判断多个字符串是否为空
     *
     * @return true 全不为空; false 至少有一个为空
     */
    public boolean areNotEmpty(CharSequence... strs) {
        if (strs == null) {
            return false;
        }

        for (int i = 0; i < strs.length; i++) {
            if (TextUtils.isEmpty(strs[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断List不为空&&size>0
     *
     * @return true 不为空&&size>0
     */
    public static boolean areNotEmpty(List list) {
//        if (list != null && list.size() > 0) {
//            return true;
//        }
//
//        return false;
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
        if (TextUtils.isEmpty(str)) {
            toast(message);
            return false;
        }
        return true;
    }

    /**
     * 判断对象是否为空
     *
     * @return true 不为空; false 为空
     */
    public boolean areNotNull(Object... objs) {
        if (objs == null) {
            return false;
        }

        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置控件可以触摸编辑
     */
    public void setEnabledTrue(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setEnabled(true);
        }
    }

    /**
     * 设置控件不可以触摸编辑
     */
    public void setEnabledFalse(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setEnabled(false);
        }
    }

    /**
     * 设置控件可以点击
     */
    public void setClickableTrue(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setClickable(true);
        }
    }

    /**
     * 设置控件不可以点击
     */
    public void setClickableFalse(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setClickable(false);
        }
    }

    /**
     * 设置控件可见
     */
    public void setVisible(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置控件不可见(隐藏)
     */
    public void setGone(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }

    /**
     * 设置控件不可见(不隐藏)
     */
    public void setInVisible(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Toast
     */
    public void toast(String message) {
        if (message != null) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 将字符串转为int
     */
    public int parseInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 将字符串转为int(抛出转换异常)
     */
    public int parseIntWithE(String str) throws Exception {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将字符串转为long
     */
    public long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /**
     * 将字符串转为long(抛出转换异常)
     */
    public long parseLongWithE(String str) throws Exception {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 将字符串转为Double
     */
    public Double parseDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            e.printStackTrace();
            return -1d;
        }
    }

    /**
     * 将字符串转为Double(抛出转换异常)
     */
    public Double parseDoubleWithE(String str) throws Exception {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 判断对象是否相等(对象需重写equals方法)
     */
    public boolean isequals(Object obj1, Object obj2) {
        if (obj1 == null) {
            return obj1 == obj2;
        } else {
            if (obj1.equals(obj2)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 获取输入框的字符串
     */
    public String getText(TextView view) {
        return view.getText().toString().trim();
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
        mActivity.startActivity(new Intent(mActivity, cls));
    }

    /**
     * 获取控件, 并设置可点击
     */
    public <T extends View> T getView(int id) {
        View view = mBaseView.findViewById(id);
        view.setClickable(true);
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
     * TextView查找并设置监听
     */
    public TextView findTextView(int id) {
        TextView view = (TextView) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * EditText查找并设置监听
     */
    public EditText findEditText(int id) {
        EditText view = (EditText) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * ImageView查找并设置监听
     */
    public ImageView findImageView(int id) {
        ImageView view = (ImageView) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * RelativeLayout查找并设置监听
     */
    public RelativeLayout findRelativeLayout(int id) {
        RelativeLayout view = (RelativeLayout) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * LinearLayout查找并设置监听
     */
    public LinearLayout findLinearLayout(int id) {
        LinearLayout view = (LinearLayout) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * Button查找并设置监听
     */
    public Button findButton(int id) {
        Button view = (Button) mBaseView.findViewById(id);
        setClickableTrue(view);
        view.setOnClickListener(this);
        return view;
    }

    /**
     * Spinner查找
     */
    public Spinner findSpinner(int id) {
        return (Spinner) mBaseView.findViewById(id);
    }

    /**
     * ListView查找
     */
    public ListView findListView(int id) {
        return (ListView) mBaseView.findViewById(id);
    }

    /**
     * GridView查找
     */
    public GridView findGridView(int id) {
        return (GridView) mBaseView.findViewById(id);
    }

    /**
     * 设置text
     */
    public TextView setTvText(int id, String text) {
        TextView view = findTextView(id);
        view.setText(showStr(text));
        return view;
    }

    /**
     * 设置text
     */
    public EditText setEtText(int id, String text) {
        EditText view = findEditText(id);
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
        View view = mActivity.getWindow().peekDecorView();// 用于判断虚拟软键盘是否是显示的
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager inputmanger = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取string
     *
     * @param id
     */
    public String getStringById(int id) {
        return mActivity.getResources().getString(id);
    }

    /**
     * 获取demin
     *
     * @param id
     */
    protected int getDimensionById(int id) {
        return (int) mActivity.getResources().getDimension(id);
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(String action) {
        Intent intent = new Intent(action);
        mActivity.sendBroadcast(intent);
    }

    /**
     * 注册广播
     */
    public void registerReceiver(String... action) {
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
        mActivity.registerReceiver(mReceiver, mFilter);
    }

    /**
     * 根据接受到的action处理不同消息
     *
     * @param action
     */
    public void handlerReceiver(Context context, String action, Intent intent) {
    }

}
