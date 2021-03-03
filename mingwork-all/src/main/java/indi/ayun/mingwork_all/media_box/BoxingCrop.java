
package indi.ayun.mingwork_all.media_box;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import indi.ayun.mingwork_all.media_box.loader.IBoxingCrop;
import indi.ayun.mingwork_all.media_box.model.config.BoxingCropOption;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A loader holding {@link IBoxingCrop} to crop images.
 *
 * @author ChenSL
 */
public class BoxingCrop {
    private static final BoxingCrop INSTANCE = new BoxingCrop();
    private IBoxingCrop mCrop;

    private BoxingCrop() {
    }

    public static BoxingCrop getInstance() {
        return INSTANCE;
    }

    public void init(@NonNull IBoxingCrop loader) {
        this.mCrop = loader;
    }

    public void onStartCrop(Activity activity, Fragment fragment, @NonNull BoxingCropOption cropConfig,
                            @NonNull String path, int requestCode) {
        if (ensureLoader()) {
            throw new IllegalStateException("init method should be called first");
        }
        if (cropConfig == null) {
            throw new IllegalArgumentException("crop config is null.");
        }
        mCrop.onStartCrop(activity, fragment, cropConfig, path, requestCode);
    }

    public Uri onCropFinish(int resultCode, Intent data) {
        if (ensureLoader()) {
            throw new IllegalStateException("init method should be called first");
        }
        return mCrop.onCropFinish(resultCode, data);
    }

    public IBoxingCrop getCrop() {
        return mCrop;
    }

    private boolean ensureLoader() {
        return mCrop == null;
    }
}
