package indi.ayun.mingwork_all.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;
import java.util.HashMap;

import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;
import indi.ayun.mingwork_all.ui.web.WebActivity;

public class WebViewMgr {
    private WebView webView;
    private WebSettings settings;

    private View close;

    /**
     * 基本初始化，包含：
     * 1.开启缩放功能;
     * 2. 开启自适应
     * 3. 开启js
     * 4. 开启自动js弹窗
     * 5.支持多窗口打开
     * 6. 支持网页缓存
     * 7.支持flash
     * @param webView
     */
    public WebViewMgr(WebView webView){
        this.webView = webView;
        settings = webView.getSettings();

        //开启缩放功能
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);// 设置WebView可触摸放大缩小
        settings.setDisplayZoomControls(false);// 设置WebView可触摸放大缩小

        //开启自适应
        settings.setUseWideViewPort(true);//不设置排版可能会乱
        settings.setLoadWithOverviewMode(true);//充满整个webview
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);//设置加载页面的方式

        //开启js
        settings.setJavaScriptEnabled(true);//WebView内直接使用JavaScript

        //开启自动js弹窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口

        //支持多窗口打开
        settings.setSupportMultipleWindows(true);//支持新窗口  -------网页内部跳转必须false-------

        //支持网页缓存
        settings.setAppCacheEnabled(true);//支持缓存app
        settings.setDomStorageEnabled(true);//设置缓存app区域
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        //支持flash
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);//flash

        //不弹出浏览器
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

    }

    /**
     * 开启自适应功能
     */
    public WebViewMgr enableAdaptive(){
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        return this;
    }

    /**
     * 禁用自适应功能
     */
    public WebViewMgr disableAdaptive(){
        settings.setUseWideViewPort(false);
        settings.setLoadWithOverviewMode(false);
        return this;
    }

    /**
     * 开启JavaScript
     */
    @SuppressLint("SetJavaScriptEnabled")
    public WebViewMgr enableJavaScript(){
        settings.setJavaScriptEnabled(true);
        return this;
    }

    /**
     * 禁用JavaScript
     */
    public WebViewMgr disableJavaScript(){
        settings.setJavaScriptEnabled(false);
        return this;
    }

    /**
     * 开启缩放功能
     */
    public WebViewMgr enableZoom(){
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        return this;
    }

    /**
     * 禁用缩放功能
     */
    public WebViewMgr disableZoom(){
        settings.setSupportZoom(false);
        settings.setUseWideViewPort(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(true);
        return this;
    }



    /**
     * 开启JavaScript自动弹窗
     */
    public WebViewMgr enableJavaScriptOpenWindowsAutomatically(){
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        return this;
    }

    /**
     * 禁用JavaScript自动弹窗
     */
    public WebViewMgr disableJavaScriptOpenWindowsAutomatically(){
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        return this;
    }
    /**
     * 支持打开多弹窗
     */
    public WebViewMgr enableSupportMultipleWindows(){
        settings.setSupportMultipleWindows(true);
        return this;
    }

    /**
     * 禁用打开多弹窗
     */
    public WebViewMgr disableSupportMultipleWindows(){
        settings.setSupportMultipleWindows(false);
        return this;
    }
    /**
     * 支持网络缓存
     */
    public WebViewMgr enableCooking(){
        settings.setAppCacheEnabled(true);//支持缓存app
        settings.setDomStorageEnabled(true);//设置缓存app区域
        return this;
    }

    /**
     * 禁用网络缓存
     */
    public WebViewMgr disableCooking(){
        settings.setAppCacheEnabled(false);//支持缓存app
        settings.setDomStorageEnabled(false);//设置缓存app区域
        return this;
    }
    /**
     * 可用flash
     */
    public WebViewMgr enableFlash(){
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        return this;
    }

    /**
     * 禁用flash
     */
    public WebViewMgr disableFlash(){
        settings.setPluginState(WebSettings.PluginState.OFF);
        return this;
    }
    /**
     * 返回
     * @return true：已经返回，false：到头了没法返回了
     */
    public boolean goBack(){
        if(webView.canGoBack()){
            webView.goBack();
            return true;
        }else{
            return false;
        }
    }

    /**
     * 刷新
     * @param url
     */
    public void onRefresh(String url){
        webView.loadUrl(url);
        webView.reload();
    }

    /**
     * 打开网页
     * @param activity
     * @param url
     * @param title
     * @param webtag
     */
    public static void openWeb(Activity activity,String url,String title,String webtag){
        Intent intent=new Intent(activity, WebActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("webtag",webtag);
        activity.startActivity(intent);
    }

    /**
     * 打开自定义网页
     * @param activity
     * @param webActivity
     * @param map
     */
    public static void openWeb(Activity activity, Activity webActivity, HashMap<String,Object> map){
        Intent intent=new Intent(activity, webActivity.getClass());
        Bundle bundle=new Bundle();
        bundle.putSerializable("data", map);
        intent.putExtras(bundle);
        //Bundle bundle = getIntent().getExtras();
        //SerializableMap serializableMap = (SerializableMap) bundle.get("map");
        activity.startActivity(intent);
    }

    /**
     * 关闭网页
     * @param view1
     * @param view2
     * @param listener
     */
    public static void setCloseWeb(final WebView view1, View view2, final OnFragmentInteractionListener listener){
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view1.canGoBack()) {
                    view1.goBack();
                }else {
                    Bundle bundle=new Bundle();
                    bundle.putString("type","close");
                    listener.onFragmentInteraction(bundle);
                }
            }
        });
    }

}
