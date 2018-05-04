package com.gingold.basisdependency.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisImmerseUtils;

/**
 * 沉浸式状态栏
 */

public class ImmerseActivity extends BaseActivity {
    private LinearLayout ll_immerse_title;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_immerse);
        //要实现沉浸式的布局, 一般是自定义的标题栏
        ll_immerse_title = getViewNoClickable(R.id.ll_immerse_title);
        //设置沉浸式状态栏效果
        BasisImmerseUtils.setImmerseLayout(mActivity, ll_immerse_title);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                toast("取消沉浸式状态!");
                BasisImmerseUtils.clearImmerseLayout(mActivity, ll_immerse_title);
            }
        }, 252 * 10);
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
