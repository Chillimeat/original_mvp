package indi.ayun.original_mvp.recycleview.old;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import indi.ayun.original_mvp.i.OnFragmentInteractionListener;

import java.util.List;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    protected CommonAdapter.OnItemClickListener mOnItemClickListener;
    protected OnFragmentInteractionListener listener;

    public CommonAdapter(Context context, int layoutId, List<T> datas,OnFragmentInteractionListener listener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutId = layoutId;
        this.mDatas = datas;
        this.listener=listener;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(this.mContext, parent, this.mLayoutId);
        this.onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        this.setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        this.convert(holder, this.mDatas.get(position), position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {

    }

    protected boolean isEnabled(int viewType) {
        return true;
    }

    protected void setListener(ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (this.isEnabled(viewType)) {
            viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (CommonAdapter.this.mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        CommonAdapter.this.mOnItemClickListener.onItemClick(v, viewHolder, position);
                    }

                }
            });
            viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    if (CommonAdapter.this.mOnItemClickListener != null) {
                        int position = viewHolder.getAdapterPosition();
                        return CommonAdapter.this.mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public abstract void convert(ViewHolder var1, T var2, int var3);

    public int getItemCount() {
        int count = 0;
        if (this.mDatas != null) {
            count = this.mDatas.size();
        }
        return count;
    }

    public void setOnItemClickListener(CommonAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View var1, androidx.recyclerview.widget.RecyclerView.ViewHolder var2, int var3);
        boolean onItemLongClick(View var1, androidx.recyclerview.widget.RecyclerView.ViewHolder var2, int var3);
    }

    /**
     * 获取监听
     */
    public OnFragmentInteractionListener getFragmentListener() {
        return listener;
    }

}
