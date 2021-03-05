package indi.ayun.original_mvp.recycleview.old;


import android.view.ViewGroup;
import androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class WrapperUtils {
    public WrapperUtils() {
    }

    public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, final WrapperUtils.SpanSizeCallback callback) {
        innerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

    }

    public static void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof LayoutParams) {
            LayoutParams p = (LayoutParams)lp;
            p.setFullSpan(true);
        }

    }

    public interface SpanSizeCallback {
        int getSpanSize(GridLayoutManager var1, GridLayoutManager.SpanSizeLookup var2, int var3);
    }
}
