package com.gingold.basislibrary.adapter.rv;

import android.support.v4.util.SparseArrayCompat;

/**
 * item多类型管理器
 */
public class BasisRvItemViewDelegateManager<T> {
    //item类型集合
    SparseArrayCompat<BasisRvItemViewDelegate<T>> delegates = new SparseArrayCompat();

    //item不同类型总数
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    //添加新的类型的item
    public BasisRvItemViewDelegateManager<T> addDelegate(BasisRvItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public BasisRvItemViewDelegateManager<T> addDelegate(int viewType, BasisRvItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An BasisRvItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered BasisRvItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public BasisRvItemViewDelegateManager<T> removeDelegate(BasisRvItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("BasisRvItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public BasisRvItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    //获取当前位置的item类型
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            BasisRvItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No BasisRvItemViewDelegate added that matches position=" + position + " in data source");
    }

    //初始化布局和数据
    public void initView(BasisRvViewHolder holder, T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            BasisRvItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(data, position)) {
                delegate.initView(holder, data, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No BasisLvGvItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public BasisRvItemViewDelegate getItemViewDelegate(int viewType) {
        return delegates.get(viewType);
    }

    public int getItemViewLayoutId(int viewType) {
        return getItemViewDelegate(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(BasisRvItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }
}
