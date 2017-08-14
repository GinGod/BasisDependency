package com.gingold.basisdependency.activity;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.SPData;
import com.gingold.basisdependency.data.TestData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;
import com.gingold.basislibrary.utils.BasisSPUtils;

/**
 *
 */

public class SPActivity extends BaseActivity {
    private int times = 0;
    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_sp);
        initTitle("SP测试", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        findListView(R.id.lv_sp).setAdapter(new BasisLvGvAdapter<String>(mActivity, R.layout.item_textview, TestData.SPList) {
            @Override
            protected void initView(BasisLvGvViewHolder basisViewHolder, final String data, int position) {
                basisViewHolder.setTvTextListener(R.id.tv_item_main, data, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (data) {
                            case SPData.STRING:
                                BasisSPUtils.setStringPreferences(mActivity, "string", "string" + times);
                                BasisSPUtils.setStringPreferences(mActivity, "string", "string", "string" + times);
                                toast(BasisSPUtils.getStringPreference(mActivity, "string", "错误"));
                                toast(BasisSPUtils.getStringPreference(mActivity, "string", "string", "错误") + "第二次");
                                break;case SPData.INT:
                                BasisSPUtils.setIntPreferences(mActivity, "int", 252 + times);
                                BasisSPUtils.setIntPreferences(mActivity, "int", "int", 252 + times);
                                toast(BasisSPUtils.getIntPreference(mActivity, "int", 0) + "");
                                toast(BasisSPUtils.getIntPreference(mActivity, "int", "int", 0) + "第二次");
                                break;case SPData.LONG:
                                BasisSPUtils.setLongPreferences(mActivity, "LONG", 0 + times);
                                BasisSPUtils.setLongPreferences(mActivity, "LONG", "LONG", 0 + times);
                                toast(BasisSPUtils.getLongPreferences(mActivity, "LONG", 0) + "");
                                toast(BasisSPUtils.getLongPreferences(mActivity, "LONG", "LONG", 0) + "第二次");
                                break;case SPData.BOOLEAN:
                                BasisSPUtils.setBooleanPreferences(mActivity, "BOOLEAN", true);
                                BasisSPUtils.setBooleanPreferences(mActivity, "BOOLEAN", "BOOLEAN", false);
                                toast(BasisSPUtils.getBooleanPreferences(mActivity, "BOOLEAN", false) + "");
                                toast(BasisSPUtils.getBooleanPreferences(mActivity, "BOOLEAN", "BOOLEAN", true) + "第二次");
                                break;
                        }
                        times++;
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
