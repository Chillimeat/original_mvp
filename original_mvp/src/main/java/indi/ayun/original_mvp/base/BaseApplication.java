package indi.ayun.original_mvp.base;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import indi.ayun.original_mvp.OriginalMVP;
import indi.ayun.original_mvp.daemon.AbsWorkService;
import indi.ayun.original_mvp.daemon.DaemonEnv;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.preference.OpCredential;
import indi.ayun.original_mvp.utils.ActivityLifecycleListener;



public abstract class BaseApplication extends MultiDexApplication {
    //系统
    private static AlarmManager mAlarm;
    private static KeyguardManager mKeyguardMgr;

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        MLog.d("生命周期");
        super.onCreate();
        mAlarm= (AlarmManager) getSystemService(ALARM_SERVICE);
        mKeyguardMgr= (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        registerActivityLifecycleCallbacks(new ActivityLifecycleListener());
    }

    /**
     * 初始化token
     * @param token
     */
    public int TYPE_TOKEN_DEFAULT=0;
    public int TYPE_TOKEN_VARIABLE=1;
    public void initAccessToken(String token,int type){
        if (type==0) {
            if (!OpCredential.getInstance().getUserLogin()) {
                OpCredential.getInstance().saveUserToken(token);
            }
        }
        if (type==1){
            if (OpCredential.getInstance().getUserLogin()) {
                OpCredential.getInstance().saveUserToken(token);
            }
        }
    }


    public void DaemonEnvInit(@NonNull Context app, @NonNull Class<? extends AbsWorkService> serviceClass, @Nullable Integer wakeUpInterval){
        DaemonEnv.initialize(this, serviceClass, wakeUpInterval);
    }

    public void DaemonEnvInit(@NonNull Context app, @NonNull Class<? extends AbsWorkService> serviceClass){
        DaemonEnv.initialize(this, serviceClass, DaemonEnv.DEFAULT_WAKE_UP_INTERVAL);
    }

    public void DaemonEnvStart(@NonNull final Class<? extends Service> serviceClass){
        DaemonEnv.startServiceMayBind(serviceClass);
    }

    @Override
    public void onTerminate() {
        MLog.d("生命周期");
        // 程序终止的时候执行
        //ToastUtil.showCenter("onTerminate");
        System.out.print("onTerminate");
        terminate();
        super.onTerminate();
    }
    @Override
    public void onLowMemory() {
        MLog.d("生命周期");
        // 低内存的时候执行
        //ToastUtil.showCenter("onLowMemory");
        System.out.print("onLowMemory");
        lowMemory();
        super.onLowMemory();
    }
    @Override
    public void onTrimMemory(int level) {
        MLog.d("生命周期");
        // 程序在内存清理的时候执行
        //ToastUtil.showCenter("onTrimMemory");
        System.out.print("onTrimMemory");
        trimMemory(level);
        super.onTrimMemory(level);
    }
    public static AlarmManager getAlarm() {
        return mAlarm;
    }
    public static KeyguardManager getKeyguardMgr() {
        return mKeyguardMgr;
    }

    public abstract void terminate();
    public abstract void lowMemory();
    public abstract void trimMemory(int level);

}
