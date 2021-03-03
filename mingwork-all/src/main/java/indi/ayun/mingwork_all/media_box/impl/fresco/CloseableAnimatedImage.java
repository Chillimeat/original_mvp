package indi.ayun.mingwork_all.media_box.impl.fresco;

import androidx.annotation.Nullable;

import com.facebook.imagepipeline.image.CloseableImage;

/**
 * Encapsulates the data needed in order for {@code AnimatedDrawable} to render a {@code
 * AnimatedImage}.
 */
public class CloseableAnimatedImage extends CloseableImage {

    private AnimatedImageResult mImageResult;

    private boolean mIsStateful;

    public CloseableAnimatedImage(AnimatedImageResult imageResult) {
        this(imageResult, true);
    }

    public CloseableAnimatedImage(AnimatedImageResult imageResult, boolean isStateful) {
        mImageResult = imageResult;
        mIsStateful = isStateful;
    }

    @Override
    public synchronized int getWidth() {
        return isClosed() ? 0 : mImageResult.getImage().getWidth();
    }

    @Override
    public synchronized int getHeight() {
        return isClosed() ? 0 : mImageResult.getImage().getHeight();
    }

    @Override
    public void close() {
        AnimatedImageResult imageResult;
        synchronized (this) {
            if (mImageResult == null) {
                return;
            }
            imageResult = mImageResult;
            mImageResult = null;
        }
        imageResult.dispose();
    }

    @Override
    public synchronized boolean isClosed() {
        return mImageResult == null;
    }

    @Override
    public synchronized int getSizeInBytes() {
        return isClosed() ? 0 : mImageResult.getImage().getSizeInBytes();
    }

    @Override
    public boolean isStateful() {
        return mIsStateful;
    }

    public synchronized AnimatedImageResult getImageResult() {
        return mImageResult;
    }

    public synchronized @Nullable
    AnimatedImage getImage() {
        return isClosed() ? null : mImageResult.getImage();
    }
}
