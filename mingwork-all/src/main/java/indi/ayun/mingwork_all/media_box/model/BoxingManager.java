

package indi.ayun.mingwork_all.media_box.model;

import android.content.ContentResolver;

import indi.ayun.mingwork_all.media_box.model.callback.IAlbumTaskCallback;
import indi.ayun.mingwork_all.media_box.model.callback.IMediaTaskCallback;
import indi.ayun.mingwork_all.media_box.model.config.BoxingConfig;
import indi.ayun.mingwork_all.media_box.model.task.IMediaTask;
import indi.ayun.mingwork_all.media_box.model.task.impl.AlbumTask;
import indi.ayun.mingwork_all.media_box.model.task.impl.ImageTask;
import indi.ayun.mingwork_all.media_box.model.task.impl.VideoTask;
import indi.ayun.mingwork_all.media_box.utils.BoxingExecutor;

import androidx.annotation.NonNull;

/**
 * The Manager to load {@link IMediaTask} and {@link AlbumTask}, holding {@link BoxingConfig}.
 *
 * @author ChenSL
 */
public class BoxingManager {
    private static final BoxingManager INSTANCE = new BoxingManager();

    private BoxingConfig mConfig;

    private BoxingManager() {
    }

    public static BoxingManager getInstance() {
        return INSTANCE;
    }

    public void setBoxingConfig(BoxingConfig config) {
        mConfig = config;
    }

    public BoxingConfig getBoxingConfig() {
        return mConfig;
    }

    public void loadMedia(@NonNull final ContentResolver cr, final int page,
                          final String id, @NonNull final IMediaTaskCallback callback) {
        final IMediaTask task = mConfig.isVideoMode() ? new VideoTask() : new ImageTask();
        BoxingExecutor.getInstance().runWorker(new Runnable() {
            @Override
            public void run() {
                task.load(cr, page, id, callback);
            }
        });

    }

    public void loadAlbum(@NonNull final ContentResolver cr, @NonNull final IAlbumTaskCallback callback) {
        BoxingExecutor.getInstance().runWorker(new Runnable() {

            @Override
            public void run() {
                new AlbumTask().start(cr, callback);
            }
        });

    }

}
