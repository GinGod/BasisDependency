package com.gingold.basisdependency.adapter;

import android.content.Context;

import com.gingold.basisdependency.data.LVRVData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvMultiAdapter;

import java.util.List;

/**
 *
 */

public class MultiLvAdapter extends BasisLvGvMultiAdapter<LVRVData.LVBean> {
    public MultiLvAdapter(Context context, List<LVRVData.LVBean> datas) {
        super(context, datas);
    }

    @Override
    public void addItemViewDelegate() {
        addItemViewDelegate(new Type1Delagate());
        addItemViewDelegate(new Type2Delagate());
        addItemViewDelegate(new Type3Delagate());
    }
}
