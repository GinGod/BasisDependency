package com.gingold.basisdependency.activity;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.MainActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisDisplayUtils;

/**
 * 启动页
 */

public class SplashActivity extends BaseActivity {
    @Override
    public void setupViewLayout() {
        BasisDisplayUtils.setFullScreen(mActivity);//全屏
        setContentView(R.layout.activity_splash);
       mBasisHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityAndFinish(MainActivity.class);
            }
        }, 2520);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
