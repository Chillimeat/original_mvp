package indi.ayun.original_mvp.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.base.BaseDialog;
import indi.ayun.original_mvp.notice.ToastUtil;
import indi.ayun.original_mvp.utils.verification.IsNothing;

public class InputDialog02 extends BaseDialog {
    private Dialog mDialog;
    private static InputDialog02 inputDialog02;


    private TextView mClose, mOk, mTitle;
    private EditText mInputView;

    private String TAG;
    private String initial, title;

    public InputDialog02(String title, String initial, String tag) {
        TAG = tag;
        this.title = title;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_input02, null);
        run(layout);
    }

    public InputDialog02(int resource,  String initial, String tag) {
        TAG = tag;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public InputDialog02(int resource, String title, String initial, String tag) {
        TAG = tag;
        this.title = title;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    private void run(View layout) {

        mTitle=layout.findViewById(R.id.dialog_input2_title);
        if (IsNothing.onAnything(title))mTitle.setText(title);
        mClose=layout.findViewById(R.id.dialog_input2_close);
        mClose.setOnClickListener(new CloseDialog());
        mOk=layout.findViewById(R.id.dialog_input2_ok);
        mOk.setOnClickListener(new Ok());
        mInputView =layout.findViewById(R.id.dialog_input2_con);
        mInputView.setHint("请输入。。。");
        mInputView.setText(initial);

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(false,0);
    }

    public static void show(String title, String initial, String tag) {
        if (inputDialog02 != null) {
            inputDialog02.dismiss();
        }
        inputDialog02 = null;
        inputDialog02 = new InputDialog02(title, initial, tag);
        inputDialog02.show();
    }

    public static void show(int resource,String initial, String tag) {
        if (inputDialog02 != null) {
            inputDialog02.dismiss();
        }
        inputDialog02 = null;
        inputDialog02 = new InputDialog02(resource,initial, tag);
        inputDialog02.show();
    }

    public static void show(int resource, String title, String initial, String tag) {
        if (inputDialog02 != null) {
            inputDialog02.dismiss();
        }
        inputDialog02 = null;
        inputDialog02 = new InputDialog02(resource, title, initial, tag);
        inputDialog02.show();
    }

    class CloseDialog implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    class Ok implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (IsNothing.onAnything(mInputView.getText().toString().trim())) {
                if (mInputView.getText().toString().trim().equals(initial)) {
                    ToastUtil.showCenter("未修改");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", TAG);
                    bundle.putString("info", mInputView.getText().toString().trim());
                    getListener().onFragmentInteraction(bundle);
                    dismiss();
                }
            } else {
                ToastUtil.showCenter("修改内容不能为空");
            }
        }
    }
}
