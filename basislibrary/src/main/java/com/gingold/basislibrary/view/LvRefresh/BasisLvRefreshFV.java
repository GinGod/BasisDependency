package com.gingold.basislibrary.view.LvRefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gingold.basislibrary.R;

/**
 * 上拉刷新listView脚布局
 */
public class BasisLvRefreshFV extends LinearLayout {
    public final static int STATE_NORMAL = 0;//默认状态(上拉加载和加载完成)
    public final static int STATE_READY = 1;//松开加载
    public final static int STATE_LOADING = 2;//正在加载

    private Context mContext;

    private LinearLayout mFooterView;
    private View mCircleProgressView;
    private TextView mFooterStatus;
    public int mMeasuredHeight;

    public BasisLvRefreshFV(Context context) {
        super(context);
        initView(context);
    }

    public BasisLvRefreshFV(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mFooterView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.lv_refresh_footer, null);
        mFooterView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        addView(mFooterView);// 初始情况，设置上拉加载view高度为正常高度
        setGravity(Gravity.BOTTOM);

        mCircleProgressView = mFooterView.findViewById(R.id.cpv_lv_footer);
        mFooterStatus = (TextView) mFooterView.findViewById(R.id.tv_lv_footer_status);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
    }

    public void setState(int state) {
        mCircleProgressView.setVisibility(View.GONE);
        if (state == STATE_READY) {
            mFooterStatus.setText("松开加载更多");
        } else if (state == STATE_LOADING) {
            mCircleProgressView.setVisibility(View.VISIBLE);
            mFooterStatus.setText("加载中...");
        } else {
            mFooterStatus.setText("加载更多");
        }
    }


    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mFooterView.getLayoutParams();
        lp.height = height;
        mFooterView.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mFooterView.getLayoutParams().height;
    }


    /**
     * normal status
     */
    public void normal() {
        mFooterStatus.setVisibility(View.VISIBLE);
        mCircleProgressView.setVisibility(View.GONE);
    }


    /**
     * loading status
     */
    public void loading() {
        mFooterStatus.setVisibility(View.VISIBLE);
        mCircleProgressView.setVisibility(View.VISIBLE);
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mFooterView.getLayoutParams();
//        lp.height = 0;
//        mFooterView.setLayoutParams(lp);
        setBottomMargin(-mMeasuredHeight);
    }

    /**
     * showDialogSimple footer
     */
    public void show() {
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mFooterView.getLayoutParams();
//        lp.height = LayoutParams.WRAP_CONTENT;
//        mFooterView.setLayoutParams(lp);
        setBottomMargin(0);
    }

    public void setBottomMargin(int bottomMargin) {
        LinearLayout.LayoutParams lp = (LayoutParams) mFooterView.getLayoutParams();
        lp.bottomMargin = bottomMargin;
        mFooterView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LinearLayout.LayoutParams lp = (LayoutParams) mFooterView.getLayoutParams();
        return lp.bottomMargin;
    }

}
