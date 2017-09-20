package com.gingold.basislibrary.Base;

import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

/**
 * BasisBaseService
 */
public abstract class BasisBaseService extends Service {
    public Application app;//application
    public BasisBaseService mService;// 上下文
    public Context context;// 上下文
    public Handler mHandler;//Handler
    public Gson gson;//Gson

    private BroadcastReceiver mReceiver;//广播
    private IntentFilter mFilter;//广播接受过滤器

    private final String TAG = this.getClass().getSimpleName() + "TAG";//类名日志tag

    @Override
    public void onCreate() {
        super.onCreate();
        initData();// 初始化数据
        logicDispose();// 逻辑
        additionalLogic();//附加逻辑
    }

    /**
     * 初始化数据
     */
    private void initData() {
        app = getApplication();
        mService = BasisBaseService.this;
        context = BasisBaseService.this;
        mHandler = new Handler();
        gson = new Gson();
    }

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
    public void onDestroy() {
        destory();
        super.onDestroy();
    }

    /**
     * service destory前的操作
     */
    public void destory() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);//取消注册广播
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
     * Toast
     */
    public void toast(String message) {
        if (message != null) {
            Toast.makeText(mService, message, Toast.LENGTH_SHORT).show();
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
     * 执行延迟消息
     */
    public void postDelayed(Runnable r, long delayMillis) {
        mHandler.postDelayed(r, delayMillis);
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
        registerReceiver(mReceiver, mFilter);
    }

    /**
     * 根据接受到的action处理不同消息
     *
     * @param action
     */
    public void handlerReceiver(Context context, String action, Intent intent) {
    }

}
