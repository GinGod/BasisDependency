package com.gingold.basislibrary.adapter.rv;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * RecyclerView.Adapter头布局脚布局装饰器
 */
public class BasisRvHeaderAndFooterWrapper<T> extends BasisRvSpecificAdapter {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();//头布局集合
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();//脚布局集合

    public BasisRvHeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        mInnerAdapter = adapter;//内容adapter
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) { //当前是头布局
            BasisRvViewHolder holder = BasisRvViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
            return holder;

        } else if (mFootViews.get(viewType) != null) {//当前是脚布局
            BasisRvViewHolder holder = BasisRvViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);//正常内容
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);//返回每个头布局的viewType
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());//返回每个脚布局的viewType
        }
        return mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {//当前是头布局
            return;
        }
        if (isFooterViewPos(position)) {//当前是脚布局
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    /**
     * 头布局个数
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 脚布局个数
     */
    public int getFootersCount() {
        return mFootViews.size();
    }

    /**
     * 内容item总个数
     */
    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();//头布局的position
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();//脚布局的position
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    @Override
    public boolean isSpecific(int position) {
        if (mInnerAdapter instanceof BasisRvSpecificAdapter) {
            BasisRvSpecificAdapter adapter = (BasisRvSpecificAdapter) mInnerAdapter;
            return isHeaderViewPos(position) || isFooterViewPos(position) || adapter.isSpecific(position  - getHeadersCount());
        }
        return isHeaderViewPos(position) || isFooterViewPos(position);
    }

}
