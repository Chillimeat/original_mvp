package indi.ayun.original_mvp.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.i.OnFragmentInteractionListener;
import indi.ayun.original_mvp.manager.ActivityMgr;
import indi.ayun.original_mvp.manager.ComFragmentMgr;
import indi.ayun.original_mvp.manager.FragmentListenerMgr;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.permission.CreatePermission;

public class BaseActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private CreatePermission createPermission;
    private UtilBase mUtilBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MLog.d("生命周期");
        super.onCreate(savedInstanceState);
        setInitContext();
    }

    public void setInitContext(){
        getActivityMgr().addActivity(this);
        getFragmentListenerMgr().addListener(this,this);
        createPermission= CreatePermission.with(this);
        mUtilBase=new UtilBase();
        mUtilBase.setContext(this);
        mUtilBase.setActivity(this);
        getCommonFragmentMgr().starter(this,this);
        BaseDialog.init(this,this);
    }

    @Override
    protected void onStart() {
        MLog.d("生命周期");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        MLog.d("生命周期");
        super.onRestart();
        setInitContext();
    }

    @Override
    protected void onResume() {
        MLog.d("生命周期");
        super.onResume();
    }

    //runinggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg

    //-------------------------------------------------------------------------------------------------------------activity

    public ActivityMgr getActivityMgr(){
        return OriginalMVP.activityMgr;
    }

    /**
     * 引用activity
     * @param activity
     * @return
     */
    public Activity activity(Class<? extends Activity> activity){
        return OriginalMVP.activityMgr.activity(activity);
    }
    //-------------------------------------------------------------------------------------------------------------监听

    public FragmentListenerMgr getFragmentListenerMgr(){
        return OriginalMVP.fragmentListenerMgr;
    }

    @Override
    public void onFragmentInteraction(Bundle bundle) {

    }
    /**
     * 获取监听
     */
    public OnFragmentInteractionListener getListener(Class tClass) {
       return getFragmentListenerMgr().getListener(tClass);
    }
    //-------------------------------------------------------------------------------------------------------------fragment
    public BaseFragment starter(BaseFragment baseFragment){
        return BaseFragment.newInstance(baseFragment);
    }

    //-------------------------------------------------------------------------------------------------------------公共fragment
    /**
     * 获取commonFragmentMgr
     * @return
     */
    public ComFragmentMgr getCommonFragmentMgr(){
        return OriginalMVP.comFragmentMgr;
    }

    /**
     * 得到指定的fragment
     * @param fragment
     */
    public Fragment getComFragment(Class<? extends BaseComFragment> fragment){
        return getCommonFragmentMgr().getComFragment(fragment);
    }


    /**
     * 移除一个fragment
     * @param fragment
     */
    public void removeComFragment(BaseComFragment fragment){
        getCommonFragmentMgr().removeComFragment(fragment);
    }

    /**
     * 移除一个fragment
     */
    public void removeAllComFragment(){
        getCommonFragmentMgr().removeAllComFragment();
    }
    //-------------------------------------------------------------------------------------------------------------物理
    /**
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    //-------------------------------------------------------------------------------------------------------------性能与优化
    /**
     * 防止快速点击
     * @return 返回true才能点击
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 透明的状态栏，黑色的字
     */
    public void setFullBlackWord(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //--------------------------------------------------------------------------------------------------------------------功能
    public CreatePermission createPermission(String... permission){
        return createPermission.checkPermission(permission);
    }

    //runinggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg

    @Override
    protected void onPause() {
        MLog.d("生命周期");
        super.onPause();
    }

    @Override
    protected void onStop() {
        MLog.d("生命周期");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MLog.d("生命周期");
        super.onDestroy();
        getActivityMgr().removeActivity(this);
        getFragmentListenerMgr().removeListener(this);
    }


}
