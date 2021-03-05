package indi.ayun.original_mvp.recycleview;


import androidx.recyclerview.widget.GridLayoutManager;

public class SectionedColumnLookup extends GridLayoutManager.SpanSizeLookup{

    protected SectionedRecyclerViewAdapter<?, ?, ?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;

    public SectionedColumnLookup(SectionedRecyclerViewAdapter<?, ?, ?, ?, ?> adapter, GridLayoutManager layoutManager) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
    }

    @Override
    public int getSpanSize(int position) {
        if (adapter.hasHeader()) {//列表顶部有header
            if (position == 0) {
                return layoutManager.getSpanCount();
            } else if (position + 1 < adapter.getItemCount()) {
                if (adapter.isSectionHeaderPosition(position -1) || adapter.isSectionFooterPosition(position -1)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            } else {
                return layoutManager.getSpanCount();
            }
        } else {//列表顶部没有header
            if (position + 1 < adapter.getItemCount()) {
                if (adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition(position)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            } else {
                return layoutManager.getSpanCount();
            }
        }
    }
}
