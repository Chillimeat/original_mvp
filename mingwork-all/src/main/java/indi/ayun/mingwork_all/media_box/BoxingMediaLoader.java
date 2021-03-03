

package indi.ayun.mingwork_all.media_box;

import android.widget.ImageView;

import indi.ayun.mingwork_all.media_box.loader.IBoxingCallback;
import indi.ayun.mingwork_all.media_box.loader.IBoxingMediaLoader;

import androidx.annotation.NonNull;

/**
 * A loader holding {@link IBoxingMediaLoader} to displayThumbnail medias.
 *
 * @author ChenSL
 */
public class BoxingMediaLoader {
    private static final BoxingMediaLoader INSTANCE = new BoxingMediaLoader();
    private IBoxingMediaLoader mLoader;

    private BoxingMediaLoader() {
    }

    public static BoxingMediaLoader getInstance() {
        return INSTANCE;
    }

    public void init(@NonNull IBoxingMediaLoader loader) {
        this.mLoader = loader;
    }

    public void displayThumbnail(@NonNull ImageView img, @NonNull String path, int width, int height) {
        if (ensureLoader()) {
            throw new IllegalStateException("init method should be called first");
        }
        mLoader.displayThumbnail(img, path, width, height);
    }

    public void displayRaw(@NonNull ImageView img, @NonNull String path, int width, int height, IBoxingCallback callback) {
        if (ensureLoader()) {
            throw new IllegalStateException("init method should be called first");
        }
        mLoader.displayRaw(img, path, width, height, callback);
    }

    public IBoxingMediaLoader getLoader() {
        return mLoader;
    }

    private boolean ensureLoader() {
        return mLoader == null;
    }
}
