package com.gingold.basisdependency.adapter;

import android.graphics.Color;

import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.LVData;
import com.gingold.basislibrary.adapter.lvgv.BasisItemViewDelegate;
import com.gingold.basislibrary.adapter.lvgv.BasisViewHolder;

/**
 *
 */

public class Type3Delagate implements BasisItemViewDelegate<LVData.LVBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_textview;
    }

    @Override
    public boolean isForViewType(LVData.LVBean data, int position) {
        return data.status == 3;
    }

    @Override
    public void initView(BasisViewHolder holder, LVData.LVBean data, int position) {
        holder.setTvText(R.id.tv_item_main, data.des).setTvColor(R.id.tv_item_main, Color.CYAN);
    }
}
