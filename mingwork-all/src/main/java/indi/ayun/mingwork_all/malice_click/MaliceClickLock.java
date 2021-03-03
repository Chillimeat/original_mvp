package indi.ayun.mingwork_all.malice_click;


import android.os.Bundle;
import android.view.View;

import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.notice.ToastUtil;

import static java.lang.String.valueOf;

public class MaliceClickLock implements OnMaliceClick.OnLockClick {
    private OnMaliceClick.OnLockClickListener lockClickListener;
    private Bundle bundle;
    public MaliceClickLock(OnMaliceClick.OnLockClickListener lockClickListener) {
        this.lockClickListener = lockClickListener;
        bundle=new Bundle();//每个页面重新建立
    }

    @Override
    public void setLock(View v) {
        MLog.d("点击上锁监听:"+bundle.getBoolean(valueOf(v.getId()),true));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                MLog.d("点击上锁监听-点击传导:"+bundle.getBoolean(valueOf(v.getId()),true));
                if (bundle.getBoolean(String.valueOf(v.getId()),true)){
                    bundle.putBoolean(v.getId()+"",false);
                    lockClickListener.onGoodClick(v1);
                }else {
                    ToastUtil.showCenter("正在运行，不要瞎点");
                    lockClickListener.onMaliceClick(v1);
                }
            }
        });
        v.performClick();
    }

    @Override
    public void unLock(View v) {
        bundle.putBoolean(v.getId()+"",true);
    }
}
