package com.gingold.basisdependency.activity;

import android.view.View;
import android.widget.ImageView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;

/**
 * 加载大图, 测试Glide缓存
 */
public class Glide2Activity extends BaseActivity {
    private ImageView iv_glide2_pic;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_glide2);
    }

    @Override
    public void initView() {
        iv_glide2_pic = getViewNoClickable(R.id.iv_glide2_pic);
//        Glide.with(mActivity).load("http://hr.zqlwl.com/upload/ehr/apps/pic/0005.jpg").error(R.drawable.splash).into(iv_glide2_pic);
//        Picasso.with(mActivity).load("http://hr.zqlwl.com/upload/ehr/apps/pic/0005.jpg").into(iv_glide2_pic);
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {

    }
}
