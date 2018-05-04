package com.gingold.basisdependency.activity;

import android.view.View;
import android.widget.ImageView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisAnimUtils;

/**
 * 动画测试
 */

public class AnimationActivity extends BaseActivity {
    private ImageView iv_animation;
    private ImageView iv_animation1;
    private ImageView iv_animation2;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_animation);
        initTitle("动画", "");
    }

    @Override
    public void initView() {
        iv_animation = getViewNoClickable(R.id.iv_animation);
        iv_animation1 = getViewNoClickable(R.id.iv_animation1);
        iv_animation2 = getViewNoClickable(R.id.iv_animation2);
    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_animation_alpha, R.id.tv_animation_translate, R.id.tv_animation_scale, R.id.tv_animation_rotate, R.id.tv_animation_set, R.id.tv_animation_rotate_contitue
                , R.id.tv_animation_alpha1, R.id.tv_animation_translate1, R.id.tv_animation_scale1, R.id.tv_animation_rotate1, R.id.tv_animation_set1, R.id.tv_animation_rotate_contitue1
                , R.id.tv_animation_alpha2, R.id.tv_animation_translate2, R.id.tv_animation_scale2, R.id.tv_animation_rotate2, R.id.tv_animation_set2, R.id.tv_animation_rotate_contitue2);
    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_animation_alpha://透明动画(view动画)
                BasisAnimUtils.alpha(iv_animation);
                break;
            case R.id.tv_animation_translate://平移动画(view动画)
                BasisAnimUtils.trans(iv_animation);
                break;
            case R.id.tv_animation_scale://缩放动画(view动画)
                BasisAnimUtils.scale(iv_animation);
                break;
            case R.id.tv_animation_rotate://旋转动画(view动画)
                BasisAnimUtils.rotate(iv_animation);
                break;
            case R.id.tv_animation_set://集合动画(view动画)
                BasisAnimUtils.set(iv_animation);
                break;
            case R.id.tv_animation_rotate_contitue://连续旋转动画(view动画)
                BasisAnimUtils.rotateContinue(iv_animation);
                break;

            case R.id.tv_animation_alpha1://透明动画(view资源动画)
                BasisAnimUtils.alpha(mActivity, iv_animation1);
                break;
            case R.id.tv_animation_translate1://平移动画(view资源动画)
                BasisAnimUtils.trans(mActivity, iv_animation1);
                break;
            case R.id.tv_animation_scale1://缩放动画(view资源动画)
                BasisAnimUtils.scale(mActivity, iv_animation1);
                break;
            case R.id.tv_animation_rotate1://旋转动画(view资源动画)
                BasisAnimUtils.rotate(mActivity, iv_animation1);
                break;
            case R.id.tv_animation_set1://集合动画(view资源动画)
                BasisAnimUtils.set(mActivity, iv_animation1);
                break;
            case R.id.tv_animation_rotate_contitue1://连续旋转动画(view资源动画)
                BasisAnimUtils.rotateContinue(mActivity, iv_animation1);
                break;

            case R.id.tv_animation_alpha2://透明动画(属性动画)
                BasisAnimUtils.alphaOfObject(iv_animation2);
                break;
            case R.id.tv_animation_translate2://平移动画(属性动画)
                BasisAnimUtils.transOfObject(iv_animation2);
                break;
            case R.id.tv_animation_scale2://缩放动画(属性动画)
                BasisAnimUtils.scaleOfObject(iv_animation2);
                break;
            case R.id.tv_animation_rotate2://旋转动画(属性动画)
                BasisAnimUtils.rotateOfObject(iv_animation2);
                break;
            case R.id.tv_animation_set2://集合动画(属性动画)
                BasisAnimUtils.setOfObject(iv_animation2);
                break;
            case R.id.tv_animation_rotate_contitue2://连续旋转动画(属性动画)
                BasisAnimUtils.rotateContinue(iv_animation2);
                break;
        }
    }


}
