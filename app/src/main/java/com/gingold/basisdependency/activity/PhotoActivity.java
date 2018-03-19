package com.gingold.basisdependency.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisglidelibrary.BasisGlideUtils;
import com.gingold.basislibrary.utils.BasisPhotoUtils;

/**
 *
 */

public class PhotoActivity extends BaseActivity {
    private TextView tv_photo_takePic;
    private TextView tv_photo_pickPic;
    private ImageView iv_photo_show;

    private final int TAKEPHOTO = 52, PICKPIC = 252;
    public String mTakePhotoPath;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_photo);
    }

    @Override
    public void initView() {
        tv_photo_takePic = findTextView(R.id.tv_photo_takePic);
        tv_photo_pickPic = findTextView(R.id.tv_photo_pickPic);
        iv_photo_show = findImageView(R.id.iv_photo_show);
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_photo_takePic:
                mTakePhotoPath = BasisPhotoUtils.takePhoto(mActivity, TAKEPHOTO);
                break;
            case R.id.tv_photo_pickPic:
                BasisPhotoUtils.pickedPhoto(mActivity, PICKPIC);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    BasisGlideUtils.load(mActivity, mTakePhotoPath, iv_photo_show);
                }
                break;
            case PICKPIC:
                if (resultCode == RESULT_OK) {
                    BasisGlideUtils.load(mActivity, BasisPhotoUtils.pickedPhotoResult(mActivity, data), iv_photo_show);
                }
                break;
        }
    }
}
