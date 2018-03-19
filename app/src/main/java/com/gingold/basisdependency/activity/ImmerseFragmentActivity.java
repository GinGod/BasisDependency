package com.gingold.basisdependency.activity;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisImmerseUtils;

/**
 * 实现沉浸式状态栏Fragment
 */

public class ImmerseFragmentActivity extends BaseActivity {
    private ViewPager vp_immersefragment;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_immersefragment);
//        BasisImmerseUtils.hideStatusBar(mActivity);
//        BasisImmerseUtils.hideNavigationBar(mActivity);
//        BasisImmerseUtils.setTransparentWindowBar(mActivity);
    }

    @Override
    public void initView() {
        vp_immersefragment = getViewNoClickable(R.id.vp_immersefragment);
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        setVPAdapter();
    }

    private void setVPAdapter() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BasisImmerseUtils.setOnWindowFocusChanged(mActivity, hasFocus);
    }


}
