package indi.ayun.original_mvp.media_box.impl.fresco;

import android.graphics.drawable.Drawable;

import com.facebook.imagepipeline.image.CloseableImage;

/**
 * Factory for instances of {@link AnimatedDrawable}.
 */
public interface AnimatedDrawableFactory {

    /**
     * Creates an {@link AnimatedDrawable} based on an {@link AnimatedImage}.
     *
     * @param closeableImage the result of the code
     * @return a newly constructed {@link Drawable}
     */
    Drawable create(CloseableImage closeableImage);


}
