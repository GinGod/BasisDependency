package com.gingold.basislibrary.adapter.lvgv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 存在多种类型item的适配器
 *
 * @param <T> item的数据类型
 */
public abstract class BasisLvGvMultiAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    private BasisLvGvItemViewDelegateManager mItemViewDelegateManager;

    public BasisLvGvMultiAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mItemViewDelegateManager = new BasisLvGvItemViewDelegateManager();

        addItemViewDelegate();
    }

    public abstract void addItemViewDelegate();

    //增加item的类型
    public BasisLvGvMultiAdapter addItemViewDelegate(BasisLvGvItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    //是否使用多种类型条目
    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getViewTypeCount() {
        if (useItemViewDelegateManager()) {
            //存在多种类型条目
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (useItemViewDelegateManager()) {
            //根据当前位置和数据获取type
            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
            return viewType;
        }
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BasisLvGvItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        int layoutId = itemViewDelegate.getItemViewLayoutId();

        BasisLvGvViewHolder basisViewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            basisViewHolder = new BasisLvGvViewHolder(mContext, itemView, parent, position);
            basisViewHolder.mLayoutId = layoutId;
            onViewHolderCreated(basisViewHolder, basisViewHolder.getConvertView());
        } else {
            basisViewHolder = (BasisLvGvViewHolder) convertView.getTag();
            basisViewHolder.mPosition = position;
        }

        //初始化布局和数据
        initView(basisViewHolder, getItem(position), position);
        return basisViewHolder.getConvertView();
    }

    //初始化布局和数据
    protected void initView(BasisLvGvViewHolder basisViewHolder, T data, int position) {
        mItemViewDelegateManager.initView(basisViewHolder, data, position);
    }

    public void onViewHolderCreated(BasisLvGvViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
