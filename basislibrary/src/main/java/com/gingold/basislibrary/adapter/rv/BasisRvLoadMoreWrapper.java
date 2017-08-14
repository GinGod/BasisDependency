package com.gingold.basislibrary.adapter.rv;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView.Adapter加载更多装饰器
 * 当数据为空时会一直重复加载更多, 不建议使用
 */
public class BasisRvLoadMoreWrapper<T> extends BasisRvSpecificAdapter {
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

    private View mLoadMoreView;
    private int mLoadMoreLayoutId;
    private Handler mHandler = new Handler();

    public BasisRvLoadMoreWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
    }

    /**
     * 是否有加载更多功能
     */
    private boolean hasLoadMore() {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }

    /**
     * 当前position是否是加载更多
     */
    private boolean isShowLoadMore(int position) {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowLoadMore(position)) {
            return ITEM_TYPE_LOAD_MORE;//当前位置是加载更多
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE) {//加载更多holder
            BasisRvViewHolder holder;
            if (mLoadMoreView != null) {
                holder = BasisRvViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else {
                holder = BasisRvViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isShowLoadMore(position)) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMoreRequested();//当加载更多显示时,调用方法
                    } else {
                        onLoadMoreListener();//加载更多方法
                    }
                }
            }, 500);
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }

    /**
     * 加载更多的监听
     */
    public interface OnLoadMoreListener {
        /**
         * 加载更多时调用的方法
         */
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * 设置加载更多监听
     */
    public BasisRvLoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        if (loadMoreListener != null) {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    /**
     * 加载更多方法, 当设置加载更多监听时, 方法则失效
     */
    public void onLoadMoreListener() {

    }

    /**
     * 设置加载更多显示的view
     */
    public BasisRvLoadMoreWrapper setLoadMoreView(View loadMoreView) {
        mLoadMoreView = loadMoreView;
        return this;
    }

    /**
     * 设置加载更多显示的layoutId
     */
    public BasisRvLoadMoreWrapper setLoadMoreView(int layoutId) {
        mLoadMoreLayoutId = layoutId;
        return this;
    }

    @Override
    public boolean isSpecific(int position) {
        if (mInnerAdapter instanceof BasisRvSpecificAdapter) {
            BasisRvSpecificAdapter adapter = (BasisRvSpecificAdapter) mInnerAdapter;
            return isShowLoadMore(position) || adapter.isSpecific(position);
        }
        return isShowLoadMore(position);
    }
}

