package com.gingold.basisdependency.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.Urls;

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
    private ImageView iv_glide_pic1;
    private ImageView iv_glide_pic_cache;

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
        iv_glide_pic1 = findImageView(R.id.iv_glide_pic1);
        iv_glide_pic_cache = findImageView(R.id.iv_glide_pic_cache);
    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_glide_load2, R.id.tv_glide_cache_samll, R.id.tv_glide_cache_big);
    }

    @Override
    public void logicDispose() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_glide_clearCache:
                toast("清除缓存中...");
                break;
            case R.id.tv_glide_load:
//                Glide.with(mActivity).load(R.drawable.lvgvrv_arrow_down).into(iv_glide_pic);
//                GlideApp.with(mActivity).load(R.drawable.lvgvrv_arrow_down).into(iv_glide_pic);
//                RequestOptions options = new RequestOptions().bitmapTransform(new CircleCrop());
//                GlideApp.with(mActivity).load(Urls.picUrl1)
//                        .apply(options)
//                        .into(iv_glide_pic);
//                Glide.with(mActivity).load(Urls.picUrl1).transition(new DrawableTransitionOptions().crossFade(2000)).thumbnail(0.5f).into(iv_glide_pic);
//                BasisGlideUtils.load(iv_glide_pic, R.drawable.lvgvrv_arrow_down, iv_glide_pic);
//                BasisGlideUtils.load(iv_glide_pic, Urls.picUrl1, iv_glide_pic, 2000, R.drawable.lvgvrv_arrow_down, true);
//                BasisGlideUtils.loadSpecial(iv_glide_pic, Urls.picUrl1, iv_glide_pic, mActivity, BasisGlideUtils.CROPCIRCLE);
//                select();

                toast("加载图片中...");
                break;
            case R.id.tv_glide_load2:
                break;
            case R.id.tv_glide_download:


//                BasisOkHttpUtils.postString().url("http://hrtest.zqlwl.com/maintain/getPath.do").build().execute(new BasisCallback() {
//                    @Override
//                    public void onSuccess(Call call, Response response, String result) {
//
//                    }
//
//                    @Override
//                    public void onFailure(String url, String content, Call call, Exception e, String message) {
//
//                    }
//
//                    @Override
//                    public void onException(String url, String content, String result, Exception e, String errorMessage) {
//
//                    }
//                });
                break;
            case R.id.tv_glide_cache_samll://测试Glide缓存
//                Glide.with(mActivity).load("http://hr.zqlwl.com/upload/ehr/apps/pic/0005.jpg").skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_glide_pic_cache);
//                Picasso.with(mActivity).load("http://hr.zqlwl.com/upload/ehr/apps/pic/0005.jpg").into(iv_glide_pic);
                break;
            case R.id.tv_glide_cache_big://测试Glide缓存
                startActivity(Glide2Activity.class);
                break;
        }
    }

    private void select() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", R.drawable.lvgvrv_arrow_down);
        map.put("2", R.drawable.ic_launcher);
//        BasisGlideUtils.loadWithError(mActivity, Urls.picUrl1, iv_glide_pic, "1", map);
    }
}
