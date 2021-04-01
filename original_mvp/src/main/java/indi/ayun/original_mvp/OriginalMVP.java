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
import indi.ayun.original_mvp.preference.OpBaseSetting;
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
        OpCredential.getInstance().init(context);
        OpBaseSetting.getInstance().init(context);

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

    /**
     * 获取项目名称
     * @return
     */
    public static String getAppName() {
        return mAppName;
    }

    /**
     * log得基本初始化，写在该类得初始化中
     * @param isShowLog
     * @param isShowSystem
     * @param tag
     */
    public static void mLogInit(boolean isShowLog, boolean isShowSystem, @Nullable String tag){
        MLog.init(isShowLog,isShowSystem,tag);
    }

    /**
     * log得开关
     * @param isShowLog
     */
    public static void switchLog(boolean isShowLog) {
        MLog.switchLog(isShowLog);
    }

    /**
     * 系统log开关
     * @param isShowSystem
     */
    public static void switchSystem(boolean isShowSystem){MLog.switchSystem(isShowSystem); }

    /**
     * 线程池
     * @return
     */
    public static ThreadExecutor getThreadExecutor(){
        if (mThreadExecutor != null) return mThreadExecutor;
        throw new NullPointerException("you should init first");
    }

    /**
     * 清空资源
     */
    public static void closeResource(){
        getThreadExecutor().shutdownNow();
    }

    public static void initOpBaseSetting(int httpPort,int udpPort,int successCode,String domain,String cloudIP,String inetAddress){
        OpBaseSetting.getInstance().saveCloudIP(cloudIP);
        OpBaseSetting.getInstance().saveDomain(domain);
        OpBaseSetting.getInstance().saveHttp_port(httpPort);
        OpBaseSetting.getInstance().saveUdp_port(udpPort);
        OpBaseSetting.getInstance().saveSuccessCode(successCode);
        OpBaseSetting.getInstance().saveInetAddress(inetAddress);
    }
}
