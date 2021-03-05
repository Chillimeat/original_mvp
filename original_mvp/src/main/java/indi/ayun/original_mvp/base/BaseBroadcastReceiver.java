package indi.ayun.original_mvp.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BaseBroadcastReceiver extends BroadcastReceiver {
    private UtilBase mUtilBase;
    /**
     * 1、BroadCastReceiver 的生命周期很短暂，当接收到广播的时候创建，当onReceive()方法结束后销毁
     *
     * 2、正因为BroadCastReceiver的声明周期很短暂，所以不要在广播接收器中去创建子线程做耗时的操作，因为广播接受者被销毁后，这个子进程就会成为空进程，很容易被杀死
     *
     * 3、因为BroadCastReceiver是运行在主线程的，所以不能直接在BroadCastReceiver中去做耗时的操作，否则就会出现ANR异常
     *
     * 耗时的较长的工作最好放到Service中去完成
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        mUtilBase=new UtilBase();
        mUtilBase.setContext(context);
    }
}
