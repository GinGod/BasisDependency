package com.gingold.basisdependency.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.Urls;
import com.gingold.basislibrary.glide.BasisCallBack;
import com.gingold.basislibrary.glide.BasisGlideUtils;
import com.gingold.basislibrary.glide.GlideApp;

import java.io.File;
import java.util.HashMap;

/**
 *
 */

public class GlideActivity extends BaseActivity {
    private TextView tv_glide_cacheSize;
    private TextView tv_glide_clearCache;
    private TextView tv_glide_load;
    private TextView tv_glide_download;
    private ImageView iv_glide_pic;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_glide);
        initTitle("Glide", "");
    }

    @Override
    public void initView() {
        tv_glide_cacheSize = findTextView(R.id.tv_glide_cacheSize);
        tv_glide_clearCache = findTextView(R.id.tv_glide_clearCache);
        tv_glide_load = findTextView(R.id.tv_glide_load);
        tv_glide_download = findTextView(R.id.tv_glide_download);
        iv_glide_pic = findImageView(R.id.iv_glide_pic);
    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {
        tv_glide_cacheSize.setText(BasisGlideUtils.getCacheSize(mActivity));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_glide_clearCache:
                BasisGlideUtils.clearAllCacheMemory(mActivity);
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_glide_cacheSize.setText(BasisGlideUtils.getCacheSize(mActivity));
                    }
                }, 1000);
                toast("清除缓存中...");
                break;
            case R.id.tv_glide_load:
//                Glide.with(mActivity).load(R.drawable.arrow_down).into(iv_glide_pic);
//                GlideApp.with(mActivity).load(R.drawable.arrow_down).into(iv_glide_pic);
                RequestOptions options = new RequestOptions().bitmapTransform(new CircleCrop());
                GlideApp.with(mActivity).load(Urls.picUrl1)
                        .apply(options)
                        .into(iv_glide_pic);
//                Glide.with(mActivity).load(Urls.picUrl1).transition(new DrawableTransitionOptions().crossFade(2000)).thumbnail(0.5f).into(iv_glide_pic);
//                BasisGlideUtils.load(iv_glide_pic, R.drawable.arrow_down, iv_glide_pic);
//                BasisGlideUtils.load(iv_glide_pic, Urls.picUrl1, iv_glide_pic, 2000, R.drawable.arrow_down, true);
//                BasisGlideUtils.loadSpecial(iv_glide_pic, Urls.picUrl1, iv_glide_pic, mActivity, BasisGlideUtils.CROPCIRCLE);
//                select();

                toast("加载图片中...");
                break;
            case R.id.tv_glide_download:
                BasisGlideUtils.downloadPic(mActivity, Urls.picUrl1, "测试", new BasisCallBack() {
                    @Override
                    public void onSuccess(Bitmap bitmap, File file, String fileName) {
                        toast(fileName);
//                        iv_glide_pic.setImageBitmap(bitmap);
                        BasisGlideUtils.load(mActivity, fileName, iv_glide_pic);
                    }

                    @Override
                    public void onFailure() {
                        toast("下载失败");
                    }
                });
                break;
        }
    }

    private void select() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", R.drawable.arrow_down);
        map.put("2", R.drawable.ic_launcher);
        BasisGlideUtils.loadWithError(mActivity, Urls.picUrl1, iv_glide_pic, "1", map);
    }
}
