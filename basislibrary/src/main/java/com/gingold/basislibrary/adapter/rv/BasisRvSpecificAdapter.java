package com.gingold.basislibrary.adapter.rv;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 含有特殊布局的adapter
 */

public abstract class BasisRvSpecificAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public RecyclerView.Adapter mInnerAdapter;//内容adapter

    /**
     * 特殊布局的item
     *
     * @param position 特殊布局的位置
     * @return true: 当前位置的item占据一整行, false: 正常布局
     */
    public abstract boolean isSpecific(int position);

    /**
     * 获取去除头布局和脚布局后的item个数
     */
    public abstract int getRealItemCount();

    /**
     * 对GridLayoutManager的特殊局进行特殊处理
     */
    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mInnerAdapter != null) {
            BasisRvWrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new BasisRvWrapperUtils.SpanSizeCallback() {
                @Override
                public int getSpanSize(GridLayoutManager gridLayoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                    if (isSpecific(position)) {
                        return gridLayoutManager.getSpanCount();//特殊布局显示一行
                    }
                    if (oldLookup != null) {
                        return oldLookup.getSpanSize(position);
                    }
                    return 1;
                }
            });
        } else {

        }

    }

    /**
     * 对StaggeredGridLayoutManager的特殊局进行特殊处理
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (mInnerAdapter != null) {
//            mInnerAdapter.onViewAttachedToWindow(holder);
            int position = holder.getLayoutPosition();
            if (isSpecific(position)) {
                BasisRvWrapperUtils.setFullSpan(holder, true);//特殊布局显示一行
            } else {
                BasisRvWrapperUtils.setFullSpan(holder, false);
            }
        }
    }
}
