package indi.ayun.original_mvp.recycleview.old;

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int type);

    int getItemViewType(int pos, T var2);
}
