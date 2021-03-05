package indi.ayun.original_mvp.manager;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Stack;

import indi.ayun.original_mvp.mlog.MLog;

/**
 * 主要功能: 管理和回收Act
 */
public class ActivityMgr {

    
    //存储ActivityStack
    private static Stack<WeakReference<Activity>> activityStack = new Stack<>();

    //单例模式
    private static ActivityMgr instance;

    /**
     * 单列堆栈集合对象
     * @return ActivityMgr 单列堆栈集合对象
     */
    public static ActivityMgr getScreenManager() {
        if (instance == null) {
            synchronized (ActivityMgr.class){
                if (instance == null) {
                    instance = new ActivityMgr();
                }
            }
        }
        return instance;
    }

    /**
     * 将Act纳入推栈集合中
     * @param activity Act对象
     */
    public void addActivity(Activity activity) {
        activityStack.add(new WeakReference<Activity>(activity));
    }
    /**
     * 堆栈中销毁并移除
     * @param activity 指定Act对象
     */
    public void removeActivity(Activity activity) {
        Activity item;
        Iterator<WeakReference<Activity>> iterator;
        iterator=activityStack.iterator();
        int i=0;
        while (iterator.hasNext()){
            if (activityStack.get(i)!=null){
                i++;
                item=iterator.next().get();
                if (item==activity){
                    iterator.remove();
                    break;
                }
            }else {
                break;
            }
        }

    }
    /**
     * 获取当前Act对象
     * @return Activity 当前act
     */
    public WeakReference<Activity> currentActivity() {
        WeakReference<Activity> activity = null;
        if (!activityStack.empty()){
            activity = activityStack.lastElement();
        }
        //MLog.i("ActivityMgr-->>currentActivity", activity + "");
        return activity;
    }


    /**
     * 获得当前Act的类名
     * @return String
     */
    public String getCurrentActivityName() {
        String actSimpleName = "";
        if (!activityStack.empty()) {
            actSimpleName = activityStack.lastElement().getClass().getSimpleName();
        }
        //MLog.i("ActivityMgr-->>getCurrentActivityName", actSimpleName);
        return actSimpleName;
    }
    /**
     * 栈中销毁并移除所有Act对象
     */
    public void removeAllActivity() {
        Activity item;
        Iterator<WeakReference<Activity>> iterator;
        iterator=activityStack.iterator();
        int i=0;
        while (iterator.hasNext()){
            if (activityStack.get(i)!=null){
                i++;
                item=iterator.next().get();
                iterator.remove();
                if (item!=null){
                    item.finish();
                }
            }else {
                break;
            }
        }
        System.gc();
        System.exit(0);
    }


    /**
     * 获取activity对象
     * @param activity
     * @return
     */
    public Activity activity(Class<? extends Activity> activity) {
        Activity item= null;
        MLog.d("WeakReference<Activity>-size:"+activityStack.size());
        if (activityStack.size()>0){
            for (int i=0;i<activityStack.size();i++){
                MLog.d("WeakReference<Activity>-name:"+activityStack.get(i).get().getClass().getSimpleName()+"; "+activity.getSimpleName());
                if (activityStack.get(i).get().getClass().getSimpleName().equals(activity.getSimpleName())){
                    item=activityStack.get(i).get();
                }
            }
        }
        MLog.d("WeakReference<Activity>-item:"+item);
        return item;
    }
}
