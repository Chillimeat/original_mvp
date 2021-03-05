package indi.ayun.original_mvp.interaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import indi.ayun.original_mvp.manager.ActivityMgr;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.notice.ToastUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 主要功能：App应用退出
 */
public class ExitBack {


    private static Boolean isExit = false;

    /**
     * 退出App程序应用
     *
     * @param context 上下文
     * @return boolean True退出|False提示
     */
    public static boolean exitApp(Context context,String tag) {
        Timer tExit = null;
        MLog.i("ExitBack-->>exitApp", tag + "");
        if (!isExit) {
            isExit = true;
            //信息提示
            ToastUtil.showShortToast("再按一次退出程序");
            //创建定时器
            tExit = new Timer();
            //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    //取消退出
                    isExit = false;
                }
            }, 2000);
        } else {
            ActivityMgr.getScreenManager().removeAllActivity();
            //创建ACTION_MAIN
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Context content = ((Activity) context);
            //启动ACTION_MAIN
            content.startActivity(intent);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        MLog.i("ExitBack-->>exitApp", isExit + "");
        MLog.i("ExitBack-->>exitApp", "最大内存：" + Runtime.getRuntime().maxMemory());
        MLog.i("ExitBack-->>exitApp", "占用内存：" + Runtime.getRuntime().totalMemory());
        MLog.i("ExitBack-->>exitApp", "空闲内存：" + Runtime.getRuntime().freeMemory());
        return isExit;
    }

    /**
     * 直接退出App程序应用
     *
     * @param context 上下文
     * @return boolean True退出|False提示
     */
    public static void directExitApp(Context context) {

        ActivityMgr.getScreenManager().removeAllActivity();
        //创建ACTION_MAIN
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Context content = ((Activity) context);
        //启动ACTION_MAIN
        content.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        MLog.i("ExitBack-->>exitApp", "最大内存：" + Runtime.getRuntime().maxMemory());
        MLog.i("ExitBack-->>exitApp", "占用内存：" + Runtime.getRuntime().totalMemory());
        MLog.i("ExitBack-->>exitApp", "空闲内存：" + Runtime.getRuntime().freeMemory());
    }
}
