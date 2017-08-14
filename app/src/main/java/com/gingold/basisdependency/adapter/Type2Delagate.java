package com.gingold.basisdependency.adapter;

import android.graphics.Color;

import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.LVRVData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvItemViewDelegate;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;

/**
 *
 */

public class Type2Delagate implements BasisLvGvItemViewDelegate<LVRVData.LVBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_textview;
    }

    @Override
    public boolean isForViewType(LVRVData.LVBean data, int position) {
        return data.status == 2;
    }

    @Override
    public void initView(BasisLvGvViewHolder holder, LVRVData.LVBean data, int position) {
        holder.setTvText(R.id.tv_item_main, data.des).setTvColor(R.id.tv_item_main, Color.GREEN);
    }
}
