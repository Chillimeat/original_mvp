package indi.ayun.mingwork_all.recycleview.old;

import android.content.Context;
import android.view.ViewGroup;

import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;

import java.util.List;

public abstract class MultiItemCommonAdapter<T> extends CommonAdapter<T> {
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    public MultiItemCommonAdapter(Context context, List<T> datas, MultiItemTypeSupport<T> multiItemTypeSupport, OnFragmentInteractionListener listener) {
        super(context, -1, datas,listener);
        this.mMultiItemTypeSupport = multiItemTypeSupport;
    }
    public int getItemViewType(int position) {
        return this.mMultiItemTypeSupport.getItemViewType(position, this.mDatas.get(position));
    }
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = this.mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder viewHolder = ViewHolder.createViewHolder(this.mContext, parent, layoutId);
        this.onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        this.setListener(parent, viewHolder, viewType);
        return viewHolder;
    }
}
