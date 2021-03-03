package indi.ayun.mingwork_all.base;

import android.app.job.JobParameters;
import android.app.job.JobService;


public class BaseJobService extends JobService {
    private UtilBase mUtilBase;
    @Override
    public void onCreate() {
        super.onCreate();
        mUtilBase=new UtilBase();
        mUtilBase=new UtilBase();
        mUtilBase.setContext(this);
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
