package com.gingold.basislibrary.view.CircleProgress;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.List;

/**
 *
 */
public abstract class BaseProgressController {

    private View mTarget;

    private List<Animator> mAnimators;

    public void setTarget(View target) {
        this.mTarget = target;
    }

    public View getTarget() {
        return mTarget;
    }

    public int getWidth() {
        return mTarget.getWidth();
    }

    public int getHeight() {
        return mTarget.getHeight();
    }

    /**
     * 重新绘制
     */
    public void postInvalidate() {
        mTarget.postInvalidate();
    }

    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * 初始化动画
     */
    public void initAnimation() {
        mAnimators = createAnimation();
    }

    public abstract List<Animator> createAnimation();

    /**
     * 设置动画状态
     */
    public void setAnimationStatus(AnimStatus animStatus) {
        if (mAnimators == null) {
            return;
        }
        int count = mAnimators.size();
        for (int i = 0; i < count; i++) {
            Animator animator = mAnimators.get(i);
            boolean isRunning = animator.isRunning();
            switch (animStatus) {
                case START:
                    if (!isRunning) {
                        animator.start();
                    }
                    break;
                case END:
                    if (isRunning) {
                        animator.end();
                    }
                    break;
                case CANCEL:
                    if (isRunning) {
                        animator.cancel();
                    }
                    break;
            }
        }
    }

    /**
     * 动画状态
     */
    public enum AnimStatus {
        START, END, CANCEL
    }

}
