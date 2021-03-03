package indi.ayun.mingwork_all;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import indi.ayun.mingwork_all.manager.ActivityMgr;
import indi.ayun.mingwork_all.manager.ComFragmentMgr;
import indi.ayun.mingwork_all.manager.FragmentListenerMgr;
import indi.ayun.mingwork_all.media_box.BoxingMediaLoader;
import indi.ayun.mingwork_all.media_box.impl.BoxingFrescoLoader;
import indi.ayun.mingwork_all.media_box.loader.IBoxingMediaLoader;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.notices.NotificationChannels;
import indi.ayun.mingwork_all.utils.media.FrescoUtils;
import indi.ayun.mingwork_all.utils.phone.Camera;

public class MingWork_All {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String mAppName;
    public static ActivityMgr activityMgr;
    public static FragmentListenerMgr fragmentListenerMgr;
    public static ComFragmentMgr comFragmentMgr;

    private MingWork_All() {
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

    public static void switchSystem(boolean isShowSystem){MLog.switchSystem(isShowSystem);
    }
}
