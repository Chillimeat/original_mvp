package indi.ayun.mingwork_all.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.base.BaseDialog;
import indi.ayun.mingwork_all.interaction.ExitBack;
import indi.ayun.mingwork_all.mlog.MLog;
import indi.ayun.mingwork_all.utils.verification.IsNothing;

public class FirstMustNoteDialog extends BaseDialog {
    private Dialog mDialog;
    private static FirstMustNoteDialog firstMustNoteDialog;

    private TextView conView,titleView;
    private Button buttonYes,buttonNo;

    private String TAG;
    private String title, con;
    private SpannableStringBuilder stringBuilder;

    public FirstMustNoteDialog(String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_first_must_note, null);
        run(layout);
    }

    public FirstMustNoteDialog(String title, SpannableStringBuilder stringBuilder, String tag) {
        TAG = tag;
        this.title = title;
        this.stringBuilder = stringBuilder;
        MLog.d(TAG+"; \n"+title+"; \n"+stringBuilder.toString()+"; \n"+getActivity());
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_first_must_note, null);
        run(layout);
    }

    public FirstMustNoteDialog(int resource, String tag) {
        TAG = tag;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public FirstMustNoteDialog(int resource, String title, String con, String tag) {
        TAG = tag;
        this.title = title;
        this.con = con;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public FirstMustNoteDialog(int resource, String title, SpannableStringBuilder stringBuilder, String tag) {
        TAG = tag;
        this.title = title;
        this.stringBuilder = stringBuilder;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }


    private void run(View layout) {
        buttonNo = layout.findViewById(R.id.dialog_firstMustNote_no);
        buttonNo.setOnClickListener(new CloseDialog());
        conView = layout.findViewById(R.id.dialog_firstMustNote_con);
        conView.setMovementMethod(LinkMovementMethod.getInstance());
        if (IsNothing.onAnything(con))conView.setText(con);
        if (stringBuilder!=null)conView.setText(stringBuilder);
        titleView = layout.findViewById(R.id.dialog_firstMustNote_txt);
        titleView.setText(title);
        buttonYes=layout.findViewById(R.id.dialog_firstMustNote_yes);
        buttonYes.setOnClickListener(new Ok());

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(true,0);
    }

    public static void show(String title, String con, String tag) {
        if (firstMustNoteDialog != null) {
            firstMustNoteDialog.dismiss();
        }
        firstMustNoteDialog = null;
        firstMustNoteDialog = new FirstMustNoteDialog(title, con, tag);
        firstMustNoteDialog.show();
    }

    public static void show(String title, SpannableStringBuilder stringBuilder, String tag) {
        if (firstMustNoteDialog != null) {
            firstMustNoteDialog.dismiss();
        }
        firstMustNoteDialog = null;
        firstMustNoteDialog = new FirstMustNoteDialog(title, stringBuilder, tag);
        firstMustNoteDialog.show();
    }

    public static void show(int resource,String tag) {
        if (firstMustNoteDialog != null) {
            firstMustNoteDialog.dismiss();
        }
        firstMustNoteDialog = null;
        firstMustNoteDialog = new FirstMustNoteDialog(resource, tag);
        firstMustNoteDialog.show();
    }

    public static void show(int resource, String title, String con, String tag) {
        if (firstMustNoteDialog != null) {
            firstMustNoteDialog.dismiss();
        }
        firstMustNoteDialog = null;
        firstMustNoteDialog = new FirstMustNoteDialog(resource, title, con, tag);
        firstMustNoteDialog.show();
    }

    public static void show(int resource, String title,SpannableStringBuilder stringBuilder, String tag) {
        if (firstMustNoteDialog != null) {
            firstMustNoteDialog.dismiss();
        }
        firstMustNoteDialog = null;
        firstMustNoteDialog = new FirstMustNoteDialog(resource, title, stringBuilder, tag);
        firstMustNoteDialog.show();
    }

    public static boolean isShow(){
        if (firstMustNoteDialog != null) {
            return true;
        }
        return false;
    }

    class CloseDialog implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            dismiss();
            ExitBack.directExitApp(getActivity());
        }
    }
    class Ok implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("type", TAG);
            getListener().onFragmentInteraction(bundle);
            dismiss();
        }
    }

}
