package indi.ayun.mingwork_all.smartrefresh.listener;

import androidx.annotation.NonNull;

import indi.ayun.mingwork_all.smartrefresh.api.RefreshLayout;

/**
 * 加载更多监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnLoadMoreListener {
    void onLoadMore(@NonNull RefreshLayout refreshLayout);
}
