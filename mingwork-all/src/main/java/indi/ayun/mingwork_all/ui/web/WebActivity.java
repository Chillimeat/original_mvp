package indi.ayun.mingwork_all.ui.web;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.ayun.mingwork_all.databinding.ActivityWebBinding;

import indi.ayun.mingwork_all.base.BaseActivity;
import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;
import indi.ayun.mingwork_all.manager.WebViewMgr;
import indi.ayun.mingwork_all.utils.verification.IsNothing;


public class WebActivity extends BaseActivity implements OnFragmentInteractionListener {

    private WebViewMgr webMgr;
    private Intent intent;
    private ActivityWebBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWebBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setFullBlackWord();

        webMgr=new WebViewMgr(binding.webView);
        intent=getIntent();
        if (null!=intent){
           if (IsNothing.onAnything(intent.getStringExtra("title"))){
               binding.webTopTitle.setText(intent.getStringExtra("title"));
           }
           if (IsNothing.onAnything(intent.getStringExtra("url"))){
               binding.webView.loadUrl(intent.getStringExtra("url"));
           }
        }
        WebViewMgr.setCloseWeb(binding.webView,binding.webClose,this);
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
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
            }else {
               finish();
            }
            return true;    //已处理
        }
        return true;
    }

}
