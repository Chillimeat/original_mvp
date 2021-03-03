package indi.ayun.mingwork_all.base;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import indi.ayun.mingwork_all.MingWork_All;


public class UtilBase {
    private Context applicationContext;
    private Context context;
    private Fragment fragment;
    private Activity activity;

    public UtilBase(){
        applicationContext= MingWork_All.getContext();
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public Context getContext() {
        if (context==null)
        return applicationContext;
        else return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
