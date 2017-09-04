package com.gingold.basisdependency;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.activity.GlideActivity;
import com.gingold.basisdependency.activity.LvRefreshActivity;
import com.gingold.basisdependency.activity.MultiLvActivity;
import com.gingold.basisdependency.activity.MultiRvActivity;
import com.gingold.basisdependency.activity.OkHttpActivity;
import com.gingold.basisdependency.activity.OkHttpPicActivity;
import com.gingold.basisdependency.activity.RvRefreshActivity;
import com.gingold.basisdependency.activity.SPActivity;
import com.gingold.basisdependency.activity.TestActivity;
import com.gingold.basisdependency.data.MainData;
import com.gingold.basisdependency.data.TestData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;
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
            protected void initView(BasisLvGvViewHolder basisViewHolder, final MainData.MainBean data, int position) {
                basisViewHolder.setTvTextListener(R.id.tv_item_main, data.des, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast(data.des);
                        switch (data.des) {
                            case MainData.TEST:
                                startActivity(TestActivity.class);
                                break;
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
                            case MainData.RVADAPTER:
                                startActivity(MultiRvActivity.class);
                                break;
                            case MainData.RVREFRESH:
                                startActivity(RvRefreshActivity.class);
                                break;
                            case MainData.LVREFRESH:
                                startActivity(LvRefreshActivity.class);
                                break;
                            case MainData.OKHTTP:
                                startActivity(OkHttpActivity.class);
                                break;
                            case MainData.OKHTTPPIC:
                                startActivity(OkHttpPicActivity.class);
                                break;
                            case MainData.GLIDE:
                                startActivity(GlideActivity.class);
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
