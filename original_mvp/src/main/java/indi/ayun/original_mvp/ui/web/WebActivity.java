package indi.ayun.original_mvp.ui.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import indi.ayun.original_mvp.R;
import indi.ayun.original_mvp.base.BaseActivity;
import indi.ayun.original_mvp.i.OnFragmentInteractionListener;
import indi.ayun.original_mvp.manager.WebViewMgr;
import indi.ayun.original_mvp.utils.verification.IsNothing;


public class WebActivity extends BaseActivity implements OnFragmentInteractionListener {
    private WebViewMgr webMgr;
    private Intent intent;

    WebView webView;
    RelativeLayout webClose;
    TextView webTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setFullBlackWord();
        webView=findViewById(R.id.web_view);
        webClose=findViewById(R.id.web_close);
        webTitle=findViewById(R.id.web_top_title);
        webMgr=new WebViewMgr(webView);
        intent=getIntent();
        if (null!=intent){
           if (IsNothing.onAnything(intent.getStringExtra("title"))){
               webTitle.setText(intent.getStringExtra("title"));
           }
           if (IsNothing.onAnything(intent.getStringExtra("url"))){
               webView.loadUrl(intent.getStringExtra("url"));
           }
        }
        WebViewMgr.setCloseWeb(webView,webClose,this);
    }

    @Override
    public void onAgainCreated(int num) {
        
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {
        if (bundle!=null&&IsNothing.onAnything(bundle.getString("type"))){
            if (bundle.getString("type").equals("close")){
                finish();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {  //表示按返回键
            if (webView.canGoBack()) {
                webView.goBack();
            }else {
               finish();
            }
            return true;    //已处理
        }
        return true;
    }

}
