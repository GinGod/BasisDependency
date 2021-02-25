package com.gingold.basislibrary.adapter.lvgv;


import androidx.collection.SparseArrayCompat;

/**
 * item多类型管理器
 */
public class BasisLvGvItemViewDelegateManager<T> {
    //item类型集合
    SparseArrayCompat<BasisLvGvItemViewDelegate<T>> delegates = new SparseArrayCompat();

    //item不同类型总数
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    //添加新的类型的item
    public BasisLvGvItemViewDelegateManager<T> addDelegate(BasisLvGvItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        if (delegate != null) {
            delegates.put(viewType, delegate);
            viewType++;
        }
        return this;
    }

    public BasisLvGvItemViewDelegateManager<T> addDelegate(int viewType, BasisLvGvItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An BasisLvGvItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered BasisLvGvItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    public BasisLvGvItemViewDelegateManager<T> removeDelegate(BasisLvGvItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("BasisLvGvItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    public BasisLvGvItemViewDelegateManager<T> removeDelegate(int itemType) {
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
            BasisLvGvItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {//是符合类型的 BasisLvGvItemViewDelegate
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No BasisLvGvItemViewDelegate added that matches position=" + position + " in data source");
    }

    //初始化布局和数据
    public void initView(BasisLvGvViewHolder holder, T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            BasisLvGvItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {
                //初始化布局和数据
                delegate.initView(holder, data, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No BasisLvGvItemViewDelegateManager added that matches position=" + position + " in data source");
    }


    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    public int getItemViewType(BasisLvGvItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    //根据data和position获取ItemViewDelegate
    public BasisLvGvItemViewDelegate getItemViewDelegate(T data, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            BasisLvGvItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(data, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No BasisLvGvItemViewDelegate added that matches position=" + position + " in data source");
    }

    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }
}
