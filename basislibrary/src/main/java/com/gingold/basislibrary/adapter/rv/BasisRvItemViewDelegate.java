package com.gingold.basislibrary.adapter.rv;


/**
 * 存在多种类型item时
 *
 * @param <T> item的数据类型
 */

public interface BasisRvItemViewDelegate<T> {

    /**
     * 当前类型item的layoutid
     */
    int getItemViewLayoutId();

    /**
     * 判断当前类型的 BasisItemViewDelegate 是否是符合 data和position 的类型
     */
    boolean isForViewType(T data, int position);

    /**
     * 当前item的holder,数据,和position
     */
    void initView(BasisRvViewHolder holder, T data, int position);

}
