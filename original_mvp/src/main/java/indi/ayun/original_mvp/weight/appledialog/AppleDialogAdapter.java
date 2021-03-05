package indi.ayun.original_mvp.weight.appledialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import indi.ayun.original_mvp.R;

import java.util.ArrayList;

/**
 * @author SoBan
 * @create 2017/4/12 10:29.
 */

public class AppleDialogAdapter extends BaseAdapter {

    private ArrayList<AppleDialogItem> items;
    private Context context;

    public AppleDialogAdapter(Context context, ArrayList<AppleDialogItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public AppleDialogItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.apple_dialog_item, null);
            ViewHolder holder = new ViewHolder();
            holder.icon = (ImageView) view.findViewById(R.id.apple_dialog_item_icon);
            holder.text = (TextView) view.findViewById(R.id.apple_dialog_item_txt);
            holder.layout=view.findViewById(R.id.pop_apple_dialog_item_layout);
            view.setTag(holder);
        } else if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        AppleDialogItem item = items.get(position);
        if (item.getResId() == 0) {
            holder.icon.setVisibility(View.GONE);
        }else {
            holder.icon.setImageResource(item.getRescource());
        }
        holder.text.setText(item.getText());
        if (item.id==0){
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.apple_pop_b1));
        }else {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.apple_pop_b0));
        }
        return view;
    }

    private class ViewHolder {
        ImageView icon;
        TextView text;
        LinearLayout layout;
    }
}