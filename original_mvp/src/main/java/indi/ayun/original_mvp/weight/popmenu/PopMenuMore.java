package indi.ayun.original_mvp.weight.popmenu;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import indi.ayun.original_mvp.R;

import java.util.ArrayList;
import java.util.List;


public class PopMenuMore {
    /**
     * 上下文.
     */
    private Context mContext;
    /**
     * 菜单项
     */
    private ArrayList<PopMenuMoreItem> mItemList;
    /**
     * 列表适配器.
     */
    private BaseAdapter mAdapter;
    /**
     * 菜单选择监听.
     */
    private OnItemSelectedListener mListener;
    /**
     * 下角图标
     */
    private ImageView cornerIcon;
    /**
     * 列表.
     */
    private ListView mListView;
    /**
     * 弹出窗口.
     */
    private PopupWindow mPopupWindow;

    public PopMenuMore(Context context) {
        mContext = context;
        mItemList = new ArrayList<>();

        View view = LayoutInflater.from(context).inflate(R.layout.popmenu_more, null);
        view.setFocusableInTouchMode(true);
        mAdapter = new PopMenuMoreAdapter(context, mItemList);
        cornerIcon = (ImageView) view.findViewById(R.id.pm_cornerimg);
        mListView = (ListView) view.findViewById(R.id.pm_listview);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopMenuMoreItem item = (PopMenuMoreItem) mAdapter.getItem(position);
                if (mListener != null) {
                    mListener.selected(view, item, position);
                }
                mPopupWindow.dismiss();
            }
        });
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));

        setBackgroundColor(context.getResources().getColor(R.color.white),1,context.getResources().getColor(R.color.line));
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
     * 设置下角图标
     *
     * @param resId
     */
    public void setCorner(int resId) {
        cornerIcon.setBackgroundResource(resId);
    }


    /**
     * 添加菜单项
     *
     * @param item
     */
    public void addItem(PopMenuMoreItem item) {
        mItemList.add(item);
        mAdapter.notifyDataSetChanged();
    }

    public void addItems(List<PopMenuMoreItem> items) {
        if (items != null) {
            mItemList.clear();
        }
        for (PopMenuMoreItem item : items) {
            mItemList.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 作为指定View的下拉控制显示.
     *
     * @param parent 所指定的View
     */
    public void showAsDropDown(View parent) {
        mPopupWindow.showAsDropDown(parent);
    }

    /**
     * 隐藏菜单.
     */
    public void dismiss() {
        mPopupWindow.dismiss();
    }
    /**
     * 当前菜单是否正在显示.
     *
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow.isShowing();
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
        void selected(View view, PopMenuMoreItem item, int position);
    }
}
