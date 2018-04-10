package com.gingold.basisdependency.activity;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.gingold.basisdependency.Base.BaseHtml5Activity;
import com.gingold.basisdependency.R;
import com.gingold.basislibrary.utils.BasisLogUtils;

/**
 * H5交互测试
 */

public class Html5Activity extends BaseHtml5Activity {
    private LinearLayout mLayout;

    @Override
    public void setupViewLayout() {
        setContentView(R.layout.activity_html5);
    }

    @Override
    public void initView() {
        mLayout = (LinearLayout) findViewById(R.id.ll_html5_webview);
    }

    @Override
    public void listener() {
    }

    @Override
    public void logicDispose() {
        mHomeUrl = "http://flower.zqlwl.com/index.php/Shop";
        initWebView(mLayout);
        //重新设置后将取代前面设置的client对象
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new WebViewClient());
        //JS交互
        mWebView.addJavascriptInterface(new JSInterfaces(), "AndroidRec");
//        mWebView.loadUrl("javascript:方法名(" + "'" + 参数 + "'" + ")");
        mWebView.loadUrl(mHomeUrl);
//        test();
    }

    @Override
    public void onClick(View v) {
    }

    public void test() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                BasisLogUtils.e(isBottom() + " " + isTop());//测试顶部和底部判断逻辑
                test();
            }
        }, 1000);
    }

    private class JSInterfaces {
        @JavascriptInterface
        public void showToastRec(String arg) {
            BasisLogUtils.e("showToastRec");
        }

        @JavascriptInterface
        public void loginSuccess(String arg) {
            BasisLogUtils.e("loginSuccess");
        }

        @JavascriptInterface
        public void updateSuccess(String arg) {
            BasisLogUtils.e("updateSuccess");
        }

        @JavascriptInterface
        public void loginFailure(String arg) {
            BasisLogUtils.e("loginFailure");
        }

        @JavascriptInterface
        public void isHomePage(String arg) {
            BasisLogUtils.e("isHomePage");
        }
    }

    @Override
    public boolean doShouldOverrideUrlLoading(String url) {
        //            view.loadUrl(url);//            return true;
        BasisLogUtils.e("shouldOverrideUrlLoading: " + url);
        return super.doShouldOverrideUrlLoading(url);
    }

    @Override
    public void doPageStarted(WebView view, String url, Bitmap favicon) {
        BasisLogUtils.e("onPageStarted: " + url);
        super.doPageStarted(view, url, favicon);
    }

    @Override
    public void doPageFinished(WebView view, String url) {
        super.doPageFinished(view, url);
        BasisLogUtils.e("onPageFinished: " + url);
    }

    @Override
    public void doScaleChanged(WebView view, float oldScale, float newScale) {
        super.doScaleChanged(view, oldScale, newScale);
        BasisLogUtils.e("onScaleChanged: " + oldScale + " - " + newScale);
    }

    @Override
    public void doReceivedTitle(WebView view, String title) {
        super.doReceivedTitle(view, title);
        BasisLogUtils.e("onReceivedTitle: " + title);
    }

    @Override
    public void doReceivedIcon(WebView view, Bitmap icon) {
        super.doReceivedIcon(view, icon);
        BasisLogUtils.e("doReceivedIcon: ");
    }
}
