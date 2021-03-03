package indi.ayun.mingwork_all.media_box.impl;

import androidx.annotation.DrawableRes;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.media_box.model.BoxingManager;

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
