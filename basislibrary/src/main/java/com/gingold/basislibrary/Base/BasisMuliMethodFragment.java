package com.gingold.basislibrary.Base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含一些通用方法的BasisBaseFragment
 *
 * @author
 */
public abstract class BasisMuliMethodFragment extends BasisBaseFragment {
    private BroadcastReceiver mReceiver;//广播
    private IntentFilter mFilter;//广播接受过滤器
    private ArrayList<BroadcastReceiver> mReceiverList = new ArrayList<>();//广播集合

    @Override
    public void onDestroyView() {
        destory();
        super.onDestroyView();
    }

    /**
     * fragment destory前的操作
     */
    public void destory() {
        try {
            if (mReceiver != null) {
                mActivity.unregisterReceiver(mReceiver);//取消注册广播
            }

            removeAllReceiver();//取消所有已经添加注册的广播

            mBasisHandler.removeCallbacksAndMessages(null);//清空所有消息
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                mActivity.unregisterReceiver(mReceiver);//取消注册上个广播
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
            mActivity.registerReceiver(mReceiver, mFilter);
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
            mActivity.registerReceiver(receiver, filter);
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
                mActivity.unregisterReceiver(receiver);
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
                mActivity.unregisterReceiver(receiver);
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
            getViewNoClickable(ids[i]).setEnabled(true);
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
            getViewNoClickable(ids[i]).setEnabled(false);
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
            getViewNoClickable(ids[i]).setVisibility(View.VISIBLE);
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
            getViewNoClickable(ids[i]).setVisibility(View.GONE);
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
            getViewNoClickable(ids[i]).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置控件背景颜色
     */
    public void setBGColor(int color, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setBackgroundColor(getColorById(color));
        }
    }

    /**
     * 设置控件背景颜色
     */
    public void setBGColor(int color, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getViewNoClickable(ids[i]).setBackgroundColor(getColorById(color));
        }
    }

    /**
     * 设置控件背景资源
     */
    public void setBGResource(int resource, View... views) {
        BasisBaseUtils.setBGResource(resource, views);
    }

    /**
     * 设置控件背景资源
     */
    public void setBGResource(int resource, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            getViewNoClickable(ids[i]).setBackgroundResource(resource);
        }
    }

    /**
     * 设置文本框字体颜色
     */
    public void setTVTextColor(int color, TextView... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setTextColor(getColorById(color));
        }
    }

    /**
     * 设置文本框字体颜色
     */
    public void setTVTextColor(int color, int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getViewNoClickable(ids[i]))).setTextColor(getColorById(color));
        }
    }

    /**
     * 设置下划线
     */
    public void setUnderline(TextView... views) {
        BasisBaseUtils.setUnderline(views);
    }

    /**
     * 设置下划线
     */
    public void setUnderline(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getViewNoClickable(ids[i]))).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        }
    }

    /**
     * 设置删除线
     */
    public void setStrike(TextView... views) {
        BasisBaseUtils.setStrike(views);
    }

    /**
     * 设置删除线
     */
    public void setStrike(int... ids) {
        for (int i = 0; i < ids.length; i++) {
            ((TextView) (getViewNoClickable(ids[i]))).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
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
    public int getDimensionById(int id) {
        return (int) this.getResources().getDimension(id);
    }

    /**
     * 获取Color
     *
     * @param id
     */
    public int getColorById(int id) {
        return (int) this.getResources().getColor(id);
    }

    /**
     * 发送广播
     */
    public void sendBroadCast(String action) {
        Intent intent = new Intent(action);
        mActivity.sendBroadcast(intent);
    }

}
