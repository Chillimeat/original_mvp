package indi.ayun.original_mvp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import indi.ayun.original_mvp.manager.ActivityMgr;
import indi.ayun.original_mvp.manager.ComFragmentMgr;
import indi.ayun.original_mvp.manager.FragmentListenerMgr;
import indi.ayun.original_mvp.media_box.BoxingMediaLoader;
import indi.ayun.original_mvp.media_box.impl.BoxingFrescoLoader;
import indi.ayun.original_mvp.media_box.loader.IBoxingMediaLoader;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.notices.NotificationChannels;
import indi.ayun.original_mvp.preference.OpCredential;
import indi.ayun.original_mvp.thread.ThreadExecutor;
import indi.ayun.original_mvp.utils.media.FrescoUtils;
import indi.ayun.original_mvp.utils.phone.Camera;

public class OriginalMVP {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mAppName;
    public static ActivityMgr activityMgr;
    public static FragmentListenerMgr fragmentListenerMgr;
    public static ComFragmentMgr comFragmentMgr;
    private static ThreadExecutor mThreadExecutor;
    public static OpCredential mOpCredential;

    private OriginalMVP() {
        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    /**
     * 初始化工具类
     * @param context 上下文
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void init(Context context,String appName) {
        mContext = context.getApplicationContext();
        mAppName=appName;
        mLogInit(true,false,null);
        activityMgr=ActivityMgr.getScreenManager();
        fragmentListenerMgr=FragmentListenerMgr.getScreenManager();
        comFragmentMgr = ComFragmentMgr.getComFragmentMgr();
        mThreadExecutor=ThreadExecutor.getInstance();
        mThreadExecutor.setExecutor(5,10,200);
        mOpCredential=OpCredential.getInstance().init(context);

        Camera.getInstance().init(mContext);
        FrescoUtils.initConfig(mContext);
        FrescoUtils.initDrawable(getContext().getResources());
        NotificationChannels.createAllNotificationChannels(mContext);
        IBoxingMediaLoader loader = new BoxingFrescoLoader(mContext);
        BoxingMediaLoader.getInstance().init(loader);
        //BoxingCrop.getInstance().init(new BoxingUcrop());
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (mContext != null) return mContext;
        throw new NullPointerException("you should init first");
    }

    public static String getAppName() {
        return mAppName;
    }

    public static void mLogInit(boolean isShowLog, boolean isShowSystem, @Nullable String tag){
        MLog.init(isShowLog,isShowSystem,tag);
    }

    public static void switchLog(boolean isShowLog) {
        MLog.switchLog(isShowLog);
    }

    public static void switchSystem(boolean isShowSystem){MLog.switchSystem(isShowSystem); }

    public static ThreadExecutor getThreadExecutor(){
        if (mThreadExecutor != null) return mThreadExecutor;
        throw new NullPointerException("you should init first");
    }

    public static void closeResource(){
        getThreadExecutor().shutdownNow();
    }

    public static OpCredential getOpCredential() {
        if (mOpCredential!=null)return mOpCredential;
        throw new NullPointerException("you should init first");
    }
}
