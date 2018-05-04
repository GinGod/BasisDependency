package com.gingold.basislibrary.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.gingold.basislibrary.R;

/**
 * 动画工具类
 */
public class BasisAnimUtils {

    /**
     * 透明度渐变的动画
     */
    public static void alpha(View view) {
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.2f);
        // 动画播放的时间长度
        aa.setDuration(2000);
        // 设置重复播放的次数
        aa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        aa.setRepeatMode(Animation.REVERSE);
        //动画结束后还原到初始状态
        aa.setFillAfter(true);
        // 让iv播放aa动画
        view.startAnimation(aa);
    }

    /**
     * 平移动画
     */
    public static void trans(View view) {
        //相对于父窗口是相对于view的父控件, 测试发现动画只在父窗口范围内显示, 超出父窗口的位置不显示
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0.5f);
        // 动画播放的时间长度
        ta.setDuration(2000);
        // 设置重复播放的次数
        ta.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ta.setRepeatMode(Animation.REVERSE);
        //动画结束后还原到初始状态
        ta.setFillAfter(false);
        // 让iv播放aa动画
        view.startAnimation(ta);
    }

    /**
     * 缩放动画
     */
    public static void scale(View view) {
        ScaleAnimation sa = new ScaleAnimation(0.2f, 2.0f, 0.2f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        // 动画播放的时间长度
        sa.setDuration(2000);
        // 设置重复播放的次数
        sa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        sa.setRepeatMode(Animation.REVERSE);
        //动画结束后还原到初始状态
        sa.setFillAfter(false);
        // 让iv播放aa动画
        view.startAnimation(sa);
    }

    /**
     * 旋转动画
     */
    public static void rotate(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        ra.setDuration(2000);
        // 设置重复播放的次数
        ra.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ra.setRepeatMode(Animation.REVERSE);
        //动画结束后还原到初始状态
        ra.setFillAfter(false);
        // 让iv播放aa动画
        view.startAnimation(ra);
    }

    /**
     * 连续旋转
     */
    public static void rotateContinue(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        ra.setDuration(252);
        // 设置重复播放的次数
        ra.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ra.setRepeatMode(Animation.RESTART);
        //动画结束后还原到初始位置
        ra.setFillAfter(false);
        ra.setInterpolator(new LinearInterpolator());//速度平均
        // 让iv播放aa动画
        view.startAnimation(ra);
    }

    /**
     * 动画合集 集合
     */
    public static void set(View view) {
        AnimationSet set = new AnimationSet(false);

        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        // 动画播放的时间长度
        aa.setDuration(2000);
        // 设置重复播放的次数
        aa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        aa.setRepeatMode(Animation.REVERSE);

        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        ra.setDuration(2000);
        // 设置重复播放的次数
        ra.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ra.setRepeatMode(Animation.REVERSE);

        ScaleAnimation sa = new ScaleAnimation(0.2f, 2.0f, 0.2f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        sa.setDuration(2000);
        // 设置重复播放的次数
        sa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        sa.setRepeatMode(Animation.REVERSE);

        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.5f);
        // 动画播放的时间长度
        ta.setDuration(2000);
        // 设置重复播放的次数
        ta.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ta.setRepeatMode(Animation.REVERSE);

        set.addAnimation(aa);
        set.addAnimation(ta);
        set.addAnimation(sa);
        set.addAnimation(ra);
        view.startAnimation(set);
    }

    /**
     * 透明度渐变的动画
     */
    public static void alpha(Context context, View view) {
        anim(context, view, R.anim.anim_view_alpha);
    }

    /**
     * 平移动画
     */
    public static void trans(Context context, View view) {
        anim(context, view, R.anim.anim_view_trans);
    }

    /**
     * 缩放动画
     */
    public static void scale(Context context, View view) {
        anim(context, view, R.anim.anim_view_scale);
    }

    /**
     * 旋转动画
     */
    public static void rotate(Context context, View view) {
        anim(context, view, R.anim.anim_view_rotate);
    }

    /**
     * 连续旋转动画
     */
    public static void rotateContinue(Context context, View view) {
        anim(context, view, R.anim.anim_view_rotate_continue);
    }

    /**
     * 动画合集 集合
     */
    public static void set(Context context, View view) {
        anim(context, view, R.anim.anim_view_set);
    }

    /**
     * 资源动画
     */
    public static void anim(Context context, View view, int animId) {
        Animation aa = AnimationUtils.loadAnimation(context, animId);
        view.startAnimation(aa);
    }

    /**
     * 透明度渐变的动画
     *
     * @param view
     */
    public static void alphaOfObject(View view) {
        animOfObject(view, "alpha", new float[]{
                0.2f, 0.4f, 0.6f, 0.8f, 1.0f});
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public static void transOfObject(View view) {
        animOfObject(view, "translationX",
                new float[]{10f, 20f, 30f, 40f, 60f, 80f});
    }

    /**
     * 缩放动画
     */
    public static void scaleOfObject(View view) {
        // iv.setScaleX(scaleX)
        animOfObject(view, "scaleX", new float[]{
                1f, 2f, 3f, 4f, 5f, 6f});
    }

    /**
     * 旋转动画
     */
    public static void rotateOfObject(View view) {
        animOfObject(view, "rotationY",
                new float[]{90f, 180f, 270f, 360f});
    }

    /**
     * 属性动画(执行时不会清除原有动画)
     */
    public static void animOfObject(View target, String propertyName, float... values) {
        ObjectAnimator oa = ObjectAnimator.ofFloat(target, propertyName, values);
        oa.setDuration(2000);
        oa.setRepeatCount(ObjectAnimator.INFINITE);
        oa.setRepeatMode(ObjectAnimator.REVERSE);
        oa.start();
    }

    /**
     * 属性动画合集 集合
     */
    public static void setOfObject(View view) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "translationX",
                new float[]{10f, 20f, 30f, 40f, 60f, 80f});
        oa.setDuration(3000);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(view, "translationY",
                new float[]{-10f, -20f, -30f, -40f, -60f, -80f});
        oa2.setDuration(3000);
        set.playTogether(oa, oa2);
        set.start();
    }

    /**
     * 清除动画
     */
    public static void clearAnimation(View... views) {
        for (int i = 0; i < views.length; i++) {
            views[0].clearAnimation();
        }
    }

}
