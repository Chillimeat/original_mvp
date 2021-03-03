package indi.ayun.mingwork_all.daemon;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class DaemonEnv {

    private DaemonEnv() {}

    public static final int DEFAULT_WAKE_UP_INTERVAL = 6 * 60 * 1000;
    private static final int MINIMAL_WAKE_UP_INTERVAL = 3 * 60 * 1000;

    static Context sApp;
    static Class<? extends AbsWorkService> sServiceClass;
    private static int sWakeUpInterval = DEFAULT_WAKE_UP_INTERVAL;
    static boolean sInitialized;

    static final Map<Class<? extends Service>, ServiceConnection> BIND_STATE_MAP = new HashMap<>();

    /**
     * @param app Application Context.
     * @param wakeUpInterval 定时唤醒的时间间隔(ms).
     */
    public static void initialize(@NonNull Context app, @NonNull Class<? extends AbsWorkService> serviceClass, @Nullable Integer wakeUpInterval) {
        sApp = app;
        sServiceClass = serviceClass;
        if (wakeUpInterval != null) sWakeUpInterval = wakeUpInterval;
        sInitialized = true;
    }

    public static void startServiceMayBind(@NonNull final Class<? extends Service> serviceClass) {
        if (!sInitialized) return;
        final Intent i = new Intent(sApp, serviceClass);
        i.putExtra("who","awaken");
        startServiceSafely(i,"0");
        ServiceConnection bound = BIND_STATE_MAP.get(serviceClass);
        if (bound == null) sApp.bindService(i, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BIND_STATE_MAP.put(serviceClass, this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                BIND_STATE_MAP.remove(serviceClass);
                startServiceSafely(i,"0");
                if (!sInitialized) return;
                sApp.bindService(i, this, Context.BIND_AUTO_CREATE);
            }
            
            @Override
            public void onBindingDied(ComponentName name) {
                onServiceDisconnected(name);
            }
        }, Context.BIND_AUTO_CREATE);
    }

    /**
     *
     * @param i
     * @param provoke 0为不是刺激是启动，1为刺激，需要开展工作。
     */
    public static void startServiceSafely(Intent i,String provoke) {
        if (!sInitialized) return;
        i.putExtra("provoke",provoke);
        try { sApp.startService(i); } catch (Exception ignored) {}
    }

    public static int getWakeUpInterval() {
        return Math.max(sWakeUpInterval, MINIMAL_WAKE_UP_INTERVAL);
    }
}
