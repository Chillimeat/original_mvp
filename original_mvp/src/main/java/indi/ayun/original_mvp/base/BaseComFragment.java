package indi.ayun.original_mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.i.OnFragmentInteractionListener;
import indi.ayun.original_mvp.manager.ActivityMgr;
import indi.ayun.original_mvp.manager.ComFragmentMgr;
import indi.ayun.original_mvp.manager.FragmentListenerMgr;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.permission.CreatePermission;
import indi.ayun.original_mvp.utils.verification.IsNothing;

public abstract class BaseComFragment extends Fragment  implements OnFragmentInteractionListener{
    protected OnFragmentInteractionListener listener;
    public String TAG = "BaseComFragment";
    private FragmentManager mFragmentManager;
    private static Fragment fragment;
    private Activity activity;
    private CreatePermission createPermission;
    private UtilBase mUtilBase;

    private Map<String,Integer> FIRST = new HashMap<>();
    private Map<String,Boolean> executeAgain = new HashMap<>();
    public BaseComFragment()
    {
        this.TAG = getClass().getName();
        fragment=this;
        getCommonFragmentMgr().addComFragment(this);
        FIRST.put(TAG,1);
        executeAgain.put(TAG,true);
    }

    public BaseComFragment(OnFragmentInteractionListener listener)
    {
        this.TAG = getClass().getName();
        this.listener=listener;
        fragment=this;
        getCommonFragmentMgr().addComFragment(this);
        FIRST.put(TAG,1);
        executeAgain.put(TAG,true);
    }

    public BaseComFragment(OnFragmentInteractionListener listener, FragmentManager fragmentManager)
    {
        this.TAG = getClass().getName();
        this.mFragmentManager=fragmentManager;
        this.listener=listener;
        fragment=this;
        getCommonFragmentMgr().addComFragment(this);
        FIRST.put(TAG,1);
        executeAgain.put(TAG,true);
    }

    public String getTAG()
    {
        return TAG;
    }

    public static BaseComFragment newInstance(BaseComFragment fragment) {
        return fragment;
    }

    /**
     * ??????fragment????????????activity?????????????????????????????????
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        MLog.d("????????????");
        super.onAttach(context);
        activity=(Activity)context;
        getFragmentListenerMgr().addListener(this,this);
        createPermission= CreatePermission.with(activity);
        mUtilBase=new UtilBase();
        mUtilBase.setContext(context);
        mUtilBase.setActivity(activity);
        mUtilBase.setFragment(this);
    }

    /**
     * ?????????????????????????????????????????????????????????????????????????????????
     * @param context
     * @param attrs
     * @param savedInstanceState
     */
    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        MLog.d("????????????");
        super.onInflate(context, attrs, savedInstanceState);
    }

    /**
     * ??????fragment?????????????????????????????????
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        MLog.d("????????????");
        super.onCreate(savedInstanceState);
    }

    /**
     * ?????????????????????fragment???view?????????????????????????????????view??????
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MLog.d("????????????");
        return null;
    }

    /**
     * ???fragment?????????activity?????????????????????
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        MLog.d("????????????");
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * ??????fragment?????????
     */
    @Override
    public void onStart() {
        MLog.d("????????????");
        super.onStart();
    }

    /**
     * ??????fragment????????????onStart???????????????onResume???onStart??????onResume????????????
     */
    @Override
    public void onResume() {
        MLog.d("????????????");
        super.onResume();
        //??????Fragment?????????????????????????????????????????????
        getView().setFocusableInTouchMode(true);
        //??????Fragment????????????????????????????????????
        getView().requestFocus();
        //???????????????View??????KeyListener?????????
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getThatFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
        if (IsNothing.onAnything(FIRST.get(TAG))&&FIRST.get(TAG)>1) {
            if (null==executeAgain.get(TAG)||executeAgain.get(TAG)){//??????????????????????????????
                executeAgain.put(TAG,false);
                int num=FIRST.get(TAG);
                FIRST.put(TAG,num+1);
                boolean bb=onAgainVisible(FIRST.get(TAG));//??????
                executeAgain.put(TAG,bb);//?????????????????????
            }
        } else {
            int num=FIRST.get(TAG);
            FIRST.put(TAG,num+1);
            executeAgain.put(TAG,true);
        }
    }

    /**
     * ??????
     */
    @Override
    public void onPause() {
        MLog.d("????????????");
        super.onPause();
    }

    /**
     * ??????
     */
    @Override
    public void onStop() {
        MLog.d("????????????");
        super.onStop();
    }

    /**
     * ??????fragment????????????view???????????????
     */
    @Override
    public void onDestroyView() {
        MLog.d("????????????");
        super.onDestroyView();
    }

    /**
     * ??????
     */
    @Override
    public void onDestroy() {
        MLog.d("????????????");
        super.onDestroy();
        getFragmentListenerMgr().removeListener(this);
        FIRST.remove(TAG);
        executeAgain.remove(TAG);
    }

    /**
     * onDestroy???????????????????????????????????????fragment???activity??????????????????????????????
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**
     * ???????????????????????????????????????????????????
     * ?????????????????????
     */
    public abstract boolean onAgainVisible(int num);



    //runinggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg

    //-----------------------------------------------------------------------------------------FragmentManager
    public FragmentManager getThatFragmentManager() {
        if (mFragmentManager==null)return getFragmentManager();
        return mFragmentManager;
    }

    //-----------------------------------------------------------------------------------------activity
    /**
     * ??????activity
     * @return
     */
    public Activity activityContext(){
        return activity;
    }

    public ActivityMgr getActivityMgr(){
        return OriginalMVP.activityMgr;
    }

    /**
     * ??????activity
     * @param activity
     * @return
     */
    public Activity activity(Class<? extends Activity> activity){
        return getActivityMgr().activity(activity);
    }
    //-----------------------------------------------------------------------------------------??????
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
     * ????????????
     */
    public OnFragmentInteractionListener getListener(Class tClass) {
        return getFragmentListenerMgr().getListener(tClass);
    }
    //-----------------------------------------------------------------------------------------??????fragment
    /**
     * ??????commonFragmentMgr
     * @return
     */
    public ComFragmentMgr getCommonFragmentMgr(){
        return OriginalMVP.comFragmentMgr;
    }
    /**
     * ????????????????????????fragment
     */
    public static Fragment getComFragment(){
        return fragment;
    }
    /**
     * ???????????????fragment
     * @param fragment
     */
    public Fragment getComFragment(Class<? extends BaseComFragment> fragment){
        return getCommonFragmentMgr().getComFragment(fragment);
    }
    /**
     * ????????????fragment
     * @param fragment
     */
    public void removeComFragment(BaseComFragment fragment){
        getCommonFragmentMgr().removeComFragment(fragment);
    }

    /**
     * ????????????fragment
     */
    public void removeAllComFragment(){
        getCommonFragmentMgr().removeAllComFragment();
    }
    //----------------------------------------------------------------------------------------------??????
    /**
     * ??????????????????
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //??????
            onVisible();
            if (IsNothing.onAnything(FIRST.get(TAG))&&FIRST.get(TAG)>1) {
                if (null==executeAgain.get(TAG)||executeAgain.get(TAG)){//??????????????????????????????
                    executeAgain.put(TAG,false);
                    int num=FIRST.get(TAG);
                    FIRST.put(TAG,num+1);
                    boolean bb=onAgainVisible(FIRST.get(TAG));//??????
                    executeAgain.put(TAG,bb);//?????????????????????
                }
            } else {
                int num=FIRST.get(TAG);
                FIRST.put(TAG,num+1);
                executeAgain.put(TAG,true);
            }
        } else {
            //?????????
            onHidden();
        }
    }

    /**
     * ??????????????????
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
            //??????
            onHidden();
        } else {
            //??????
            onVisible();
            if (IsNothing.onAnything(FIRST.get(TAG))&&FIRST.get(TAG)>1) {
                if (null==executeAgain.get(TAG)||executeAgain.get(TAG)){//??????????????????????????????
                    executeAgain.put(TAG,false);
                    int num=FIRST.get(TAG);
                    FIRST.put(TAG,num+1);
                    boolean bb=onAgainVisible(FIRST.get(TAG));//??????
                    executeAgain.put(TAG,bb);//?????????????????????
                }
            } else {
                int num=FIRST.get(TAG);
                FIRST.put(TAG,num+1);
                executeAgain.put(TAG,true);
            }
        }
    }

    /**
     * ???????????????????????????
     */
    public void onHidden(){

    }
    /**
     * ????????????????????????
     */
    public void onVisible(){

    }

    //-----------------------------------------------------------------------------------------??????

    //--------------------------------------------------------------------------------------------------------------------??????
    public CreatePermission createPermission(String... permission){
        return createPermission.checkPermission(permission);
    }
}
