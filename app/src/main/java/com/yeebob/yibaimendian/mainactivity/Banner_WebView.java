package com.yeebob.yibaimendian.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yeebob.yibaimendian.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 广告推广网页
 * Created by WGl on 2016-1-25.
 */
@ContentView(R.layout.banner_webview)
public class Banner_WebView extends AppCompatActivity {

    @ViewInject(R.id.web_webView)
    private WebView mWebView;

    private String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        x.view().inject(this);
        initWeb();   //打开广告页面

    }

    private void initWeb() {

        //获取广告href url
        Intent intent = getIntent();
        URL = intent.getStringExtra("url");

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.loadUrl(URL);
        mWebView.setWebViewClient(new WebViewClient());
    }
   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //设置回退
        //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }
        return false;
    }*/
}
