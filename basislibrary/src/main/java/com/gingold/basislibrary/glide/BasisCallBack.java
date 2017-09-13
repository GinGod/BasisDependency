package com.gingold.basislibrary.glide;

import android.graphics.Bitmap;

import java.io.File;

/**
 *
 */

public interface BasisCallBack {
    /**
     * 图片下载本地成功
     */
    void onSuccess(Bitmap bitmap, File file, String fileName);

    /**
     * 图片下载本地失败
     */
    void onFailure();
}
