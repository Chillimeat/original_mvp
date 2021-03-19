package indi.ayun.original_mvp.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;
/**
 * @Description 底部刷新得默认创造者,上下文与刷新布局，返回刷新底部（RefreshFooter）接口
 * @Author Modify by ayun on 2021/3/18 15:16
 */
public interface DefaultRefreshFooterCreator {

    @NonNull
    RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout);
}
