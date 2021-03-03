package indi.ayun.mingwork_all.malice_click;

import android.view.View;

public interface OnMaliceClick{
    interface OnLockClickListener {
        void onMaliceClick(View v);
        void onGoodClick(View v);
    }

    interface OnLockClick {
        void setLock(View v);
        void unLock(View v);
    }
}