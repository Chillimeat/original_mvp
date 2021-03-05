package indi.ayun.original_mvp.mlog.base;

import android.widget.TextView;


public class UILog {
    private TextView mLog;
    public UILog(TextView mLog){
        this.mLog=mLog;
    }

    public void clearUILog() {
        this.mLog.setText("");
    }

    public void uiInMainLog(final String msg) {
        AsyncRun.runInMain(new Runnable() {
            @Override
            public void run() {
                mLog.append(msg);
                mLog.append("\r\n");
            }
        });

    }

    public void uiInMainLog(int delay,final String msg) {
        AsyncRun.runInMain(delay,new Runnable() {
            @Override
            public void run() {
                mLog.append(msg);
                mLog.append("\r\n");
            }
        });

    }

    public void uiInBackLog(final String msg) {
        AsyncRun.runInBack(new Runnable() {
            @Override
            public void run() {
                mLog.append(msg);
                mLog.append("\r\n");
            }
        });

    }

    public void uiInBackLog(int delay,final String msg) {
        AsyncRun.runInBack(delay,new Runnable() {
            @Override
            public void run() {
                mLog.append(msg);
                mLog.append("\r\n");
            }
        });

    }
}
