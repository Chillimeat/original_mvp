package indi.ayun.original_mvp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.base.BaseDialog;
import indi.ayun.original_mvp.utils.verification.IsNothing;

public class NoteTxtDialog extends BaseDialog {
    private Dialog mDialog;
    private static NoteTxtDialog noteTxtDialog;

    private TextView mClose,mOk,mTitle;
    private TextView noteView;

    private String TAG;
    private String con, title;

    public NoteTxtDialog(String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_note_txt01, null);
        run(layout);
    }

    public NoteTxtDialog(int resource,  String tag) {
        TAG = tag;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public NoteTxtDialog(int resource, String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    private void run(View layout) {
        mTitle = layout.findViewById(R.id.dialog_noteTxt_title);
        if (IsNothing.onAnything(title))mTitle.setText(title);
        mClose = layout.findViewById(R.id.dialog_noteTxt_close);
        mClose.setOnClickListener(new CloseDialog());
        mOk = layout.findViewById(R.id.dialog_noteTxt_ok);
        mOk.setOnClickListener(new Ok());
        noteView = layout.findViewById(R.id.dialog_noteTxt_con);
        noteView.setHint("请输入");
        if (IsNothing.onAnything(con)) noteView.setText(con);

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(false,0);
    }

    public static void show(String title, String con, String tag) {
        if (noteTxtDialog != null) {
            noteTxtDialog.dismiss();
        }
        noteTxtDialog = null;
        noteTxtDialog = new NoteTxtDialog(title, con, tag);
        noteTxtDialog.show();
    }

    public static void show(int resource,String tag) {
        if (noteTxtDialog != null) {
            noteTxtDialog.dismiss();
        }
        noteTxtDialog = null;
        noteTxtDialog = new NoteTxtDialog(resource, tag);
        noteTxtDialog.show();
    }

    public static void show(int resource, String title, String con, String tag) {
        if (noteTxtDialog != null) {
            noteTxtDialog.dismiss();
        }
        noteTxtDialog = null;
        noteTxtDialog = new NoteTxtDialog(resource, title, con, tag);
        noteTxtDialog.show();
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
