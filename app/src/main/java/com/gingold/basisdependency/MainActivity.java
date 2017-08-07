package com.gingold.basisdependency;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.activity.MultiLvActivity;
import com.gingold.basisdependency.activity.SPActivity;
import com.gingold.basisdependency.data.MainData;
import com.gingold.basisdependency.data.TestData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisViewHolder;
import com.gingold.basislibrary.utils.BasisLogUtils;

public class MainActivity extends BaseActivity {

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_main);
        initTitle("Basis测试", "");
    }

    @Override
    public void initView() {
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        findListView(R.id.lv_main).setAdapter(new BasisLvGvAdapter<MainData.MainBean>(mActivity, R.layout.item_textview, TestData.mainList) {
            @Override
            protected void initView(BasisViewHolder basisViewHolder, final MainData.MainBean data, int position) {
                basisViewHolder.setTvTextListener(R.id.tv_item_main, data.des, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast(data.des);
                        switch (data.des) {
                            case MainData.SP:
                                startActivity(SPActivity.class);
                                break;
                            case MainData.LOG:
                                BasisLogUtils.e(TAG, getStringById(R.string.long_string));
                                BasisLogUtils.e(getStringById(R.string.long_string));
                                break;
                            case MainData.LVADAPTER:
                                startActivity(MultiLvActivity.class);
                                break;
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
