package indi.ayun.original_mvp.smartrefresh.listener;

import androidx.annotation.NonNull;

import indi.ayun.original_mvp.smartrefresh.api.RefreshLayout;

/**
 * 刷新监听器
 * Created by SCWANG on 2017/5/26.
 */

public interface OnRefreshListener {
    void onRefresh(@NonNull RefreshLayout refreshLayout);
}
