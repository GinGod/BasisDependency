package com.gingold.basislibrary.adapter.rv;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * 适用RecyclerView的适配器
 */
public abstract class BasisRvAdapter<T> extends BasisRvMultiAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public BasisRvAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        //默认添加一种类型item
        addItemViewDelegate(new BasisRvItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T data, int position) {
                return true;
            }

            @Override
            public void initView(BasisRvViewHolder holder, T data, int position) {
                BasisRvAdapter.this.initView(holder, data, position);
            }
        });
    }

    @Override
    public void addItemViewDelegate() {

    }

    public abstract void initView(BasisRvViewHolder holder, T data, int position);
}
