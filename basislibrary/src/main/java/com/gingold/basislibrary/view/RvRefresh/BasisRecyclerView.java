package com.gingold.basislibrary.view.RvRefresh;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.gingold.basislibrary.adapter.rv.BasisRvEmptyWrapper;
import com.gingold.basislibrary.adapter.rv.BasisRvHeaderAndFooterWrapper;

import java.util.ArrayList;

/**
 * 这是一个带下拉刷新和上拉加载的revycleview
 */
public class BasisRecyclerView extends RecyclerView {
    /**
     * 是否可以刷新的常量
     */
    private boolean pullRefreshEnabled = true;
    /**
     * 是否可以加载更多的常量
     */
    private boolean loadMoreEnabled = false;
    private boolean loadMoreEnabledState = false;//记录loadMore设置状态
    private boolean shouldLoadMore = false;//根据滑动操作判断是否应该加载更多
    private int loadMoreThreshold = 252 / 2;//根据滑动操作判断是否应该加载更多
    /**
     * 头布局集合
     */
    private ArrayList<View> mHeaderViews = new ArrayList<>();
    /**
     * 脚布局集合
     */
    private ArrayList<View> mFootViews = new ArrayList<>();
    //当前的头布局
    private BasisRvRefreshHV mHeadView;
    //当前的脚布局
    private BasisRvRefreshFV mFootView;
    //adapter没有数据的时候显示,类似于listView的emptyView
    private View mEmptyView;
    //监听器
    private OnRefreshAndLoadMoreListener refreshAndLoadMoreListener;
    //加载数据中的状态
    private boolean isLoadingData = false;
    //没有数据的状态
    private boolean isNoMore = false;
    //阻率
    private static final float DRAG_RATE = 2;
    /**
     * 最后的y坐标
     */
    private float mLastY = -1;
    private float mDownY = -1;
    private BasisRvHeaderAndFooterWrapper mWrapAdapter;
    private BasisRvEmptyWrapper mEmptyWrapper;
    private BasisRvHeaderAndFooterWrapper mHeaderAndFooterWrapper;

    public BasisRecyclerView(Context context) {
        this(context, null);
    }

    public BasisRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasisRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 这是一个初始化的方法
     */
    private void init() {
        //头布局
        mHeadView = new BasisRvRefreshHV(getContext());
        //脚布局
        mFootView = new BasisRvRefreshFV(getContext());
    }

    /**
     * 当滚动状态改变的时候
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        if (state == RecyclerView.SCROLL_STATE_IDLE && refreshAndLoadMoreListener != null && !isLoadingData && loadMoreEnabled && shouldLoadMore) {
            LayoutManager layoutManager = getLayoutManager();
            final int lastVisibleItemPosition;
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 2
                    && layoutManager.getItemCount() > layoutManager.getChildCount()
                    && !isNoMore && mHeadView.getStatus() < BasisRvRefreshHV.STATE_REFRESHING
                    && loadMoreEnabled) {

                isLoadingData = true;
                mFootView.setState(BasisRvRefreshFV.STATE_LOADING);
                refreshAndLoadMoreListener.onLoadMore();
                shouldLoadMore = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        smoothScrollToPosition(lastVisibleItemPosition + 1);
                    }
                }, 252);
            }
        }
    }

    /**
     * 获取最大值
     */
    private int findMax(int[] into) {
        int max = into[0];
        for (int value : into) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 控件的触摸事件的监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mLastY == -1) {
            mLastY = e.getRawY();
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = e.getRawY();
                mDownY = e.getRawY();
                shouldLoadMore = false;
                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = e.getRawY() - mLastY;
                mLastY = e.getRawY();
                if (isOnTop() && pullRefreshEnabled) {
                    mHeadView.onMove(deltaY / DRAG_RATE);
                    if (mHeadView.getVisibleHeight() > 0 && mHeadView.getStatus() < mHeadView.STATE_REFRESHING) {
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if ((mDownY - e.getRawY()) > loadMoreThreshold) {
                    shouldLoadMore = true;
                } else {
                    shouldLoadMore = false;
                }
                mDownY = -1;
            default:
                //复位
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mHeadView.releaseAction()) {
                        if (refreshAndLoadMoreListener != null) {
                            refreshAndLoadMoreListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 是否是顶部
     */
    public boolean isOnTop() {
        return mHeadView.getParent() != null;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mHeaderAndFooterWrapper = new BasisRvHeaderAndFooterWrapper(adapter);
        //添加头布局和脚布局
        initHeadAndFootView();
        mEmptyWrapper = new BasisRvEmptyWrapper(mHeaderAndFooterWrapper);
        if (mEmptyView != null) {
            mEmptyWrapper.setEmptyView(mEmptyView);
        }
        mWrapAdapter = new BasisRvHeaderAndFooterWrapper(mEmptyWrapper);
        mWrapAdapter.addHeaderView(mHeadView);
        mWrapAdapter.addFootView(mFootView);
        super.setAdapter(mWrapAdapter);
    }

    /**
     * 添加头布局和脚布局
     */
    private void initHeadAndFootView() {
        for (int i = 0; i < mHeaderViews.size(); i++) {
            mHeaderAndFooterWrapper.addHeaderView(mHeaderViews.get(i));
        }

        for (int i = 0; i < mFootViews.size(); i++) {
            mHeaderAndFooterWrapper.addFootView(mFootViews.get(i));
        }
    }

    /**
     * 设置没有更多数据了
     *
     * @param noMore
     */
    public void setNoMoreData(boolean noMore) {
        this.isNoMore = noMore;
        mFootView.setState(isNoMore ? BasisRvRefreshFV.STATE_NOMORE : BasisRvRefreshFV.STATE_COMPLETE);
        notifyDataSetChanged();
    }

    /**
     * 获取一个空布局
     *
     * @return
     */
    public View getmEmptyView() {
        return mEmptyView;
    }

    /**
     * 还原所有的状态
     */
    public void resetStatus() {
        setloadMoreComplete();
        refreshComplete();
        notifyDataSetChanged();
    }

    /**
     * 设置刷新完成
     */
    private void refreshComplete() {
        mHeadView.refreshComplete();
        notifyDataSetChanged();
    }

    /**
     * 设置加载更多完成
     */
    public void setloadMoreComplete() {
        //设置加载数据为false
        isLoadingData = false;
        mFootView.setState(BasisRvRefreshFV.STATE_COMPLETE);
        notifyDataSetChanged();
    }

    /**
     * 设置刷新和加载更多的监听器
     */
    public void setRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener refreshAndLoadMoreListener) {
        this.refreshAndLoadMoreListener = refreshAndLoadMoreListener;
    }

    /**
     * 刷新和加载更多的监听器
     */
    public interface OnRefreshAndLoadMoreListener {
        void onRefresh();

        void onLoadMore();
    }

    /**
     * 设置是否启用下拉刷新功能
     */
    public void setReFreshEnabled(boolean isEnabled) {
        pullRefreshEnabled = isEnabled;
    }

    /**
     * 设置刷新完成
     */
    public void setReFreshComplete() {
        mHeadView.refreshComplete();
    }

    /**
     * 设置是否启用上拉加载功能
     */
    public void setLoadMoreEnabled(boolean isEnabled) {
        loadMoreEnabled = isEnabled;
        loadMoreEnabledState = isEnabled;
    }

    /**
     * 设置是否刷新ing状态
     */
    public void setRefreshing(boolean refreshing) {
        if (refreshing && pullRefreshEnabled && refreshAndLoadMoreListener != null) {
            mHeadView.setState(BasisRvRefreshHV.STATE_REFRESHING);
            mHeadView.onMove(mHeadView.getMeasuredHeight());
            refreshAndLoadMoreListener.onRefresh();
        }
    }

    /**
     * 添加头布局(必须在设置适配器之前设置才能生效)
     */
    public void addHeadView(View headView) {
        mHeaderViews.add(headView);
    }

    /**
     * 添加底部布局(必须在设置适配器之前设置才能生效)
     */
    public void addFootView(View footView) {
        mFootViews.add(footView);
    }

    /**
     * 设置可以下拉加载的空布局(必须在设置适配器之前设置才能生效)
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    /**
     * 刷新适配器
     */
    public void notifyDataSetChanged() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mWrapAdapter != null) {
                    mWrapAdapter.notifyDataSetChanged();
                }

                if (mWrapAdapter.getRealItemCount() == 0) {
                    setLoadMoreEnabled(false);
                } else {
                    setLoadMoreEnabled(loadMoreEnabledState);
                }
            }
        }, 252);
    }
}
