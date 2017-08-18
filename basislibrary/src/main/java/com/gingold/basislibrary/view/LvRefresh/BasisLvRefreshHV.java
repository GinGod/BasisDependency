package com.gingold.basislibrary.view.LvRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gingold.basislibrary.R;
import com.gingold.basislibrary.view.CircleProgress.CircleProgressView;

/**
 * 下拉刷新listView头布局
 */
public class BasisLvRefreshHV extends LinearLayout {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private CircleProgressView mCircleProgressView;
    private TextView mStatusTextView;

    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 252;

    public final static int STATE_NORMAL = 0;//默认状态(下拉刷新和刷新完成)
    public final static int STATE_READY = 1;//松开刷新
    public final static int STATE_REFRESHING = 2;//正在刷新

    public BasisLvRefreshHV(Context context) {
        super(context);
        initView(context);
    }

    public BasisLvRefreshHV(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.lv_refresh_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.CENTER);//下拉时,布局默认显示center

        mArrowImageView = (ImageView) findViewById(R.id.iv_lv_header_arrow);//箭头
        mStatusTextView = (TextView) findViewById(R.id.tv_lv_header_status);//刷新状态
        mCircleProgressView = (CircleProgressView) findViewById(R.id.cpv_lv_header);//进度条

        //箭头旋转向上动画(默认向下)
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        //箭头旋转向下状态
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 正在刷新
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.GONE);
            mCircleProgressView.setVisibility(View.VISIBLE);
        } else {    // 其它
            mArrowImageView.setVisibility(View.VISIBLE);
            mCircleProgressView.setVisibility(View.GONE);
        }

        switch (state) {
            case STATE_NORMAL://默认状态(下拉刷新和刷新完成)
                if (mState == STATE_READY) {//当前是准备刷新状态, 将箭头旋转朝下
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mStatusTextView.setText("下拉刷新");
                break;
            case STATE_READY://松开刷新
                if (mState != STATE_READY) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText("松开刷新数据");
                }
                break;
            case STATE_REFRESHING://正在刷新
                mStatusTextView.setText("正在加载...");
                break;
            default:
        }

        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
                .getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getLayoutParams().height;
    }

}
