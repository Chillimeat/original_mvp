package indi.ayun.mingwork_all.dialog;

import android.app.Dialog;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.base.BaseDialog;

public class LoadDialog01 extends BaseDialog {
    private Dialog dialog;
    private static LoadDialog01 loadDialog01;
    public LoadDialog01(String msg) {
        LinearLayout layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_loading01, null);
        TextView tipTextView = (TextView) layout.findViewById(R.id.dialog_loading_tip);// 提示文字
        tipTextView.setText(msg);// 设置加载信息

        dialog=getDialog(layout, R.style.LoadingDialogStyle);
        setWindow(true,R.style.LoadingDialogAnimStyle);
    }

    public static LoadDialog01 show(String msg) {
        if (loadDialog01 != null) {
            loadDialog01.dismiss();
        }
        loadDialog01 = null;
        loadDialog01 = new LoadDialog01(msg);
        loadDialog01.show();
        return loadDialog01;
    }

}
