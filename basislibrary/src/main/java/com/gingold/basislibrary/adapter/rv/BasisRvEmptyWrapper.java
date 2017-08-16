package com.gingold.basislibrary.adapter.rv;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView.Adapter数据为空时显示布局
 */
public class BasisRvEmptyWrapper<T> extends BasisRvSpecificAdapter {
    public static int ITEM_TYPE_EMPTY = Integer.MAX_VALUE - 252;

    private View mEmptyView;
    private int mEmptyLayoutId;


    public BasisRvEmptyWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;
        ITEM_TYPE_EMPTY = ITEM_TYPE_EMPTY - 252;
    }

    private boolean isEmpty() {
        return (mEmptyView != null || mEmptyLayoutId != 0) && getRealItemCount() == 0;//adapter的数据为空
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isEmpty()) {//adapter的数据为空
            BasisRvViewHolder holder;
            if (mEmptyView != null) {
                holder = BasisRvViewHolder.createViewHolder(parent.getContext(), mEmptyView);
            } else {
                holder = BasisRvViewHolder.createViewHolder(parent.getContext(), parent, mEmptyLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty()) {
            return ITEM_TYPE_EMPTY;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isEmpty()) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        if (isEmpty()) return 1;
        return getInnerItemCount();
    }

    /**
     * 获取内容item总数
     */
    public int getInnerItemCount() {
        return mInnerAdapter.getItemCount();
    }

    /**
     * 内容item总个数
     */
    @Override
    public int getRealItemCount() {
        if (mInnerAdapter instanceof BasisRvSpecificAdapter) {
            return ((BasisRvSpecificAdapter)mInnerAdapter).getRealItemCount();
        }
        return getInnerItemCount();
    }

    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }

    public void setEmptyView(int layoutId) {
        mEmptyLayoutId = layoutId;
    }

    @Override
    public boolean isSpecific(int position) {
        if (mInnerAdapter instanceof BasisRvSpecificAdapter) {
            BasisRvSpecificAdapter adapter = (BasisRvSpecificAdapter) mInnerAdapter;
            return isEmpty() || adapter.isSpecific(position);
        }
        return isEmpty();
    }
}
