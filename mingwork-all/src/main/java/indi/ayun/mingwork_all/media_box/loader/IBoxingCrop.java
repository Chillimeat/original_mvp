

package indi.ayun.mingwork_all.media_box.loader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import indi.ayun.mingwork_all.media_box.model.config.BoxingCropOption;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * Cropping interface.
 *
 * @author ChenSL
 */
public interface IBoxingCrop {

    /***
     * start crop operation.
     *
     * @param cropConfig  {@link BoxingCropOption}
     * @param path        the absolute path of media.
     * @param requestCode request code for the crop.
     */
    void onStartCrop(Context context, Fragment fragment, @NonNull BoxingCropOption cropConfig,
                     @NonNull String path, int requestCode);

    Uri onCropFinish(int resultCode, Intent data);
}
