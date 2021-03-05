package indi.ayun.original_mvp.media_box.impl.fresco;

public interface AnimatedImage {

    int LOOP_COUNT_INFINITE = 0;

    void dispose();

    /**
     * Gets the width of the image (also known as the canvas in WebP nomenclature).
     *
     * @return the width of the image
     */
    int getWidth();

    /**
     * Gets the height of the image (also known as the canvas in WebP nomenclature).
     *
     * @return the height of the image
     */
    int getHeight();

    /**
     * Gets the number of frames in the image.
     *
     * @return the number of frames in the image
     */
    int getFrameCount();

    /**
     * Gets the duration of the animated image.
     *
     * @return the duration of the animated image in milliseconds
     */
    int getDuration();

    /**
     * Gets the duration of each frame of the animated image.
     *
     * @return an array that is the size of the number of frames containing the duration of each frame
     *     in milliseconds
     */
    int[] getFrameDurations();

    /**
     * Gets the number of loops to run the animation for.
     *
     * @return the number of loops, or 0 to indicate infinite
     */
    int getLoopCount();


    AnimatedImageFrame getFrame(int frameNumber);


    boolean doesRenderSupportScaling();

    /**
     * Gets the size of bytes of the encoded image data (which is the data kept in memory for the
     * image).
     *
     * @return the size in bytes of the encoded image data
     */
    int getSizeInBytes();

    /**
     * Gets the frame info for the specified frame.
     *
     * @param frameNumber the frame to get the info for
     * @return the frame info
     */
    AnimatedDrawableFrameInfo getFrameInfo(int frameNumber);
}
