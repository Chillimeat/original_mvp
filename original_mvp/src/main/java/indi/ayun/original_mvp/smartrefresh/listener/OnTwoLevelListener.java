package indi.ayun.original_mvp.smartrefresh.listener;


import androidx.annotation.NonNull;

import indi.ayun.original_mvp.smartrefresh.api.RefreshLayout;

/**
 * @Description 二级刷新监听器 刷新布局返回布尔值
 * @Author Modify by ayun on 2021/3/18 15:19
 */
public interface OnTwoLevelListener {
    /**
     * 二级刷新触发
     * @param refreshLayout 刷新布局
     * @return true 将会展开二楼状态 false 关闭刷新
     */
    boolean onTwoLevel(@NonNull RefreshLayout refreshLayout);
}