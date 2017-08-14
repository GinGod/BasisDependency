package com.gingold.basisdependency.activity;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.adapter.MultiLvAdapter;
import com.gingold.basisdependency.data.LVRVData;

/**
 *
 */

public class MultiLvActivity extends BaseActivity {
    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_multilv);
        initTitle("lvgv测试", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        findGridView(R.id.gv_multilv).setAdapter(new MultiLvAdapter(context, LVRVData.lvrvList));
    }

    @Override
    public void onClick(View v) {

    }
}
