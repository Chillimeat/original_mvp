package indi.ayun.original_mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.i.OnFragmentInteractionListener;
import indi.ayun.original_mvp.manager.ActivityMgr;
import indi.ayun.original_mvp.manager.ComFragmentMgr;
import indi.ayun.original_mvp.manager.FragmentListenerMgr;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.permission.CreatePermission;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFragment extends Fragment  implements OnFragmentInteractionListener{
    protected OnFragmentInteractionListener listener;
    private BaseFragment fragment;
    private FragmentActivity activity;
    private CreatePermission createPermission;
    private UtilBase mUtilBase;

    private Map<String,Integer> FIRST = new HashMap<>();
    public String TAG = "BaseFragment";

    public BaseFragment()
    {
        this.TAG = getClass().getName();
        fragment=this;
        FIRST.put(TAG,1);
    }

    public BaseFragment(OnFragmentInteractionListener listener)
    {
        this.TAG = getClass().getName();
        this.listener=listener;
        fragment=this;
        FIRST.put(TAG,1);
    }

    public BaseFragment(OnFragmentInteractionListener listener,Bundle bundle)
    {
        this.TAG = getClass().getName();
        this.listener=listener;
        fragment=this;
        FIRST.put(TAG,1);
        fragment.setArguments(bundle);
    }

    public static BaseFragment newInstance(BaseFragment fragment) {
        return fragment;
    }

    public String getTAG()
    {
        return TAG;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    /**
     * 当前fragment被添加到activity中回调，只会被调用一次
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        MLog.d("生命周期");
        super.onAttach(context);
        getFragmentListenerMgr().addListener(this,this);
        activity=(FragmentActivity) context;
        getCommonFragmentMgr().starter(activity,this);
        createPermission= CreatePermission.with(activity);
        mUtilBase=new UtilBase();
        mUtilBase.setContext(context);
        mUtilBase.setActivity(activity);
        mUtilBase.setFragment(this);
    }
    /**
     * 该方法只在我们直接用标签在布局文件中定义的时候才会调用
     * @param context
     * @param attrs
     * @param savedInstanceState
     */
    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }

    /**
     * 创建fragment时被调用，只会调用一次
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        MLog.d("生命周期");
        super.onCreate(savedInstanceState);
    }
    /**
     * 每次创建绘制该fragment的view组件时回调，会将显示的view返回
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MLog.d("生命周期");
        return null;
    }
    /**
     * 当fragment所在的activity启动完成时调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 启动fragment时调用
     */
    @Override
    public void onStart() {
        super.onStart();
    }
    /**
     * 恢复fragment时调用，onStart后一定调用onResume，onStart可见onResume才能交互
     */
    @Override
    public void onResume() {
        MLog.d("生命周期");
        super.onResume();
    }

    /**
     * 暂停
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 停止
     */
    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * 销毁fragment所包含的view组件时使用
     */
    @Override
    public void onDestroyView() {
        MLog.d("生命周期");
        super.onDestroyView();
        getFragmentListenerMgr().removeListener(this);
        FIRST.remove(TAG);
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * onDestroy之后一定调用，只调用一次，fragment从activity中移除或者替换时调用
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 监听不是初始化的第一次处于可见状态
     */
    public abstract void onAgainCreated();
    //runinggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg

    //----------------------------------------------------------------------------------------------可见
    /**
     * 生命周期可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见
            onVisible();
            if (FIRST.get(TAG) != 1) {
                onAgainCreated();//交互
            } else {
                FIRST.put(TAG,2) ;
            }
        } else {
            //不可见
            onHidden();
        }
    }

    /**
     * 被遮挡后可见
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        if (getChildFragmentManager().getFragments() != null) {
            for (Fragment fragment : getChildFragmentManager().getFragments()) {
                if (fragment != null) {
                    fragment.onHiddenChanged(hidden);
                }
            }
        }
        if (hidden) {
            //隐藏
            onHidden();
        } else {
            //显示
            onVisible();
            if (FIRST.get(TAG) != 1) {
                onAgainCreated();//交互
            } else {
                FIRST.put(TAG,2) ;
            }
        }
    }

    /**
     * 监听处于不可见状态
     */
    public void onHidden(){

    }
    /**
     * 监听处于可见状态
     */
    public void onVisible(){

    }


    //-----------------------------------------------------------------------------------------activity

    /**
     * 获取activity
     * @return
     */
    public FragmentActivity activityContext(){
        return activity;
    }

    public ActivityMgr getActivityMgr(){
        return OriginalMVP.activityMgr;
    }

    /**
     * 引用activity
     * @param activity
     * @return
     */
    public Activity activity(Class<? extends Activity> activity){
        return getActivityMgr().activity(activity);
    }

    /**
     * 引用activity
     * @return
     */
    public FragmentActivity activity(){
        return activity;
    }
    //-----------------------------------------------------------------------------------------监听
    public OnFragmentInteractionListener getListener() {
        return listener;
    }

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
    //--------------------------------------------------------------------------------------公共fragment
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

    //--------------------------------------------------------------------------------------------------------------------功能
    public CreatePermission createPermission(String... permission){
        return createPermission.checkPermission(permission);
    }

}
