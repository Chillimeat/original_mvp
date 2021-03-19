package indi.ayun.original_mvp.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @Description 默认Header创建器，上下文与刷新布局，返回刷新头部（RefreshHeader）接口
 * @Author Modify by ayun on 2021/3/18 15:18
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
