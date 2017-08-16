package com.gingold.basislibrary.view.RvRefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gingold.basislibrary.R;
import com.gingold.basislibrary.view.CircleProgress.CircleProgressView;


/**
 * recycleview的刷新的脚布局
 */
public class BasisRvRefreshFV extends LinearLayout {

    private CircleProgressView pb_y_recycleview_foot_loadmore_progressbar;
    private TextView tv_y_recycleview_foot_loadmore_status;
    /**
     * 加载中
     */
    public final static int STATE_LOADING = 0;
    /**
     * 加载完成
     */
    public final static int STATE_COMPLETE = 1;
    /**
     * 正常状态
     */
    public final static int STATE_NOMORE = 2;
    public View mContentView;
    public int mMeasuredHeight;

    public BasisRvRefreshFV(Context context) {
        super(context);
        initView(context);
    }

    public BasisRvRefreshFV(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * 初始化
     */
    private void initView(Context context) {
        //设置内部内容居中
        setGravity(Gravity.CENTER);
        //设置宽高
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //底部布局
        mContentView = View.inflate(context, R.layout.foot_recycleview_loadmore, null);
        pb_y_recycleview_foot_loadmore_progressbar = (CircleProgressView) mContentView.findViewById(R.id.pb_y_recycleview_foot_loadmore_progressbar);
        tv_y_recycleview_foot_loadmore_status = (TextView) mContentView.findViewById(R.id.tv_y_recycleview_foot_loadmore_status);

        addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT, 0));

        //测量
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获取高度
        mMeasuredHeight = getMeasuredHeight();
    }

    /**
     * 设置当前状态
     */
    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                pb_y_recycleview_foot_loadmore_progressbar.setVisibility(View.VISIBLE);
                tv_y_recycleview_foot_loadmore_status.setText("加载中...");
                setVisibleHeight(mMeasuredHeight);
                break;
            case STATE_COMPLETE:
                tv_y_recycleview_foot_loadmore_status.setText("加载完成");
                pb_y_recycleview_foot_loadmore_progressbar.setVisibility(View.GONE);
                setVisibleHeight(0);
                break;
            case STATE_NOMORE:
                tv_y_recycleview_foot_loadmore_status.setText("没有更多了...");
                smoothScrollTo(mMeasuredHeight);
                pb_y_recycleview_foot_loadmore_progressbar.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 这是一个让布局高度改变的方法
     */
    public void smoothScrollTo(int Height) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), Height);
        animator.setDuration(252).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    /**
     * 获取显示的高度
     */
    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        return lp.height;
    }

    /**
     * 设置显示的高度
     */
    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
        lp.height = height;
        mContentView.setLayoutParams(lp);
    }

}
