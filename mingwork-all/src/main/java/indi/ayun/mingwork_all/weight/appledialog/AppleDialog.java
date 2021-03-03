package indi.ayun.mingwork_all.weight.appledialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ayun.mingwork_all.R;

import java.util.ArrayList;
import java.util.List;


public class AppleDialog {
    /**
     * 上下文.
     */
    private Activity mContext;
    /**
     * 菜单项
     */
    private ArrayList<AppleDialogItem> mItemList;
    /**
     * 列表适配器.
     */
    private AppleDialogAdapter mAdapter;
    /**
     * 菜单选择监听.
     */
    private OnItemSelectedListener mListener;
    /**
     * 列表.
     */
    private ListView mListView;
    /**
     * 弹出窗口.
     */
    private Dialog mDialog;
    private TextView close;
    private WindowManager.LayoutParams lp;
    private static AppleDialog appleDialog;

    public AppleDialog(Activity context) {
        mContext = context;
        mItemList = new ArrayList<>();

        View view = LayoutInflater.from(context).inflate(R.layout.apple_dialog, null);
        view.setFocusableInTouchMode(true);
        mAdapter = new AppleDialogAdapter(context, mItemList);
        mListView =  view.findViewById(R.id.apple_dialog_list);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppleDialogItem item = (AppleDialogItem) mAdapter.getItem(position);
                if (mListener != null) {
                    mListener.selected(view, item, position);
                }
                mDialog.dismiss();
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && mDialog.isShowing()) {
                    mDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        close=view.findViewById(R.id.apple_dialog_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mDialog = new Dialog(mContext, R.style.AppleDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        //setBackgroundColor(context.getResources().getColor(R.color.white),1,context.getResources().getColor(R.color.line));

        Window window = mDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);

        lp = context.getWindow().getAttributes();
        lp.alpha = 0.8f; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }

    /**
     * 设置ListView背景
     *
     * @param argb Color.parseColor("..")
     */
    public void setBackgroundColor(int argb,int strokeWidth,int strokeColor) {
        int roundRadius = 5; // 8dp 圆角半径
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(argb);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        mListView.setBackgroundDrawable(gd);
    }


    /**
     * 添加菜单项
     *
     * @param item
     */
    public void addItem(AppleDialogItem item) {
        mItemList.add(item);
        mAdapter.notifyDataSetChanged();
    }

    public void addItems(List<AppleDialogItem> items) {
        if (items != null) {
            mItemList.clear();
        }
        for (AppleDialogItem item : items) {
            mItemList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 作为指定View的下拉控制显示.
     */
    public void show() {
        if (appleDialog != null) {
            appleDialog.dismiss();
        }
        appleDialog = null;
        mDialog.show();
    }

    public static void show(Activity activity){

    }

    /**
     * 隐藏菜单.
     */
    public void dismiss() {
        mDialog.dismiss();
        lp.alpha = 1.0f; //0.0-1.0
        mContext.getWindow().setAttributes(lp);
    }
    /**
     * 当前菜单是否正在显示.
     *
     * @return
     */
    public boolean isShowing() {
        return mDialog.isShowing();
    }

    /**
     * 设置菜单选择监听.
     *
     * @param listener 监听器.
     */
    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

    /**
     * 菜单项选择监听接口.
     */
    public interface OnItemSelectedListener {
        /**
         * 菜单被选择时的回调接口.
         *
         * @param view     被选择的内容的View.
         * @param item     被选择的菜单项.
         * @param position 被选择的位置.
         */
        void selected(View view, AppleDialogItem item, int position);
    }
}
