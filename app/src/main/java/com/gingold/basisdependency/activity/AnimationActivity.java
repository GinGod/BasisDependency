package com.gingold.basisdependency.activity;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;

/**
 * 动画测试
 */

public class AnimationActivity extends BaseActivity {
    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_animation);
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 透明度渐变的动画
     *
     * @param view
     */
    public void alpha(View view) {
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        // 动画播放的时间长度
        aa.setDuration(2000);
        // 设置重复播放的次数
        aa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        aa.setRepeatMode(Animation.REVERSE);
        // 让iv播放aa动画
        view.startAnimation(aa);
    }

    /**
     * 平移动画
     *
     * @param view
     */
    public void trans(View view) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        // 动画播放的时间长度
        ta.setDuration(2000);
        // 设置重复播放的次数
        ta.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ta.setRepeatMode(Animation.REVERSE);
        // 让iv播放aa动画
        view.startAnimation(ta);
    }

    /**
     * 缩放动画
     */
    public void scale(View view) {
        ScaleAnimation sa = new ScaleAnimation(0.2f, 2.0f, 0.2f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        sa.setDuration(2000);
        // 设置重复播放的次数
        sa.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        sa.setRepeatMode(Animation.REVERSE);
        // 让iv播放aa动画
        view.startAnimation(sa);
    }

    /**
     * 旋转动画
     */
    public void rotate(View view) {
        RotateAnimation ra = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        // 动画播放的时间长度
        ra.setDuration(2000);
        // 设置重复播放的次数
        ra.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ra.setRepeatMode(Animation.REVERSE);
        // 让iv播放aa动画
        view.startAnimation(ra);
    }

    /**
     * 动画合集 集合
     */
    public void set(View view) {
        AnimationSet set = new AnimationSet(false);
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
        // 让iv播放aa动画
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1f);
        // 动画播放的时间长度
        ta.setDuration(2000);
        // 设置重复播放的次数
        ta.setRepeatCount(Animation.INFINITE);
        // 设置重复播放的模式
        ta.setRepeatMode(Animation.REVERSE);
        set.addAnimation(ta);
        set.addAnimation(sa);
        set.addAnimation(ra);
        view.startAnimation(set);
    }

}
