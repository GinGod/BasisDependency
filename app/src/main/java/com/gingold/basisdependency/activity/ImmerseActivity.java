package com.gingold.basisdependency.activity;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisImmerseUtils;

/**
 * 沉浸式状态栏
 */

public class ImmerseActivity extends BaseActivity {
    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_immerse);
        BasisImmerseUtils.setImmerseLayout(mActivity, getViewNoClickable(R.id.ll_immerse_title));
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_immerse_fragment);
    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_immerse_fragment:
                startActivity(ImmerseFragmentActivity.class);
                break;
        }
    }

}
