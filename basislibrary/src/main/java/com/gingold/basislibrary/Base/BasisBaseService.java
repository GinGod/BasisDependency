package com.gingold.basislibrary.Base;

import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.gingold.basislibrary.utils.BasisLogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * BasisBaseService
 *
 * @author
 */
public abstract class BasisBaseService extends Service {
    public Application app;//application
    public BasisBaseService mService;// 上下文
    public Context context;// 上下文
    public Handler mBasisHandler;//Handler

    private BroadcastReceiver mReceiver;//广播
    private IntentFilter mFilter;//广播接受过滤器
    private ArrayList<BroadcastReceiver> mReceiverList = new ArrayList<>();//广播集合

    private final String TAG = this.getClass().getSimpleName() + "TAG: ";//类名日志tag

    @Override
    public void onCreate() {
        try {
            super.onCreate();
            init();// 初始化数据
            initData();// 逻辑
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化数据
     */
    public void init() {
        try {
            app = getApplication();
            mService = BasisBaseService.this;
            context = BasisBaseService.this;
            mBasisHandler = new Handler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 逻辑
     */
    public void initData() {
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
        try {
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);//取消注册广播
            }

            removeAllReceiver();//取消所有已经添加注册的广播

            mBasisHandler.removeCallbacksAndMessages(null);//清空所有消息
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return BasisBaseUtils.isEmpty(mService, str, message);
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
     *
     * <p>(使用{@link #areNotEmptyList(List)}方法替代)
     */
    @Deprecated
    public static boolean areNotEmpty(List list) {
        return BasisBaseUtils.areNotEmptyList(list);
    }

    /**
     * 判断List不为空&&size>0
     *
     * @return true 不为空&&size>0
     */
    public static boolean areNotEmptyList(List list) {
        return BasisBaseUtils.areNotEmptyList(list);
    }

    /**
     * 判断字符串是否为空,为空时进行提示
     *
     * @param str     字符串
     * @param message 为空时的提示信息
     * @return true 不为空; false 为空
     */
    public boolean isNotEmpty(CharSequence str, String message) {
        return BasisBaseUtils.isNotEmpty(mService, str, message);
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
     * 执行延迟消息
     */
    public void postDelayed(Runnable r, long delayMillis) {
        mBasisHandler.postDelayed(r, delayMillis);
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
     * 注册广播(只能注册一次)
     *
     * @deprecated 使用 {@link #addReceiver(OnReceiverListener, String...)} 可重复注册添加多条广播
     */
    @Deprecated
    public void registerReceiver(String... action) {
        try {
            mFilter = new IntentFilter();
            for (int i = 0; i < action.length; i++) {
                mFilter.addAction(action[i]);
            }
            if (mReceiver != null) {
                unregisterReceiver(mReceiver);
            }
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    try {
                        handlerReceiver(context, action, intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            registerReceiver(mReceiver, mFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据接受到的action处理不同消息, 与注册广播方法关联使用
     * <p>{@link #registerReceiver(String...)}
     */
    public void handlerReceiver(Context context, String action, Intent intent) {
    }

    /**
     * 注册添加广播, 可添加多条广播, 用于替代 {@link #registerReceiver(String...)} 方法
     */
    public BroadcastReceiver addReceiver(final OnReceiverListener receiverListener, String... action) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取消注册广播
     */
    public void removeReceiver(BroadcastReceiver receiver) {
        try {
            if (receiver != null) {
                unregisterReceiver(receiver);
                mReceiverList.remove(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消所有已经添加注册的广播
     */
    public void removeAllReceiver() {
        try {
            for (int i = 0; i < mReceiverList.size(); i++) {
                BroadcastReceiver receiver = mReceiverList.get(i);
                unregisterReceiver(receiver);
            }
            mReceiverList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播接受自定义监听处理
     */
    public interface OnReceiverListener {
        void onReceiver(Context context, String action, Intent intent);
    }

    /**
     * 日志打印
     */
    public void loge(String message) {
        BasisLogUtils.e(TAG, message);
    }

}
