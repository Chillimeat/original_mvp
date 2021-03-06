package indi.ayun.original_mvp.media_box.impl;

import androidx.annotation.DrawableRes;

import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.media_box.model.BoxingManager;

public class BoxingResHelper {

    @DrawableRes
    public static int getMediaCheckedRes() {
        int result = BoxingManager.getInstance().getBoxingConfig().getMediaCheckedRes();
        return result > 0 ? result : R.drawable.ic_boxing_checked;
    }

    @DrawableRes
    public static int getMediaUncheckedRes() {
        int result = BoxingManager.getInstance().getBoxingConfig().getMediaUnCheckedRes();
        return result > 0 ? result : R.drawable.boxing_shape_unchecked;
    }

    @DrawableRes
    public static int getCameraRes() {
        return BoxingManager.getInstance().getBoxingConfig().getCameraRes();
    }
}
