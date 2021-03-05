

package indi.ayun.original_mvp.media_box.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author ChenSL
 */
public class BoxingExecutor {
    private static final BoxingExecutor INSTANCE = new BoxingExecutor();

    private ExecutorService mExecutorService;

    private BoxingExecutor() {
    }

    public static BoxingExecutor getInstance() {
        return INSTANCE;
    }

    public void runWorker(@NonNull Runnable runnable) {
        ensureWorkerHandlerNotNull();
        try {
            mExecutorService.execute(runnable);
        } catch (Exception e) {
            //MLog.d("runnable stop running unexpected. " + e.getMessage());
        }
    }

    @Nullable
    public FutureTask<Boolean> runWorker(@NonNull Callable<Boolean> callable) {
        ensureWorkerHandlerNotNull();
        FutureTask<Boolean> task = null;
        try {
            task = new FutureTask<>(callable);
            mExecutorService.submit(task);
            return task;
        } catch (Exception e) {
            //MLog.d("callable stop running unexpected. " + e.getMessage());
        }
        return task;
    }

    public void runUI(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
            return;
        }
        Handler handler = ensureUiHandlerNotNull();
        try {
            handler.post(runnable);
        } catch (Exception e) {
            //MLog.d("update UI task fail. " + e.getMessage());
        }
    }

    private void ensureWorkerHandlerNotNull() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
    }

    private Handler ensureUiHandlerNotNull() {
        return new Handler(Looper.getMainLooper());
    }

}
