package indi.ayun.original_mvp.manager;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.base.BaseComFragment;
import indi.ayun.original_mvp.i.OnFragmentInteractionListener;
import indi.ayun.original_mvp.mlog.MLog;
import static indi.ayun.original_mvp.OriginalMVP.comFragmentMgr;


public class ComFragmentMgr{
    public static OnFragmentInteractionListener mListener;
    public static FragmentActivity fragmentActivity;
    //单例模式
    private static ComFragmentMgr instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static ComFragmentMgr getComFragmentMgr() {
        if (instance == null) {
            synchronized (ComFragmentMgr.class){
                if (instance == null) {
                    instance = new ComFragmentMgr();
                }
            }
        }
        return instance;
    }

    public ComFragmentMgr getInstance(){
        return comFragmentMgr;
    }

    private static Map<String,BaseComFragment> comFragmentList =new HashMap<>();


    /**
     * 加入一个fragment
     * @param fragment
     */
    public void addComFragment(BaseComFragment fragment){
        MLog.d("加入fragment名称："+fragment.getClass().getSimpleName());
//        if (comFragmentList.get(fragment.getClass().getSimpleName())==null){
            comFragmentList.put(fragment.getClass().getSimpleName(),fragment);//这里要覆盖
//        }
    }

    /**
     * 移除一个fragment
     * @param fragment
     */
    public void removeComFragment(BaseComFragment fragment){
        MLog.d("移除fragment名称："+fragment.getClass().getSimpleName());
        comFragmentList.remove(fragment.getClass().getSimpleName());
    }

    /**
     * 移除所有fragment
     */
    public void removeAllComFragment(){
        comFragmentList.clear();
    }

    /**
     * 得到指定的fragment
     * @param fragment
     */
    public BaseComFragment getComFragment(Class<? extends BaseComFragment> fragment){
        return comFragmentList.get(fragment.getSimpleName());
    }

    /**
     * 启动器
     * @param activity
     * @return
     */
    public ComFragmentMgr starter(FragmentActivity activity, OnFragmentInteractionListener listener){
        mListener =listener;
        fragmentActivity=activity;
        return getInstance();
    }


    public OnFragmentInteractionListener getListener() {
        return mListener;
    }
    public FragmentListenerMgr getFragmentListenerMgr(){
        return OriginalMVP.fragmentListenerMgr;
    }

    /**
     * 获取监听
     */
    public OnFragmentInteractionListener getListener(Class tClass) {
        return getFragmentListenerMgr().getListener(tClass);
    }

    public FragmentManager getFM() {
        return getFragmentActivity().getSupportFragmentManager();
    }

    public FragmentTransaction getFt() {
        return getFM().beginTransaction();
    }

    public static FragmentActivity getFragmentActivity() {
        return fragmentActivity;
    }
}
