package com.gingold.basisdependency.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */

public class TestActivity extends BaseActivity {
    public RotateAnimation mRotateUpAnim;
    public RotateAnimation mRotateDownAnim;
    public long ROTATE_ANIM_DURATION = 252;
    @Bind(R.id.iv_arrow)
    ImageView mIvArrow;
    @Bind(R.id.tv_test_down)
    TextView mTvTestDown;
    @Bind(R.id.tv_test_up)
    TextView mTvTestUp;
    @Bind(R.id.tv_test_clear)
    TextView mTvTestClear;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);//添加ButterKnife
        initTitle("test", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        //箭头旋转向上动画(默认向下)
        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);

        //箭头旋转向下状态
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mTvTestClear.setText("清除动画1");
    }

    @Override
    public void onClick(View view) {

    }



    @OnClick({R.id.tv_test_down, R.id.tv_test_up, R.id.tv_test_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_down:
                mIvArrow.startAnimation(mRotateDownAnim);
                toast("down");
                break;
            case R.id.tv_test_up:
                mIvArrow.startAnimation(mRotateUpAnim);
                toast("up");
                break;
            case R.id.tv_test_clear:
                mIvArrow.clearAnimation();
                toast("clear");
                break;
        }
    }
}
