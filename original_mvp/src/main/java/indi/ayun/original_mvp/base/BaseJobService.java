package indi.ayun.original_mvp.base;

import android.app.job.JobParameters;
import android.app.job.JobService;

import indi.ayun.original_mvp.mlog.MLog;


public class BaseJobService extends JobService {
    private UtilBase mUtilBase;
    @Override
    public void onCreate() {
        MLog.d("生命周期");
        super.onCreate();
        mUtilBase=new UtilBase();
        mUtilBase=new UtilBase();
        mUtilBase.setContext(this);
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        MLog.d("生命周期");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        MLog.d("生命周期");
        return false;
    }
}
