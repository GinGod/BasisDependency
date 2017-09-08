package com.gingold.basisdependency;

import android.app.Application;
import android.content.Context;

import com.gingold.basislibrary.utils.BasisLogUtils;

/**
 *
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        BasisLogUtils.e("MyApplication");
        context = getApplicationContext();
    }

    public static Context getContext() {
        if (context == null) {
            context = new MyApplication();
            BasisLogUtils.e("MyApplication");
        }
        return context;
    }
}
