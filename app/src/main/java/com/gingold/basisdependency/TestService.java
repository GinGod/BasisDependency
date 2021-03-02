package com.gingold.basisdependency;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.gingold.basislibrary.Base.BasisBaseService;
import com.gingold.basislibrary.utils.BasisLogUtils;

/**
 *
 */

public class TestService extends BasisBaseService {
    private int num = 0;

    @Override
    public void initData() {
        Toast.makeText(TestService.this, "test", Toast.LENGTH_SHORT).show();
        test();
    }

    private void test() {
        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                num++;
                BasisLogUtils.e(num + " ---");
                if (num > 5) {
                    Toast.makeText(TestService.this, "test", Toast.LENGTH_SHORT).show();
                } else {
                    test();
                }
            }
        }, 1000);
    }

    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
