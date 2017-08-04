package com.gingold.basislibrary.adapter.lvgv;


import android.support.v4.util.SparseArrayCompat;

public class ItemViewDelegateManager<T> {
    //item类型
    SparseArrayCompat<BasisItemViewDelegate<T>> delegates = new SparseArrayCompat();

    //item不同类型总数
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    //添加新的类型的item
    public ItemViewDelegateManager<T> addDelegate(BasisItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public ItemViewDelegateManager<T> addDelegate(int viewType, BasisItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An BasisItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered BasisItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(BasisItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("BasisItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    //获取当前位置的item类型
    public int getItemViewType(T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            BasisItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {//是符合类型的 BasisItemViewDelegate
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No BasisItemViewDelegate added that matches position=" + position + " in data source");
    }

    //初始化布局和数据
    public void initView(BasisViewHolder holder, T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            BasisItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {
                //初始化布局和数据
                delegate.initView(holder, data, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(BasisItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    //根据data和position获取ItemViewDelegate
    public BasisItemViewDelegate getItemViewDelegate(T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            BasisItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No BasisItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }
}
