package com.gingold.basisdependency.activity;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.MyApplication;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisClickListener;
import com.gingold.basislibrary.utils.BasisDialogUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;
import com.gingold.basislibrary.utils.BasisVersionUtils;

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
    @Bind(R.id.tv_test_time)
    TextView mTvTestTime;
    @Bind(R.id.et_test_hide)
    EditText mEtTestHide;


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

    @OnClick({R.id.iv_arrow, R.id.tv_test_down, R.id.tv_test_up, R.id.tv_test_clear, R.id.tv_test_time, R.id.et_test_hide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_down:
                mIvArrow.startAnimation(mRotateDownAnim);
//                toast("down");
                Context context = MyApplication.getContext();
                if (context == null) {
                    toast("null");
                } else {
                    Toast.makeText(app, "context", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_test_up:
                mIvArrow.startAnimation(mRotateUpAnim);
                toast("up");
                break;
            case R.id.tv_test_clear:
//                BasisProgressDialogUtils.build(mActivity).show();
                BasisDialogUtils.show(mActivity, "标题", "message", new BasisClickListener() {
                    @Override
                    public void onConfirm() {
                        toast("hkdhkj");
                    }
                });
                mIvArrow.clearAnimation();
                toast("clear");
                BasisLogUtils.e(BasisVersionUtils.getVersionName(mActivity) + " - " + BasisVersionUtils.getVersionCode(mActivity)
                        + " - " + BasisVersionUtils.getDeviceInfo());
                break;
            case R.id.tv_test_time:
                BasisLogUtils.e(BasisTimesUtils.getDeviceTimeOfSSS());
                BasisLogUtils.e(BasisTimesUtils.getDeviceTime());
                BasisLogUtils.e(BasisTimesUtils.getDeviceTimeOfYMD());

                BasisLogUtils.e(BasisTimesUtils.getLongTimeOfSSS(BasisTimesUtils.getDeviceTimeOfSSS()) + "");
                BasisLogUtils.e(BasisTimesUtils.getLongTime(BasisTimesUtils.getDeviceTime()) + "");
                BasisLogUtils.e(BasisTimesUtils.getLongTimeOfYMD(BasisTimesUtils.getDeviceTimeOfYMD()) + "");

                BasisLogUtils.e(BasisTimesUtils.getStringTimeOfSSS(BasisTimesUtils.getLongTimeOfSSS(BasisTimesUtils.getDeviceTimeOfSSS())));
                BasisLogUtils.e(BasisTimesUtils.getStringTime(BasisTimesUtils.getLongTime(BasisTimesUtils.getDeviceTime())));
                BasisLogUtils.e(BasisTimesUtils.getStringTimeOfYMD(BasisTimesUtils.getLongTimeOfYMD(BasisTimesUtils.getDeviceTimeOfYMD())));

                try {
//                    BasisLogUtils.e(BasisTimesUtils.getStringTime(1504682829586L));
//                    BasisLogUtils.e(BasisTimesUtils.getStringTime(150468282958L));
//                    BasisLogUtils.e(BasisTimesUtils.getStringTime(15046828295L));
//                    BasisLogUtils.e(BasisTimesUtils.getStringTime(1504682829L));
//                    BasisLogUtils.e(BasisTimesUtils.getStringTime(150468282L));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.et_test_hide:
                toast("点击");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast("隐藏");
                        hideInputMethod();
                    }
                }, 3000);
                break;
        }
    }
}
