package indi.ayun.mingwork_all.manager;


import android.app.Activity;
import android.app.Dialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;
import indi.ayun.mingwork_all.mlog.MLog;


import java.util.HashMap;

/**
 * 主要功能: 管理和回收碎片数据监听
 */
public class FragmentListenerMgr {

    
    //存储
    private static HashMap<String, OnFragmentInteractionListener> listenerHashMap=new HashMap<>();

    //单例模式
    private static FragmentListenerMgr instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static FragmentListenerMgr getScreenManager() {
        if (instance == null) {
            synchronized (FragmentListenerMgr.class){
                if (instance == null) {
                    instance = new FragmentListenerMgr();
                }
            }
        }
        return instance;
    }

    /**
     * 纳入推栈集合中
     */
    public void addListener(Activity activity,OnFragmentInteractionListener listener) {
        MLog.d("addListener:"+activity.getClass().getSimpleName()+";"+listener);
        put(activity.getClass().getSimpleName(),listener);
    }
    public void addListener(FragmentActivity activity, OnFragmentInteractionListener listener) {
        MLog.d("addListener:"+activity.getClass().getSimpleName()+";"+listener);
        put(activity.getClass().getSimpleName(),listener);
    }
    public void addListener(Fragment fragment, OnFragmentInteractionListener listener) {
        MLog.d("addListener:"+fragment.getClass().getSimpleName()+";"+listener);
        put(fragment.getClass().getSimpleName(),listener);
    }
    public void addListener(Dialog dialog, OnFragmentInteractionListener listener) {
        MLog.d("addListener:"+dialog.getClass().getSimpleName()+";"+listener);
        put(dialog.getClass().getSimpleName(),listener);
    }

    private void put(String name,OnFragmentInteractionListener listener){
        if (listenerHashMap.get(name)==null){
            listenerHashMap.put(name,listener);
        }
    }
    /**
     * 销毁并移除
     */
    public void removeListener(Activity activity) {
        listenerHashMap.remove(activity.getClass().getSimpleName());
    }
    public void removeListener(FragmentActivity activity) {
        listenerHashMap.remove(activity.getClass().getSimpleName());
    }
    public void removeListener(Fragment fragment) {
        listenerHashMap.remove(fragment.getClass().getSimpleName());
    }
    public void removeListener(Dialog dialog) {
        listenerHashMap.remove(dialog.getClass().getSimpleName());
    }

    /**
     * 获取
     * @param tClass
     */
    public OnFragmentInteractionListener getListener(Class tClass) {
        MLog.d("getListener:"+tClass.getSimpleName()+";"+listenerHashMap.get(tClass.getSimpleName()));
        return listenerHashMap.get(tClass.getSimpleName());
    }

    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        listenerHashMap.clear();
    }


}
