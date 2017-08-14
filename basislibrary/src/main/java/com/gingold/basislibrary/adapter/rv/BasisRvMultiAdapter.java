package com.gingold.basislibrary.adapter.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 存在多种类型item的适配器
 *
 * @param <T> item的数据类型
 */
public abstract class BasisRvMultiAdapter<T> extends BasisRvSpecificAdapter {
    protected Context mContext;
    protected List<T> mDatas;

    protected BasisRvItemViewDelegateManager mItemViewDelegateManager;
    protected OnItemClickListener mOnItemClickListener;

    public BasisRvMultiAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new BasisRvItemViewDelegateManager();

        addItemViewDelegate();
    }

    /**
     * 增加不同类型的item
     */
    public abstract void addItemViewDelegate();

    //增加item的类型
    public BasisRvMultiAdapter addItemViewDelegate(BasisRvItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    //是否使用多种类型条目
    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        //存在多种类型条目
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BasisRvItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        BasisRvViewHolder holder = BasisRvViewHolder.createViewHolder(mContext, parent, layoutId);

        onViewHolderCreated(holder, holder.getConvertView());

        return holder;
    }

    public void onViewHolderCreated(BasisRvViewHolder holder, View itemView) {
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //初始化布局和数据
        initView((BasisRvViewHolder) holder, mDatas.get(position), position);
        //条目点击事件
        setListener((BasisRvViewHolder) holder, mDatas.get(position), position);
    }

    //初始化布局和数据
    public void initView(BasisRvViewHolder holder, T data, int position) {
        mItemViewDelegateManager.initView(holder, data, position);
    }

    /**
     * 设置不同类型item是否响应点击事件
     *
     * @param position item位置
     * @return true 响应, flase 不响应
     */
    protected boolean isNotEnabled(T data, int position) {
        return false;
    }

    //条目点击事件
    protected void setListener(final BasisRvViewHolder viewHolder, final T data, final int position) {
        if (isNotEnabled(data, position)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, viewHolder, position);
                } else {
                    onItemClickListener(v, viewHolder, data, position);
                }
            }
        });

        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                } else {
                    return onItemLongClickListener(v, viewHolder, data, position);
                }
            }
        });
    }

    /**
     * 条目短按点击事件(adapter设置点击事件监听后失效)
     */
    public void onItemClickListener(View v, BasisRvViewHolder viewHolder, T data, int position) {
    }

    /**
     * 条目长按点击事件(adapter设置点击事件监听后失效)
     */
    public boolean onItemLongClickListener(View v, BasisRvViewHolder viewHolder, T data, int position) {
        return false;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public List<T> getDatas() {
        return mDatas;
    }

    public BasisRvMultiAdapter addItemViewDelegate(int viewType, BasisRvItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, BasisRvViewHolder holder, int position);

        boolean onItemLongClick(View view, BasisRvViewHolder holder, int position);
    }

    /**
     * 设置条目点击事件
     */
    public void onItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public boolean isSpecific(int position) {
        return false;
    }
}
