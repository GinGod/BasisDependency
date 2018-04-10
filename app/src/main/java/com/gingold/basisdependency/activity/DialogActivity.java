package com.gingold.basisdependency.activity;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.Base.BasisBaseViewUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.gingold.basislibrary.utils.dialog.BasisDSClickListener;
import com.gingold.basislibrary.utils.dialog.BasisDialogListenrer;
import com.gingold.basislibrary.utils.dialog.BasisDialogUtils;
import com.gingold.basislibrary.utils.dialog.BasisOnSelfDialogListener;
import com.gingold.basislibrary.utils.dialog.BasisOnSelfViewListener;
import com.gingold.basislibrary.utils.dialog.BasisPBLoadingUtils;
import com.gingold.basislibrary.utils.dialog.BasisProgressDialogUtils;
import com.gingold.basislibrary.utils.dialog.BasisSelfDialogUtils;
import com.gingold.basislibrary.utils.dialog.BasisSelfPopWinUtils;

/**
 * Dialog测试类
 *
 * @see BasisDialogUtils 使用 {@link #showDialog1()}
 * @see BasisPBLoadingUtils 使用 {@link #showDialog2()}
 * @see BasisProgressDialogUtils 使用 {@link #showDialog3()}
 * @see BasisSelfDialogUtils 使用 {@link #showDialog4()}
 * @see BasisSelfDialogUtils 使用(自定义逻辑) {@link #showDialog5()}
 * @see BasisSelfPopWinUtils 使用(EditText 焦点获取测试) {@link #showPopWin1()}
 * @see BasisSelfPopWinUtils 使用 {@link #showPopWin2()}
 * @see BasisSelfPopWinUtils 使用 {@link #showPopWin3()}
 */

public class DialogActivity extends BaseActivity {
    private Dialog mDialog;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_dialog);
        initTitle("Dialog", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_dialog_1, R.id.tv_dialog_2, R.id.tv_dialog_3, R.id.tv_dialog_4,
                R.id.tv_dialog_5, R.id.tv_dialog_6, R.id.tv_dialog_7, R.id.tv_dialog_8);
    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_1:
                showDialog1();
                break;
            case R.id.tv_dialog_2:
                showDialog2();
                break;
            case R.id.tv_dialog_3:
                showDialog3();
                break;
            case R.id.tv_dialog_4:
                showDialog4();
                break;
            case R.id.tv_dialog_5:
                showDialog5();
                break;
            case R.id.tv_dialog_6:
                showPopWin1();
                break;
            case R.id.tv_dialog_7:
                showPopWin2();
                break;
            case R.id.tv_dialog_8:
                showPopWin3();
                break;
        }
    }

    /**
     * BasisSelfPopWinUtils 使用
     */
    private void showPopWin3() {
        BasisSelfPopWinUtils.build(mActivity, com.gingold.basislibrary.R.layout.pb_loading, new BasisOnSelfViewListener() {
            @Override
            public void onSelfViewListener(View dialog, BasisBaseViewUtils basisBaseViewUtils) {

            }
        }).showAtLocation(LayoutInflater.from(mActivity).inflate(R.layout.activity_dialog, null), Gravity.LEFT, 0, 0);
    }

    /**
     * BasisSelfPopWinUtils 使用
     */
    private void showPopWin2() {
        BasisSelfPopWinUtils.build(mActivity, com.gingold.basislibrary.R.layout.pb_loading, new BasisOnSelfViewListener() {
            @Override
            public void onSelfViewListener(View dialog, BasisBaseViewUtils basisBaseViewUtils) {

            }
        }).showAtLocation(LayoutInflater.from(mActivity).inflate(R.layout.activity_dialog, null), Gravity.BOTTOM, 0, 0);
    }

    /**
     * BasisSelfPopWinUtils 使用(EditText 焦点获取测试)
     */
    private void showPopWin1() {
        BasisSelfPopWinUtils.build(mActivity, R.layout.popwin_edittext, new BasisOnSelfViewListener() {
            @Override
            public void onSelfViewListener(View dialog, BasisBaseViewUtils basisBaseViewUtils) {
                basisBaseViewUtils.setTVText("测试1", R.id.tv_popwin_1).setOnClickListener(R.id.tv_popwin_1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("测试1");
                    }
                });
            }
        }).setOutsideTouchEnable().showAsDropDown(getViewNoClickable(R.id.tv_dialog_3), -5, 5);
    }

    /**
     * BasisSelfDialogUtils 使用(自定义逻辑)
     */
    private void showDialog5() {
        BasisSelfDialogUtils.build(mActivity, com.gingold.basislibrary.R.layout.dialog_simple, new BasisOnSelfDialogListener() {
            @Override
            public void onSelfViewListener(Dialog dialog, BasisBaseViewUtils basisBaseViewUtils) {
                basisBaseViewUtils.setOnClickListener(R.id.tv_dialog_simple_confirm, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toast("确定");
                    }
                }).setOnLongClickListener(R.id.tv_dialog_simple_cancel, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        toast("取消");
                        return false;
                    }
                }).setTVText("测试", R.id.tv_dialog_simple_message)
                        .setTVTextColor(R.color.holo_red_dark, R.id.tv_dialog_simple_confirm);
            }
        }).show();
    }

    /**
     * BasisSelfDialogUtils 使用
     */
    private void showDialog4() {
        BasisSelfDialogUtils.build(mActivity, com.gingold.basislibrary.R.layout.pb_loading, new BasisOnSelfDialogListener() {
            @Override
            public void onSelfViewListener(Dialog dialog, BasisBaseViewUtils basisBaseViewUtils) {

            }
        }).show();
    }

    /**
     * BasisProgressDialogUtils 使用
     */
    private void showDialog3() {
        BasisProgressDialogUtils.build(mActivity).setTitle(R.drawable.ic_launcher, "title").setMessage("message").show();
        BasisProgressDialogUtils.build(mActivity).setTitle(R.drawable.ic_launcher, "title").setMessage("message").show();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                BasisProgressDialogUtils.dismiss();
            }
        }, 252 * 10);
    }

    /**
     * BasisPBLoadingUtils 使用
     */
    private void showDialog2() {
        BasisPBLoadingUtils.build(mActivity).setMessage("BasisPBLoadingUtils").setListener(new BasisDialogListenrer() {
            @Override
            public void onDismiss() {
                BasisLogUtils.e("onDismiss");
            }

            @Override
            public void onCancle() {
                super.onCancle();
                BasisLogUtils.e("onCancle");
            }
        }).show();

        BasisPBLoadingUtils.build(mActivity).show();

        postDelayed(new Runnable() {
            @Override
            public void run() {
                BasisPBLoadingUtils.dismiss();
            }
        }, 252 * 10);
    }

    /**
     * BasisDialogUtils 使用
     */
    private void showDialog1() {
        //连续创建两个BasisDialogUtils, dismiss方法错乱失效测试
        BasisDialogUtils.build(mActivity, "title", "message", null).setListener(new BasisDialogListenrer() {
            @Override
            public void onDismiss() {
                BasisLogUtils.e("onDismiss1");
            }
        }).show();
        //后续dialog需要手动调用dismiss方法
        mDialog = BasisDialogUtils.build(mActivity, "title", "message", new BasisDSClickListener() {
            @Override
            public void onConfirm() {
                BasisLogUtils.e("onConfirm2");
                mDialog.dismiss();
            }

            @Override
            public void onCancel() {
                super.onCancel();
                BasisLogUtils.e("onCancel2");
                mDialog.dismiss();
            }
        }).setListener(new BasisDialogListenrer() {
            @Override
            public void onDismiss() {
                BasisLogUtils.e("onDismiss2");
            }

            @Override
            public void onCancle() {
                super.onCancle();
                BasisLogUtils.e("onCancle2");
            }
        }).setCancelable(true, false).show();
    }
}
