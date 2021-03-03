package indi.ayun.mingwork_all.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


public class BaseService extends Service {
    private UtilBase mUtilBase;
    @Override
    public void onCreate() {
        super.onCreate();
        mUtilBase=new UtilBase();
        mUtilBase.setContext(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
