package indi.ayun.mingwork_all.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import indi.ayun.mingwork_all.MingWork_All;
import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;
import indi.ayun.mingwork_all.manager.ActivityMgr;
import indi.ayun.mingwork_all.manager.ComFragmentMgr;
import indi.ayun.mingwork_all.manager.FragmentListenerMgr;
import indi.ayun.mingwork_all.permission.CreatePermission;

import java.util.HashMap;
import java.util.Map;

public class BaseFragment extends Fragment  implements OnFragmentInteractionListener{
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

    @Override
    public void onAttach(@NonNull Context context) {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return null;
    }

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
                onAgainVisible();//交互
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
                onAgainVisible();//交互
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
    /**
     * 监听不是初始化的第一次处于可见状态
     */
    public void onAgainVisible(){

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
        return MingWork_All.activityMgr;
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
        return MingWork_All.fragmentListenerMgr;
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
        return MingWork_All.comFragmentMgr;
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
    //runinggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getFragmentListenerMgr().removeListener(this);
        FIRST.remove(TAG);
    }
}
