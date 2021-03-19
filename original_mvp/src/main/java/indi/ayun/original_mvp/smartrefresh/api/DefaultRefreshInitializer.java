package indi.ayun.original_mvp.smartrefresh.api;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @Description 默认全局初始化器，上下文和刷新布局无返回。
 * @Author Modify by ayun on 2021/3/18 15:19
 */

public interface DefaultRefreshInitializer {
    void initialize(@NonNull Context context, @NonNull RefreshLayout layout);
}
