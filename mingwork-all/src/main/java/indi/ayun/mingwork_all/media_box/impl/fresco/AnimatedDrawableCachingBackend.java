package indi.ayun.mingwork_all.media_box.impl.fresco;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.facebook.common.references.CloseableReference;

public interface AnimatedDrawableCachingBackend extends AnimatedDrawableBackend {

    CloseableReference<Bitmap> getBitmapForFrame(int frameNumber);

    /**
     * Gets the bitmap for the preview frame. This will only return non-null if the
     * {@code ImageDecodeOptions} were configured to decode the preview frame.
     *
     * @return a reference to the preview bitmap which must be released by the caller when done or
     *    null if there is no preview bitmap set
     */
    CloseableReference<Bitmap> getPreviewBitmap();

    /**
     * Appends a string about the state of the backend that might be useful for debugging.
     *
     * @param sb the builder to append to
     */
    void appendDebugOptionString(StringBuilder sb);

    // Overridden to restrict the return type.
    @Override
    AnimatedDrawableCachingBackend forNewBounds(Rect bounds);
}
