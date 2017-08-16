package com.gingold.basislibrary.adapter.rv;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * 头布局和脚布局进行特殊处理工具类
 * 使头布局和脚布局都占据一行
 */
public class BasisRvWrapperUtils {

    public interface SpanSizeCallback {
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
    }

    //对GridLayoutManager的头布局和脚布局进行特殊处理
    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, final SpanSizeCallback callback) {

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);//
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        } else {
            innerAdapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    //对StaggeredGridLayoutManager的头布局和脚布局进行特殊处理
    public static void setFullSpan(RecyclerView.ViewHolder holder, boolean state) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(state);
        }
    }
}
