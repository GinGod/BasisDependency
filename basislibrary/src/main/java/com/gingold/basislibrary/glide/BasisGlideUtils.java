package com.gingold.basislibrary.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.gingold.basislibrary.utils.BasisLogUtils;

import java.io.File;
import java.util.Map;

/**
 * BasisGlideUtils
 */

public class BasisGlideUtils {
    public static final int DEFAULT = 252;
    public static final int CONTEXT = 52;
    public static final int ACTIVITY = 53;
    public static final int FRAGMENT = 54;
    public static final int FRAGMENTAPP = 55;
    public static final int FRAGMENTACTIVITY = 56;
    public static final int VIEW = 57;
    public static final int CROPCIRCLE = 58;

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void load(Object context, Object model, ImageView view) {
        build(context, model).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadSpecial(Object context, Object model, ImageView view, Context activity, int special) {
        RequestOptions options = new RequestOptions();
        switch (special) {
            case CROPCIRCLE:
//                options.transform(new CropCircleTransformation(activity));
//                options.bitmapTransform(new CropCircleTransformation(activity));
                BasisLogUtils.e("111");
                break;
        }
        build(context, model).apply(options).into(view);
//        GlideApp.with(activity).load(model).transform(new CropCircleTransformation(activity)).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadWithError(Object context, Object model, ImageView view, Drawable placeholder) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholder);
        build(context, model).apply(options).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadWithError(Object context, Object model, ImageView view, int placeholderId) {
        RequestOptions options = new RequestOptions();
        options.placeholder(placeholderId);
        build(context, model).apply(options).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadWithError(Object context, Object model, ImageView view, Map<String, Drawable> map, String key) {
        Drawable drawable = map.get(key);
        RequestOptions options = new RequestOptions();
        options.placeholder(drawable);
        build(context, model).apply(options).into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadWithError(Object context, Object model, ImageView view, String key, Map<String, Integer> map) {
        int resourceId = map.get(key);
        RequestOptions options = new RequestOptions();
        options.placeholder(resourceId);
        build(context, model).apply(options).into(view);
    }

    /**
     * @param context  支持类型: Context, Activity, Fragment, FragmentActivity, View
     * @param duration 过渡动画时长
     */
    public static void load(Object context, Object model, ImageView view, int duration) {
        build(context, model)
                .transition(new DrawableTransitionOptions().crossFade(duration))
                .into(view);
    }

    /**
     * @param context        支持类型: Context, Activity, Fragment, FragmentActivity, View
     * @param sizeMultiplier 请求给定系数的缩略图
     */
    public static void load(Object context, Object model, ImageView view, float sizeMultiplier) {
        build(context, model)
                .thumbnail(sizeMultiplier)
                .into(view);
    }

    /**
     * @param context        支持类型: Context, Activity, Fragment, FragmentActivity, View
     * @param duration       过渡动画时长
     * @param sizeMultiplier 请求给定系数的缩略图
     */
    public static void load(Object context, Object model, ImageView view, int duration, float sizeMultiplier) {
        build(context, model)
                .transition(new DrawableTransitionOptions().crossFade(duration))
                .thumbnail(sizeMultiplier)
                .into(view);
    }

    /**
     * @param context          支持类型: Context, Activity, Fragment, FragmentActivity, View
     * @param thumbnailRequest 缩略图图片资源
     */
    public static void load(Object context, Object model, ImageView view, Object thumbnailRequest, boolean isDrawable) {
        build(context, model)
                .thumbnail(build(context, thumbnailRequest))
                .into(view);
    }

    /**
     * @param context          支持类型: Context, Activity, Fragment, FragmentActivity, View
     * @param duration         过渡动画时长
     * @param thumbnailRequest 缩略图图片资源
     */
    public static void load(Object context, Object model, ImageView view, int duration, Object thumbnailRequest, boolean isDrawable) {
        build(context, model)
                .transition(new DrawableTransitionOptions().crossFade(duration))
                .thumbnail(build(context, thumbnailRequest))
                .into(view);
    }

    /**
     * @param context 支持类型: Context, Activity, Fragment, FragmentActivity, View
     */
    public static void loadAsGif(Object context, Object model, ImageView view) {
        with(context).asGif().load(model).into(view);
    }

    /**
     * 获取Glide磁盘缓存大小
     */
    public static String getCacheSize(Context context) {
        return BasisGlideCacheUtil.getInstance().getCacheSize(context);
    }

    /**
     * 清除图片磁盘缓存
     */
    public static boolean clearDiskCacheSelf(final Context context) {
        return BasisGlideCacheUtil.getInstance().clearDiskCacheSelf(context);
    }

    /**
     * 清除内存缓存
     */
    public static boolean clearCacheMemory(final Context context) {
        return BasisGlideCacheUtil.getInstance().clearCacheMemory(context);
    }

    /**
     * 清除所有缓存
     */
    public static boolean clearAllCacheMemory(final Context context) {
        return clearCacheMemory(context) && clearDiskCacheSelf(context);
    }

    /**
     * 下载图片
     */
    public static void downloadPic(final Context context, final String url, final String fileName, final BasisCallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Bitmap downloadBitmap = Glide
                            .with(context)
                            .asBitmap()
                            .load(url)
                            .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                    BasisDownLoadPicUtil.downLoadPic(context, downloadBitmap, fileName, callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                    BasisDownLoadPicUtil.failure(callBack);
                }
            }
        }.start();
    }

    /**
     * 下载gif图片
     */
    public static void downloadGif(final Context context, final String url, final String fileName, final BasisCallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = GlideApp
                            .with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                            .get();

                    BasisDownLoadPicUtil.downLoadGif(context, file, fileName, callBack);
                } catch (Exception e) {
                    e.printStackTrace();
                    BasisDownLoadPicUtil.failure(callBack);
                }
            }
        }.start();
    }

    /**
     * 获取上下文类型
     */
    private static int getState(Object context) {
        if (context instanceof Context) {
            return CONTEXT;
        } else if (context instanceof Activity) {
            return ACTIVITY;
        } else if (context instanceof Fragment) {
            return FRAGMENT;
        } else if (context instanceof android.app.Fragment) {
            return FRAGMENTAPP;
        } else if (context instanceof FragmentActivity) {
            return FRAGMENTACTIVITY;
        } else if (context instanceof View) {
            return VIEW;
        }
        return DEFAULT;
    }

    /**
     * 根据不同上下文类型返回结果
     */
    public static RequestManager with(Object context) {
        int withState = getState(context);
        if (withState == CONTEXT) {
            return Glide.with((Context) context);
        } else if (withState == ACTIVITY) {
            return Glide.with((Activity) context);
        } else if (withState == FRAGMENT) {
            return Glide.with((Fragment) context);
        } else if (withState == FRAGMENTAPP) {
            return Glide.with((android.app.Fragment) context);
        } else if (withState == FRAGMENTACTIVITY) {
            return Glide.with((FragmentActivity) context);
        } else if (withState == VIEW) {
            return Glide.with((View) context);
        }
        throw new IllegalArgumentException("非法的上下文参数!");
    }

    public static RequestBuilder<Drawable> build(Object context, Object model) {
        return with(context).load(model);
    }

}
