package indi.ayun.mingwork_all.recycleview.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import indi.ayun.mingwork_all.i.OnFragmentInteractionListener;

import indi.ayun.mingwork_all.recycleview.SectionedRecyclerViewAdapter;
import indi.ayun.mingwork_all.recycleview.old.ViewHolder;


import java.util.ArrayList;
import java.util.List;


public abstract class MoreMenuAdapter<T> extends SectionedRecyclerViewAdapter<ViewHolder, ViewHolder, ViewHolder, ViewHolder, ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas=new ArrayList<>();
    protected LayoutInflater mInflater;
    protected OnFragmentInteractionListener listener;
    protected int sectionConLayoutId,sectionHeaderLayoutId;

    public MoreMenuAdapter(List<T> datas, Context mContext, OnFragmentInteractionListener listener,int sectionHeaderLayoutId,int sectionConLayoutId,View emptyView) {
        this.mDatas = datas;
        this.mContext = mContext;
        this.listener=listener;
        mInflater = LayoutInflater.from(mContext);
        this.sectionConLayoutId=sectionConLayoutId;
        this.sectionHeaderLayoutId=sectionHeaderLayoutId;
        setEmptyView(emptyView);
    }

    public void setData(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return mDatas;
    }

    public void addMoreData(List<T> newDatas) {
        mDatas.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void setItemLayout(){

    }
    /**
     * 获取监听
     */
    public OnFragmentInteractionListener getFragmentListener() {
        return listener;
    }

    @Override
    protected boolean hasHeader() {
        return false;
    }

    @Override
    protected int getSectionCount() {
        return mDatas==null? 0 : mDatas.size();
    }

    protected abstract int getItemContainsCount(int section);

    @Override
    protected int getItemCountForSection(int section) {
        return getItemContainsCount(section);
    }

    @Override
    protected boolean hasSectionFooter(int section) {
        return false;
    }


    @Override
    protected ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(this.mContext, parent, this.sectionHeaderLayoutId);
//        this.onViewHolderCreated(viewHolder, viewHolder.getConvertView());
//        this.setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    protected ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    protected ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.createViewHolder(this.mContext, parent, this.sectionConLayoutId);
//        this.onViewHolderCreated(viewHolder, viewHolder.getConvertView());
//        this.setListener(parent, viewHolder, viewType);
        return viewHolder;
    }


    @Override
    protected ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }



    @Override
    protected void onBindHeaderViewHolder(ViewHolder holder) {

    }

    protected abstract void onBindTitleViewHolder(ViewHolder holder, int section);

    @Override
    protected void onBindSectionHeaderViewHolder(ViewHolder holder, int section) {
        onBindTitleViewHolder(holder,section);
    }

    protected abstract void onBindBodyHolder(ViewHolder holder, int section, int position);

    @Override
    protected void onBindItemViewHolder(ViewHolder holder, int section, int position) {
        onBindBodyHolder(holder,section,position);
    }

    @Override
    protected void onBindSectionFooterViewHolder(ViewHolder holder, int section) {

    }

    @Override
    protected void onBindFooterViewHolder(ViewHolder holder)  {

    }
}
