package com.gingold.basisdependency.adapter;

import android.content.Context;
import android.graphics.Color;

import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.LVRVData;
import com.gingold.basislibrary.adapter.rv.BasisRvItemViewDelegate;
import com.gingold.basislibrary.adapter.rv.BasisRvMultiAdapter;
import com.gingold.basislibrary.adapter.rv.BasisRvViewHolder;

import java.util.List;

/**
 *
 */

public class MultiRvAdapter extends BasisRvMultiAdapter<LVRVData.LVBean> {
    public MultiRvAdapter(Context context, List<LVRVData.LVBean> datas) {
        super(context, datas);
    }

    @Override
    public void addItemViewDelegate() {
        addItemViewDelegate(new BasisRvItemViewDelegate<LVRVData.LVBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_textview;
            }

            @Override
            public boolean isForViewType(LVRVData.LVBean data, int position) {
                return data.status == 1;
            }

            @Override
            public void initView(BasisRvViewHolder holder, LVRVData.LVBean data, int position) {
                holder.setTvText(R.id.tv_item_main, data.des).setTvColor(R.id.tv_item_main, Color.BLUE);
            }
        });

        addItemViewDelegate(new BasisRvItemViewDelegate<LVRVData.LVBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_textview;
            }

            @Override
            public boolean isForViewType(LVRVData.LVBean data, int position) {
                return data.status == 2;
            }

            @Override
            public void initView(BasisRvViewHolder holder, LVRVData.LVBean data, int position) {
                holder.setTvText(R.id.tv_item_main, data.des).setTvColor(R.id.tv_item_main, Color.GREEN);
            }
        });

        addItemViewDelegate(new BasisRvItemViewDelegate<LVRVData.LVBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_textview;
            }

            @Override
            public boolean isForViewType(LVRVData.LVBean data, int position) {
                return data.status == 3;
            }

            @Override
            public void initView(BasisRvViewHolder holder, LVRVData.LVBean data, int position) {
                holder.setTvText(R.id.tv_item_main, data.des).setTvColor(R.id.tv_item_main, Color.RED);
            }
        });
    }
}
