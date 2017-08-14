package com.gingold.basislibrary.adapter.lvgv;

import android.content.Context;

import java.util.List;

/**
 * 适用ListView和GridView的适配器
 */
public abstract class BasisLvGvAdapter<T> extends BasisLvGvMultiAdapter<T> {

    public BasisLvGvAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        //默认添加一种类型item
        addItemViewDelegate(new BasisLvGvItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T data, int position) {
                return true;
            }

            @Override
            public void initView(BasisLvGvViewHolder holder, T data, int position) {
                BasisLvGvAdapter.this.initView(holder, data, position);
            }
        });
    }

    @Override
    public void addItemViewDelegate() {

    }

    protected abstract void initView(BasisLvGvViewHolder basisViewHolder, T data, int position);

}
