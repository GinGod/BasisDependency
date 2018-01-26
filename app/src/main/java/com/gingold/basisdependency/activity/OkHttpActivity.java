package com.gingold.basisdependency.activity;

import android.view.View;
import android.widget.TextView;

import com.gingold.basisdependency.Base.BaseActivity;
import com.gingold.basisdependency.R;
import com.gingold.basisdependency.bean.RequestBean;
import com.gingold.basislibrary.okhttp.BasisCallback;
import com.gingold.basislibrary.okhttp.BasisOkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static com.gingold.basisdependency.R.id.tv_okhttp_result;

/**
 *
 */

public class OkHttpActivity extends BaseActivity {

    @Bind(R.id.tv_okhttp_request)
    TextView mTvOkhttpRequest;
    @Bind(R.id.tv_okhttp_request_basis)
    TextView mTvOkhttpRequestBasis;
    @Bind(tv_okhttp_result)
    TextView mTvOkhttpResult;

    private String url = "http://10.120.10.78:8066/api/pms/ExpressCompany/GetNewStationInfo",
            json = "{\"DistributionCode\":\"qlkd\",\"DeptId\":1925802,\"ExpressCompanylevel\":0,\"currentpage\":0,\"pagesize\":0}";
    public OkHttpClient mOkHttpClient;

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
                request();

                break;
            case R.id.tv_okhttp_request_basis:
                request1();
                break;
            case tv_okhttp_result:
                break;
        }
    }

    private void request1() {

        BasisOkHttpUtils.
                postString()
                .dialog(mActivity)
                .url(url)
                .addParams("DistributionCode", "qlkd")
                .addParams("DeptId", "1925802")
                .addParams("ExpressCompanylevel", "0")
                .addParams("currentpage", "0")
                .addParams("pagesize", "0")
                .addParams("pagesize1", null)
                .build()
                .execute(new BasisCallback() {
                    @Override
                    public void onSuccess(Call call, Response response, String result) {
                        toast(result);
                    }

                    @Override
                    public void onFailure(String url, String content, Call call, Exception e, String message) {

                    }

                    @Override
                    public void onException(String url, String content, String result, Exception e, String errorMessage) {

                    }
                });
    }

    private void request() {
        RequestBean requestBean = new RequestBean();
        requestBean.DistributionCode = "qlkd";
        requestBean.DeptId = 1925802;
        requestBean.ExpressCompanylevel = 0;
        requestBean.currentpage = 0;
        requestBean.pagesize = 0;
//        url = "http://newpms.tswlsys.com/pms/dispatStationListByCityId_hascode.do";
        String content = "{cityId:441900,\"distributionCode\":\"rfd\"}";

        BasisOkHttpUtils
                .postString()
                .dialog(mActivity)
                .url(url)
//                .addParams("DistributionCode", "qlkd")
//                .addParams("DeptId", "1925802")
//                .addParams("ExpressCompanylevel", "0")
//                .addParams("currentpage", "0")
//                .addParams("pagesize", "0")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(json)
                .build()
                .execute(new BasisCallback() {
                    @Override
                    public void onSuccess(Call call, Response response, String result) {
                        toast(result);
                    }

                    @Override
                    public void onFailure(String url, String content, Call call, Exception e, String message) {

                    }

                    @Override
                    public void onException(String url, String content, String result, Exception e, String errorMessage) {

                    }


                });
    }

    private void request2() {
//        mOkHttpClient = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(mediaType, content);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//        mCall = mOkHttpClient.newCall(request);
    }
}


