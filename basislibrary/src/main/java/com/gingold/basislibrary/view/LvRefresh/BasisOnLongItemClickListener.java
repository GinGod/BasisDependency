package com.gingold.basislibrary.view.LvRefresh;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 条目长按按监听
 */
public abstract class BasisOnLongItemClickListener implements AdapterView.OnItemLongClickListener {
    public ListView basisListView;

    public BasisOnLongItemClickListener(ListView basisListView) {
        this.basisListView = basisListView;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //头布局脚布局不加入条目点击事件
        int headerCount = basisListView.getHeaderViewsCount();
        int footerCount = basisListView.getFooterViewsCount();
        if (position > (headerCount - 1) && position < (basisListView.getAdapter().getCount() - footerCount)) {
            onBasisItemLongClick(parent, view, position - headerCount, id);
        }
        return false;
    }

    public abstract void onBasisItemLongClick(AdapterView<?> parent, View view, int position, long id);
}
