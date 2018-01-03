package com.gingold.basislibrary.view.LvRefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 条目短按监听
 */
public abstract class BasisOnItemClickListener implements AdapterView.OnItemClickListener {
    public ListView basisListView;

    public BasisOnItemClickListener(ListView basisListView) {
        this.basisListView = basisListView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //头布局脚布局不加入条目点击事件
        int headerCount = basisListView.getHeaderViewsCount();
        int footerCount = basisListView.getFooterViewsCount();
        if (position > (headerCount - 1) && position < (basisListView.getAdapter().getCount() - footerCount)) {
            onBasisItemClick(parent, view, position - headerCount, id);
        }
    }

    public abstract void onBasisItemClick(AdapterView<?> parent, View view, int position, long id);
}
