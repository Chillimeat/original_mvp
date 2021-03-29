package indi.ayun.original_mvp.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import indi.ayun.original_mvp.mlog.MLog;


public class BaseService extends Service {
    private UtilBase mUtilBase;
    @Override
    public void onCreate() {
        MLog.d("生命周期");
        super.onCreate();
        mUtilBase=new UtilBase();
        mUtilBase.setContext(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MLog.d("生命周期");
        return null;
    }
}
