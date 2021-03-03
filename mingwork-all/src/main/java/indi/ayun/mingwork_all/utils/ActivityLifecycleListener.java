package indi.ayun.mingwork_all.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import indi.ayun.mingwork_all.preference.CredentialPreferences;

public class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {

    private int refCount = 0;
    /**
     *     onCreated;
     *     onStarted
     *     onResumed
     *     onPaused//不能交互，不用重启。
     *     onStopped//这个是重新启动
     *     onDestroyed//这个是死了
     */
    private int RECEPTION=1;//前
    private int BACKSTAGE=0;//后
    private int ABSENT=-1;//si

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        CredentialPreferences.saveAPPState(RECEPTION);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        refCount++;
        CredentialPreferences.saveAPPState(RECEPTION);
        //onStart() 一般表示一个Activity 处于前台
    }

    @Override
    public void onActivityResumed(Activity activity) {
        CredentialPreferences.saveAPPState(RECEPTION);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        CredentialPreferences.saveAPPState(BACKSTAGE);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        refCount--;
        //onStop() 一般表示Activity在后台不可见.refCount=0,app 已经处于后台了
        if(refCount == 0){
            CredentialPreferences.saveAPPState(BACKSTAGE);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        CredentialPreferences.saveAPPState(ABSENT);
    }
}
