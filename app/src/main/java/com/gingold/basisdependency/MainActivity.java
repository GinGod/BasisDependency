package com.gingold.basisdependency;

import android.Manifest;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.activity.AnimationActivity;
import com.gingold.basisdependency.activity.DBActivity;
import com.gingold.basisdependency.activity.DateTimePickerActivity;
import com.gingold.basisdependency.activity.DialogActivity;
import com.gingold.basisdependency.activity.ExceptionInfoActivity;
import com.gingold.basisdependency.activity.Html5Activity;
import com.gingold.basisdependency.activity.LvRefreshActivity;
import com.gingold.basisdependency.activity.MultiLvActivity;
import com.gingold.basisdependency.activity.PhotoActivity;
import com.gingold.basisdependency.activity.SPActivity;
import com.gingold.basisdependency.activity.TestActivity;
import com.gingold.basisdependency.data.MainData;
import com.gingold.basisdependency.data.TestData;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvAdapter;
import com.gingold.basislibrary.adapter.lvgv.BasisLvGvViewHolder;
import com.gingold.basislibrary.utils.BasisLogUtils;
import com.orhanobut.logger.Logger;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity {

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_main);
        initTitle("Basis测试", "");
        startService(new Intent(mActivity, TestService.class));
    }

    @Override
    public void initView() {
        initPermission();
    }

    @Override
    public void initListener() {

    }

    //申请必要的权限
    private void initPermission() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean isPer = EasyPermissions.hasPermissions(this, perms);
        if (isPer) {

        } else {
//            EasyPermissions.requestPermissions(this,
//                    "Need permissions for camera & microphone & read_external_storage", 0, perms);

            EasyPermissions.requestPermissions(this,
                    "需要相机和麦克风的权限以及外部存储器的读取权限", 0, perms);
        }
        Logger.e(14 % 7 + " " + 15 % 7 + " " + 16 % 7 + " " + 17 % 7 + " " + 18 % 7 + " " + 19 % 7 + " " + 20 % 7);
    }

    @Override
    public void initData() {
        findListView(R.id.lv_main).setAdapter(new BasisLvGvAdapter<MainData.MainBean>(mActivity, R.layout.item_textview, TestData.mainList) {
            @Override
            protected void initView(final BasisLvGvViewHolder basisViewHolder, final MainData.MainBean data, int position) {
                basisViewHolder.setTvTextListener(R.id.tv_item_main, data.des, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int flag = Paint.DITHER_FLAG;
                        toast(data.des);
                        switch (data.des) {
                            case MainData.DATEPICKER:
                                startActivity(DateTimePickerActivity.class);
                                break;
                            case MainData.ANIMATION:
                                startActivity(AnimationActivity.class);
                                break;
                            case MainData.HTML5:
                                startActivity(Html5Activity.class);
                                break;
                            case MainData.TEST:
                                startActivity(TestActivity.class);
                                break;
                            case MainData.SP:
                                startActivity(SPActivity.class);
                                break;
                            case MainData.LOG:
                                BasisLogUtils.e(TAG, getStringById(R.string.long_string));
                                BasisLogUtils.e(getStringById(R.string.long_string));
                                break;
                            case MainData.LVADAPTER:
                                startActivity(MultiLvActivity.class);
                                break;
                            case MainData.LVREFRESH:
                                startActivity(LvRefreshActivity.class);
                                break;
                            case MainData.PHOTO:
                                startActivity(PhotoActivity.class);
                                break;
                            case MainData.DB:
                                startActivity(DBActivity.class);
                                break;
                            case MainData.DIALOG:
                                startActivity(DialogActivity.class);
                                break;
                            case MainData.EXCEPTIONINFO:
                                startActivity(ExceptionInfoActivity.class);
                                break;
                        }

                        ((TextView) basisViewHolder.getView(R.id.tv_item_main)).getPaint().setFlags(flag);
                    }
                });

                int flag = Paint.UNDERLINE_TEXT_FLAG;
                switch (data.des) {
                    case MainData.TEST:
                        flag = Paint.ANTI_ALIAS_FLAG;
                        break;
                    case MainData.SP:
                        flag = Paint.FILTER_BITMAP_FLAG;
                        break;
                    case MainData.LOG:
                        flag = Paint.LINEAR_TEXT_FLAG;
                        break;
                    case MainData.LVADAPTER:
                        flag = Paint.SUBPIXEL_TEXT_FLAG;
                        break;
                    case MainData.LVREFRESH:
                        flag = Paint.DITHER_FLAG;
                        break;
                    case MainData.PHOTO:
                        flag = Paint.HINTING_OFF;
                        break;
                    case MainData.DB:
                        flag = Paint.HINTING_ON;
                        break;
                }

                ((TextView) basisViewHolder.getView(R.id.tv_item_main)).getPaint().setFlags(flag);
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
