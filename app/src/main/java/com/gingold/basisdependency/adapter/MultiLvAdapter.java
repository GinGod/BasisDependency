package com.gingold.basisdependency.adapter;

import android.content.Context;

import com.gingold.basisdependency.data.LVData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvMultiAdapter;

import java.util.List;

/**
 *
 */

public class MultiLvAdapter extends BasisLvGvMultiAdapter<LVData.LVBean> {
    public MultiLvAdapter(Context context, List<LVData.LVBean> datas) {
        super(context, datas);
        addItemViewDelegate(new Type1Delagate());
        addItemViewDelegate(new Type2Delagate());
        addItemViewDelegate(new Type3Delagate());
    }
}
