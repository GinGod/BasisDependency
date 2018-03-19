package com.gingold.basisdependency.activity;

import android.view.View;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.okhttp.BasisCallback;
import com.gingold.basislibrary.okhttp.BasisOkHttpUtils;
import com.gingold.basislibrary.utils.BasisLogUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 异常信息获取类(异常信息打印不全测试)
 */

public class ExceptionInfoActivity extends BaseActivity {
    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_exceptioninfo);
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {
        setOnClickListener(R.id.tv_exceptioninfo_null);
        setOnClickListener(R.id.tv_exceptioninfo_net);
    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exceptioninfo_null:
                String str = null;
                try {
                    str.length();
                } catch (Exception e) {
                    BasisLogUtils.e(e.getMessage() + " --- \n");
                    BasisLogUtils.e(e.getLocalizedMessage() + " --- \n");
                    BasisLogUtils.e(getExceptionInfo(e));
                }
                break;
            case R.id.tv_exceptioninfo_net:
                BasisOkHttpUtils.postString().url("http://www.baidu.con").build().execute(new BasisCallback() {
                    @Override
                    public void onSuccess(Call call, Response response, String result) {

                    }

                    @Override
                    public void onFailure(String url, String content, Call call, Exception e, String message) {
                        BasisLogUtils.e(e.getMessage() + " --- \n" + message);
                        BasisLogUtils.e(e.getLocalizedMessage() + " --- \n");
                        BasisLogUtils.e(getExceptionInfo(e));
                    }

                    @Override
                    public void onException(String url, String content, String result, Exception e, String errorMessage) {

                    }
                });
                break;
        }
    }

    /**
     * 获取异常信息
     */
    public static String getExceptionInfo(Exception e) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        e.printStackTrace(pw);
        pw.close();
        String info = writer.toString();
        return info;
    }
}
