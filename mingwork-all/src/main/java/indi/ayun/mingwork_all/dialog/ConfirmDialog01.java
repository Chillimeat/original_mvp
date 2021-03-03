package indi.ayun.mingwork_all.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.base.BaseDialog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

public class ConfirmDialog01 extends BaseDialog {
    private Dialog mDialog;
    private static ConfirmDialog01 confirmDialog01;

    private TextView mClose,mOk,mTitle;
    private TextView mInputView;

    private String TAG;
    private String con, title;

    public ConfirmDialog01(String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_confirm01, null);
        run(layout);
    }

    public ConfirmDialog01(int resource,  String tag) {
        TAG = tag;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public ConfirmDialog01(int resource, String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    private void run(View layout) {
        mTitle = layout.findViewById(R.id.dialog_confirm_title);
        if (IsNothing.onAnything(title))mTitle.setText(title);
        mClose = layout.findViewById(R.id.dialog_confirm_close);
        mClose.setOnClickListener(new CloseDialog());
        mOk = layout.findViewById(R.id.dialog_confirm_ok);
        mOk.setOnClickListener(new Ok());
        mInputView = layout.findViewById(R.id.dialog_confirm_con);
        mInputView.setHint("请输入");
        if (IsNothing.onAnything(con))mInputView.setText(con);

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(false,0);
    }

    public static void show(String title, String con, String tag) {
        if (confirmDialog01 != null) {
            confirmDialog01.dismiss();
        }
        confirmDialog01 = null;
        confirmDialog01 = new ConfirmDialog01(title, con, tag);
        confirmDialog01.show();
    }

    public static void show(int resource,String tag) {
        if (confirmDialog01 != null) {
            confirmDialog01.dismiss();
        }
        confirmDialog01 = null;
        confirmDialog01 = new ConfirmDialog01(resource, tag);
        confirmDialog01.show();
    }

    public static void show(int resource, String title, String con, String tag) {
        if (confirmDialog01 != null) {
            confirmDialog01.dismiss();
        }
        confirmDialog01 = null;
        confirmDialog01 = new ConfirmDialog01(resource, title, con, tag);
        confirmDialog01.show();
    }


    class CloseDialog implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type", TAG+"_Close");
            getListener().onFragmentInteraction(bundle);
            dismiss();
        }
    }
    class Ok implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type", TAG+"_Ok");
            getListener().onFragmentInteraction(bundle);
            dismiss();
        }
    }
}
