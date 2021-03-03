package indi.ayun.mingwork_all.recycleview.old;

import android.view.View;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat();
    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterWrapper(RecyclerView.Adapter adapter) {
        this.mInnerAdapter = adapter;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (this.mHeaderViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(),
                    (View) this.mHeaderViews
                            .get(viewType));

            return holder;
        }
        if (this.mFootViews.get(viewType) != null) {
            ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(),
                    (View) this.mFootViews
                            .get(viewType));

            return holder;
        }
        return this.mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position))
            return this.mHeaderViews.keyAt(position);
        if (isFooterViewPos(position)) {
            return this.mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return this.mInnerAdapter.getItemViewType(position - getHeadersCount());
    }

    private int getRealItemCount() {
        return this.mInnerAdapter.getItemCount();
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        this.mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
    }

    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(this.mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = HeaderAndFooterWrapper.this.getItemViewType(position);
                if (HeaderAndFooterWrapper.this.mHeaderViews.get(viewType) != null)
                    return layoutManager.getSpanCount();
                if (HeaderAndFooterWrapper.this.mFootViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                    return oldLookup.getSpanSize(position);
                return 1;
            }
        });
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        this.mInnerAdapter.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if ((isHeaderViewPos(position)) || (isFooterViewPos(position)))
            WrapperUtils.setFullSpan(holder);
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    public void addHeaderView(View view) {
        this.mHeaderViews.put(this.mHeaderViews.size() + 100000, view);
    }

    public void addFootView(View view) {
        this.mFootViews.put(this.mFootViews.size() + 200000, view);
    }

    public int getHeadersCount() {
        return this.mHeaderViews.size();
    }

    public int getFootersCount() {
        return this.mFootViews.size();
    }
}