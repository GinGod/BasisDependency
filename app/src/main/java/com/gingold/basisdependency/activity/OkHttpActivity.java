package com.gingold.basisdependency.activity;

import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.data.Urls;
import com.gingold.basislibrary.okhttp.BasisCallback;
import com.gingold.basislibrary.okhttp.BasisOkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Response;

import static com.gingold.basisdependency.R.id.tv_okhttp_result;

/**
 * OKHttp工具类测试
 */

public class OkHttpActivity extends BaseActivity {

    @Bind(R.id.tv_okhttp_request)
    TextView mTvOkhttpRequest;
    @Bind(R.id.tv_okhttp_request_basis)
    TextView mTvOkhttpRequestBasis;
    @Bind(tv_okhttp_result)
    TextView mTvOkhttpResult;

    private String json = "{\"appKey\":\"zqlwl\",\"data\":{\"distributioncode\":\"qlkd\"},\"tag\":\"android\"}";

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);
        initTitle("OkHttp", "");
    }

    @Override
    public void initView() {

    }

    @Override
    public void listener() {

    }

    @Override
    public void logicDispose() {

    }

    @Override
    public void onClick(View view) {

    }

    @OnClick({R.id.tv_okhttp_request, R.id.tv_okhttp_request_basis, tv_okhttp_result})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_okhttp_request:
//                request();
                requestCopy();
                break;
            case R.id.tv_okhttp_request_basis:
//                requestGet();
                requestGetCopy();
                break;
            case R.id.tv_okhttp_result:
                break;
        }
    }

    private void requestGetCopy() {
        BasisOkHttpUtils.getString(Urls.geturl, mBasisCallback);
    }

    private void requestCopy() {
        BasisOkHttpUtils.postString(Urls.timeurl, json, mBasisCallback);
    }

    private void requestGet() {
        BasisOkHttpUtils.postString().get().url(Urls.geturl).build().execute();
    }

    private void request() {
        BasisOkHttpUtils
                .postString()
                .dialog(mActivity)
                .url(Urls.timeurl)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute();
    }

    private BasisCallback mBasisCallback = new BasisCallback() {
        @Override
        public void onSuccess(Call call, Response response, String result) {
            toast(result);
        }

        @Override
        public void onFailure(String url, String content, Call call, Exception e, String message) {
            toast(message);
        }

        @Override
        public void onException(String url, String content, String result, Exception e, String errorMessage) {
            toast(errorMessage);
        }
    };

}


