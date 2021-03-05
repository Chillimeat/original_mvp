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

public class InputDialog01 extends BaseDialog {
    private Dialog mDialog;
    private static InputDialog01 inputDialog01;


    private TextView mClose, mOk, mTitle;
    private EditText mInputView;

    private String TAG;
    private String initial, title;

    public InputDialog01(String title, String initial, String tag) {
        TAG = tag;
        this.title = title;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_input01, null);
        run(layout);
    }

    public InputDialog01(int resource,  String initial, String tag) {
        TAG = tag;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    public InputDialog01(int resource, String title, String initial, String tag) {
        TAG = tag;
        this.title = title;
        this.initial = initial;
        RelativeLayout layout = (RelativeLayout) getActivity().getLayoutInflater().inflate(resource, null);
        run(layout);
    }

    private void run(View layout) {
        mTitle = layout.findViewById(R.id.dialog_inputOne_title);
        if (IsNothing.onAnything(title))mTitle.setText(title);
        mClose = layout.findViewById(R.id.dialog_inputOne_close);
        mClose.setOnClickListener(new CloseDialog());
        mOk = layout.findViewById(R.id.dialog_inputOne_ok);
        mOk.setOnClickListener(new Ok());
        mInputView = layout.findViewById(R.id.dialog_inputOne_con);
        mInputView.setHint("请输入");
        mInputView.setText(initial);

        mDialog = getDialog(layout,R.style.AppDialogStyle);
        setWindow(false,0);
    }

    public static void show(String title, String initial, String tag) {
        if (inputDialog01 != null) {
            inputDialog01.dismiss();
        }
        inputDialog01 = null;
        inputDialog01 = new InputDialog01(title, initial, tag);
        inputDialog01.show();
    }

    public static void show(int resource,String initial, String tag) {
        if (inputDialog01 != null) {
            inputDialog01.dismiss();
        }
        inputDialog01 = null;
        inputDialog01 = new InputDialog01(resource,initial, tag);
        inputDialog01.show();
    }

    public static void show(int resource, String title, String initial, String tag) {
        if (inputDialog01 != null) {
            inputDialog01.dismiss();
        }
        inputDialog01 = null;
        inputDialog01 = new InputDialog01(resource, title, initial, tag);
        inputDialog01.show();
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
