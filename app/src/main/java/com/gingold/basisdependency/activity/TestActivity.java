package com.gingold.basisdependency.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.gingold.basisdependency.bean.TestBean;
import com.gingold.basislibrary.utils.BasisCommonUtils;
import com.gingold.basislibrary.utils.BasisDeviceUtils;
import com.gingold.basislibrary.utils.BasisFileUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.BasisTimesUtils;
import com.gingold.basislibrary.utils.BasisVersionUtils;
import com.gingold.basislibrary.utils.dialog.BasisDSClickListener;
import com.gingold.basislibrary.utils.dialog.BasisDialogListenrer;
import com.gingold.basislibrary.utils.dialog.BasisDialogUtils;
import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;
import com.gingold.basislibrary.utils.dialog.BasisProgressDialogUtils;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;

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
    private TextView et_test_test;
    public String before = "";


    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);//添加ButterKnife
        initTitle("test", "");
    }

    @Override
    public void initView() {
        et_test_test = getViewNoClickable(R.id.et_test_test);
    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_test_installapk, R.id.tv_test_gson, R.id.tv_test_test);
        et_test_test.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                }
                return false;
            }
        });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test_installapk:
                final BasisPBLoadingUtils basisPBLoadingUtils = BasisPBLoadingUtils.build(mActivity).setMessage("正在复制 0%");
                basisPBLoadingUtils.show();
                final File apkFile = new File(BasisFileUtils.mkdir("download"), "test.apk");
                BasisFileUtils.copyAssetsFile(mActivity, "test.apk", apkFile, new BasisFileUtils.onCopyFileCallBack() {
                    @Override
                    public void onSuccess() {
                        BasisPBLoadingUtils.dismiss();
                        BasisCommonUtils.installApk(mActivity, apkFile);
                    }

                    @Override
                    public void onFailure(String errorMessage) {

                    }

                    @Override
                    public void onProgress(long totalSize, long currentSize, long progress) {
                        BasisLogUtils.e(totalSize + " " + currentSize + " " + progress);
                        basisPBLoadingUtils.setMessage("正在复制 " + progress + "%");
                    }
                });
                break;
            case R.id.tv_test_gson:
                String test = "[{\"distributioncode\": \"qlkd\",\"goodsname\": \"你\", \"goodsnum\": \"6\"}]";
                ArrayList<TestBean> list = gson.fromJson(test, new TypeToken<ArrayList<TestBean>>() {
                }.getType());
                break;
            case R.id.tv_test_test:
                BasisLogUtils.e(getMobileInfo() + "");
//                testFor();//增强for循环测试, 发现操作对象不能为空
//                java.io.InputStream
//                java.io.OutputStream
//                java.io.Reader
//                java.io.Writer
                break;
        }
    }

    /**
     * 增强for循环测试, 发现操作对象不能为空
     */
    private void testFor() {
        ArrayList<String> list = null;
        for (String str : list) {
            BasisLogUtils.e(str);
        }
    }

    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        //通过反射获取系统的硬件信息
        try {

            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                //暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @OnClick({R.id.iv_arrow, R.id.tv_test_down, R.id.tv_test_up, R.id.tv_test_clear, R.id.tv_test_time, R.id.et_test_hide})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_test_down:
                BasisProgressDialogUtils.build(mActivity).setTitle(R.drawable.ic_launcher, "测试标题").setMessage("测试message")
                        .setListener(new BasisDialogListenrer() {
                            @Override
                            public void onDismiss() {
                                toast("dismiss");
                            }
                        }).show();
//                test();
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
                BasisPBLoadingUtils.build(mActivity)/*.setCancelable(true, true)*/
                        .setListener(new BasisDialogListenrer() {
                            @Override
                            public void onDismiss() {
                                toast("diss");
                            }

                            @Override
                            public void onCancle() {
                                super.onCancle();
                                toast("cancle");
                            }
                        }).show();
                mIvArrow.startAnimation(mRotateUpAnim);
                toast("up");
                break;
            case R.id.tv_test_clear:
                BasisDialogUtils.build(mActivity, "标题", "你有信息未读, 请立即查看!", new BasisDSClickListener() {
                    @Override
                    public void onConfirm() {
                        toast("hkdhkj");
                    }
                }).setCancelable(false, false).show();
                mIvArrow.clearAnimation();
                toast("clear");
                BasisLogUtils.e(BasisVersionUtils.getVersionName(mActivity) + " - " + BasisVersionUtils.getVersionCode(mActivity)
                        + " - " + BasisVersionUtils.getDeviceInfo());
                break;
            case R.id.tv_test_time:
                File file = new File(BasisFileUtils.mkdir("testRecord"), BasisTimesUtils.getDeviceTimeOfYM() + ".txt");
                for (int i = 0; i < 10; i++) {
                    BasisFileUtils.writeRecord(file, "~!@#$%^&*()_+-" + i, true);
                }
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
                ShortcutBadger.applyCount(mActivity, 3); //for 1.1.4+
                break;
            case R.id.et_test_hide:
                BasisLogUtils.e(BasisDeviceUtils.getBrand() + " - " + BasisDeviceUtils.getMac() + " - " +
                        BasisDeviceUtils.getModel() + " - " + BasisDeviceUtils.getSerialNumber() + " - " +
                        BasisDeviceUtils.getIPAddress(mActivity));
                toast("点击");
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        toast("隐藏");
//                        hideInputMethod();
                    }
                }, 3000);
                break;
        }
    }

    private void test() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.dialog_simple, null);
        AlertDialog dialog = new AlertDialog.Builder(mActivity).setView(view).create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
//        new BasisOkHttpBuilder();//BasisOkHttpBuilder 不允许单独创建对象使用
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        toast("keyCode: " + keyCode);
        return super.onKeyDown(keyCode, event);
    }
}
