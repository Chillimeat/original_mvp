package indi.ayun.mingwork_all.mlog.base;

import android.os.Handler;
import android.os.Looper;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by bailong on 14/10/22.
 */
public final class AsyncRun {

    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);

    public static void runInMain(Runnable r) {
        if (Looper.getMainLooper() == Looper.myLooper()){
            r.run();
        } else {
            mainThreadHandler.post(r);
        }
    }

    /**
     * delay: 任务执行前的延迟(毫秒)
     */
    public static void runInMain(int delay, final Runnable r){

        delayTimerTask(delay, new TimerTask() {
            @Override
            public void run() {
                mainThreadHandler.post(r);
                this.cancel();
            }
        });
    }

    public static void runInBack(Runnable r) {
       executorService.submit(r);
    }

    /**
     * delay:任务执行前的延迟(毫秒)
     */
    public static void runInBack(int delay,
                                 final Runnable r) {

        delayTimerTask(delay, new TimerTask() {
            @Override
            public void run() {
                executorService.submit(r);
                this.cancel();
            }
        });
    }

    private static void delayTimerTask(int delay, TimerTask timerTask){
        Timer timer = new Timer();
        timer.schedule(timerTask, delay);
    }

}

