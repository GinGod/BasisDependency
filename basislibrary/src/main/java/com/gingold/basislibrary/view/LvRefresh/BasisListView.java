package com.gingold.basislibrary.view.LvRefresh;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;
import android.widget.TextView;

import com.gingold.basislibrary.Base.BasisBaseUtils;
import com.gingold.basislibrary.R;

/**
 * 下拉刷新上拉加载listView
 */
public class BasisListView extends ListView implements OnScrollListener {

    private float mLastY = -1; // save event y
    private Scroller mScroller; // used for scroll back
    private OnScrollListener mScrollListener; // user's scroll listener

    // the interface to trigger refresh and load more.
    private BasisLvRefreshListener mListViewListener;

    // -- header view
    private BasisLvRefreshHV mHeaderView;
    // header view content, use it to calculate the Header's height. And hide it
    // when disable pull refresh.
    private LinearLayout mHeaderViewContent;
    private TextView mHeaderTimeView;
    private int mHeaderViewHeight; // header view's height
    private boolean mEnablePullRefresh = true;
    private boolean mPullRefreshing = false; // is refreashing.

    // -- footer view
    private BasisLvRefreshFV mFooterView;
    private TextView mFooterStatusView;
    private int mFooterViewHeight;
    private boolean mEnablePullLoad;
    private boolean mPullLoading;//is loading
    private boolean mIsFooterReady = false;

    // total list items, used to detect is at the bottom of listview.
    private int mTotalItemCount;

    // for mScroller, scroll back from header or footer.
    private int mScrollBack;
    private final static int SCROLLBACK_HEADER = 0;
    private final static int SCROLLBACK_FOOTER = 1;

    private final static int SCROLL_DURATION = 525; // scroll back duration
    private final static int PULL_LOAD_MORE_DELTA = 50; // when pull up >= 50px
    // at bottom, trigger
    // load more.
    private final static float OFFSET_RADIO = 1.8f; // support iOS like pull
    public boolean isShowLoadMore = true;//默认显示加载更多布局
    // feature.

    public BasisListView(Context context) {
        super(context);
        initWithContext(context);
    }

    public BasisListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWithContext(context);
    }

    public BasisListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initWithContext(context);
    }

    private void initWithContext(Context context) {
        mScroller = new Scroller(context, new DecelerateInterpolator());
        // XListView need the scroll event, and it will dispatch the event to
        // user's listener (as a proxy).
        super.setOnScrollListener(this);

        // init header view
        mHeaderView = new BasisLvRefreshHV(context);
        mHeaderViewContent = (LinearLayout) mHeaderView.findViewById(R.id.ll_lv_header_content);
        mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.tv_lv_header_time);
        addHeaderView(mHeaderView);

        // init footer view
        mFooterView = new BasisLvRefreshFV(context);
        mFooterStatusView = (TextView) mFooterView.findViewById(R.id.tv_lv_footer_status);

        // init header height
        mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHeaderViewHeight = mHeaderViewContent.getHeight();
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        mFooterView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mFooterViewHeight = mFooterStatusView.getHeight();
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        // make sure XListViewFooter is the last footer view, and only add once.
        if (!mIsFooterReady) {//第一次设置适配器时添加脚布局
            mIsFooterReady = true;//再次设置就不在添加
            addFooterView(mFooterView);
        }
        super.setAdapter(adapter);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (getFirstVisiblePosition() == 0
                        && (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)
                        && mEnablePullRefresh) {
                    // the first item is showing, header has shown or pull down.
                    updateHeaderHeight(deltaY / OFFSET_RADIO);
                    invokeOnScrolling();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1
                        && (mFooterView.getBottomMargin() > 0 || deltaY < 0)
                        && getAdapter().getCount() > getChildCount()
                        && mEnablePullLoad) {
                    // last item, already pulled up or want to pull up.
                    updateFooterHeight(-deltaY);
                    invokeOnScrolling();
                }

                break;
            case MotionEvent.ACTION_UP:
                mLastY = -1; // reset
                if (getFirstVisiblePosition() == 0) {
                    // invoke refresh
                    if (mEnablePullRefresh
                            && mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                        startPullRefresh();
                    }
                    resetHeaderHeight();
                } else if (getLastVisiblePosition() == mTotalItemCount - 1) {
                    // invoke load more.
                    if (mEnablePullLoad
                            && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
                            && !mPullLoading) {
                        startLoadMore();
                        resetFooterHeight(true);
                    } else {
                        resetFooterHeight(isShowLoadMore);
                    }
                }

                if (isShowLoadMore) {
                    showFooterDelay();
                } else {
                    if (!mPullLoading) {
                        hideFooterDelay();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * enable or disable pull down refresh feature.
     */
    public void setPullRefreshEnable(boolean enable) {
        mEnablePullRefresh = enable;
        if (mEnablePullRefresh) {
            mHeaderView.setVisibility(View.VISIBLE);
        } else {// disable, hide the content
            mHeaderView.setVisibility(View.GONE);
        }
    }

    /**
     * @param enable 是否允许加载更多功能
     * @param isShow 加载更多初始View是否展示, true 展示, false 不展示
     */
    public void setPullLoadEnable(boolean enable, boolean isShow) {
        mEnablePullLoad = enable;
        isShowLoadMore = isShow;
        if (!mEnablePullLoad) {
            mFooterView.hide();
            mFooterView.setOnClickListener(null);
            //make sure "pull up" don't showDialogSimple a line in bottom when listview with one page
            setFooterDividersEnabled(false);
        } else {
            mPullLoading = false;
            if (isShow) {
                mFooterView.show();
                // both "pull up" and "click" will invoke load more.
                mFooterView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startLoadMore();
                    }
                });
            } else {
                mFooterView.hide();
                mFooterView.setOnClickListener(null);
            }
            mFooterView.setState(BasisLvRefreshFV.STATE_NORMAL);
            //make sure "pull up" don't showDialogSimple a line in bottom when listview with one page
            setFooterDividersEnabled(true);
        }
    }

    /**
     * 开始刷新数据
     */
    private void startPullRefresh() {
        mPullRefreshing = true;
        mHeaderView.setState(BasisLvRefreshHV.STATE_REFRESHING);
        if (mListViewListener != null) {
            //延迟加载更多
            BasisBaseUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListViewListener.onRefresh();
                }
            }, 252);
        }
    }

    /**
     * 开始加载数据
     */
    private void startLoadMore() {
        mPullLoading = true;
        mFooterView.setState(BasisLvRefreshFV.STATE_LOADING);
        if (mListViewListener != null) {
            //延迟加载更多
            BasisBaseUtils.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListViewListener.onLoadMore();
                }
            }, 252);
        }
        setSelection(mTotalItemCount + 1);
    }

    /**
     * 还原所有的状态
     */
    public void resetStatus() {
        stopRefresh();
        stopLoadMore();
    }

    /**
     * stop refresh, reset header view.
     */
    public void stopRefresh() {
        if (mPullRefreshing == true) {
            mPullRefreshing = false;
            resetHeaderHeight();
        }
    }

    /**
     * stop load more, reset footer view.
     */
    public void stopLoadMore() {
        if (mPullLoading == true) {
            mPullLoading = false;
            mFooterView.setState(BasisLvRefreshFV.STATE_NORMAL);
            if (isShowLoadMore) {
                mFooterView.show();
            } else {
                mScrollBack = SCROLLBACK_FOOTER;
                mScroller.startScroll(0, 0, 0, -mFooterViewHeight,
                        SCROLL_DURATION);
                invalidate();
            }
        }
    }

    /**
     * set last refresh time
     */
    public void setRefreshTime(String time) {
        mHeaderTimeView.setVisibility(View.VISIBLE);
        mHeaderTimeView.setText("上次更新时间: " + time);
    }

    private void updateHeaderHeight(float delta) {
        mHeaderView.setVisiableHeight((int) delta + mHeaderView.getVisiableHeight());
        if (mEnablePullRefresh && !mPullRefreshing) { // 未处于刷新状态，更新箭头
            if (mHeaderView.getVisiableHeight() > mHeaderViewHeight) {
                mHeaderView.setState(BasisLvRefreshHV.STATE_READY);
            } else {
                mHeaderView.setState(BasisLvRefreshHV.STATE_NORMAL);
            }
        }
        setSelection(0); // scroll to top each time
    }

    /**
     * reset header view's height.
     */
    private void resetHeaderHeight() {
        int height = mHeaderView.getVisiableHeight();
        if (height == 0) // not visible.
            return;
        // refreshing and header isn't shown fully. do nothing.
        if (mPullRefreshing && height <= mHeaderViewHeight) {
            return;
        }
        int finalHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to showDialogSimple all the header.
        if (mPullRefreshing && height > mHeaderViewHeight) {
            finalHeight = mHeaderViewHeight;
        }
        mScrollBack = SCROLLBACK_HEADER;
        mScroller.startScroll(0, height, 0, finalHeight - height,
                SCROLL_DURATION);
        // trigger computeScroll
        invalidate();
    }

    private void updateFooterHeight(float delta) {
        int height = mFooterView.getBottomMargin() + (int) delta;
        if (mEnablePullLoad && !mPullLoading) {
            if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load more.
                mFooterView.setState(BasisLvRefreshFV.STATE_READY);
            } else {
                mFooterView.setState(BasisLvRefreshFV.STATE_NORMAL);
            }
        }
        mFooterView.setBottomMargin(height);
    }

    private void resetFooterHeight(boolean isShow) {
        int bottomMargin = mFooterView.getBottomMargin();
        if (isShow) {
            if (bottomMargin > 0) {
                mScrollBack = SCROLLBACK_FOOTER;
                mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
                        SCROLL_DURATION);
                invalidate();
            }
        } else {
            mScrollBack = SCROLLBACK_FOOTER;
            mScroller.startScroll(0, bottomMargin, 0, -mFooterViewHeight,
                    SCROLL_DURATION);
            invalidate();
        }
    }

    private void showFooterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFooterView.show();
            }
        }, 525);
    }

    private void hideFooterDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mFooterView.hide();
            }
        }, 525);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (mScrollBack == SCROLLBACK_HEADER) {
                mHeaderView.setVisiableHeight(mScroller.getCurrY());
            } else {
                mFooterView.setBottomMargin(mScroller.getCurrY());
            }
            postInvalidate();
            invokeOnScrolling();
        }
        super.computeScroll();
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l;
    }

    private void invokeOnScrolling() {
        if (mScrollListener instanceof OnBasisScrollListener) {
            OnBasisScrollListener l = (OnBasisScrollListener) mScrollListener;
            l.onBasisScrolling(this);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mScrollListener != null) {
            mScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // send to user's listener
        mTotalItemCount = totalItemCount;
        if (mScrollListener != null) {
            mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
                    totalItemCount);
        }
    }

    public void setBasisLvRefreshListener(BasisLvRefreshListener l) {
        mListViewListener = l;
    }

    /**
     * you can listen ListView.OnScrollListener or this one. it will invoke
     * onBasisScrolling when header/footer scroll back.
     */
    public interface OnBasisScrollListener extends OnScrollListener {
        void onBasisScrolling(View view);
    }

    /**
     * implements this interface to get refresh/load more event.
     */
    public interface BasisLvRefreshListener {
        void onRefresh();

        void onLoadMore();
    }
}
