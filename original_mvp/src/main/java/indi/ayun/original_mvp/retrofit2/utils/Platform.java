package indi.ayun.original_mvp.retrofit2.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Platform {
    public static boolean Use_Android_Platform = true;
    private static Platform PLATFORM;

    public Platform() {
    }

    public static Platform get() {
        if (PLATFORM == null) {
            PLATFORM = findPlatform();
        }

        //MLog.i(PLATFORM.getClass().toString());
        return PLATFORM;
    }

    private static Platform findPlatform() {
        if (Use_Android_Platform) {
            try {
                Class.forName("android.os.Build");
                if (Build.VERSION.SDK_INT != 0) {
                    return new Platform.Android();
                }
            } catch (ClassNotFoundException var1) {

            }

            return new Platform();
        } else {
            return new Platform();
        }
    }

    public Executor defaultCallbackExecutor() {
        return Executors.newCachedThreadPool();
    }

    public void execute(Runnable runnable) {
        this.defaultCallbackExecutor().execute(runnable);
    }

    static class Android extends Platform {
        Android() {
        }

        public Executor defaultCallbackExecutor() {
            return new Platform.Android.MainThreadExecutor();
        }

        static class MainThreadExecutor implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            MainThreadExecutor() {
            }

            public void execute(Runnable r) {
                this.handler.post(r);
            }
        }
    }
}
