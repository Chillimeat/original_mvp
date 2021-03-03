package indi.ayun.mingwork_all.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

public class BaseDialog {
    private static Activity mActivity;
    private static OnFragmentInteractionListener mListener;

    public static void init(Activity activity, OnFragmentInteractionListener listener){
        MLog.d(activity.getClass().getName());
        mActivity=activity;
        mListener=listener;
    }

    private Dialog dialog;
    private WindowManager.LayoutParams lp;
    public Dialog getDialog(View layout,int themeResId){
        dialog = new Dialog(mActivity, themeResId);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // you code here;
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        return dialog;
    }

    public void setWindow(boolean isTransparent,int resId){
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);


        if (isTransparent){
            window.setBackgroundDrawableResource(android.R.color.transparent);// 一句话搞定
        }else {
            lp = mActivity.getWindow().getAttributes();
            lp.alpha = 0.8f; //0.0-1.0
            mActivity.getWindow().setAttributes(lp);
        }

        if (IsNothing.onAnything(resId)){
            window.setWindowAnimations(resId);
        }
    }

    public void dismiss() {
        if (dialog==null)return;
        dialog.dismiss();
        if (lp!=null) {
            lp.alpha = 1.0f; //0.0-1.0
            mActivity.getWindow().setAttributes(lp);
        }
    }

    public void show() {
        if (dialog==null)return;
        if(!getActivity().isFinishing())
        dialog.show();
    }

    public Activity getActivity() {
        MLog.d(mActivity.getClass().getName());
        return mActivity;
    }

    public OnFragmentInteractionListener getListener() {
        return mListener;
    }
}

