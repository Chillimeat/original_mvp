

package indi.ayun.original_mvp.media_box.model.task.impl;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import indi.ayun.original_mvp.media_box.model.callback.IMediaTaskCallback;
import indi.ayun.original_mvp.media_box.model.entity.impl.VideoMedia;
import indi.ayun.original_mvp.media_box.model.task.IMediaTask;
import indi.ayun.original_mvp.media_box.utils.BoxingExecutor;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

/**
 * A Task to load {@link VideoMedia} in database.
 *
 * @author ChenSL
 */
@WorkerThread
public class VideoTask implements IMediaTask<VideoMedia> {

    private final static String[] MEDIA_COL = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.DURATION
    };


    @Override
    public void load(final ContentResolver cr, final int page, String id, final IMediaTaskCallback<VideoMedia> callback) {
        loadVideos(cr, page, callback);
    }

    private void loadVideos(ContentResolver cr, int page, @NonNull final IMediaTaskCallback<VideoMedia> callback) {
        final List<VideoMedia> videoMedias = new ArrayList<>();
        final Cursor cursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, MEDIA_COL, null, null,
                MediaStore.Images.Media.DATE_MODIFIED + " desc" + " LIMIT " + page * IMediaTask.PAGE_LIMIT + " , " + IMediaTask.PAGE_LIMIT);
        try {
            int count = 0;
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getCount();
                do {
                    String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                    String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID));
                    String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                    String type = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE));
                    String size = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                    String date = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));
                    String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                    VideoMedia video = new VideoMedia.Builder(id, data).setTitle(title).setDuration(duration)
                            .setSize(size).setDataTaken(date).setMimeType(type).build();
                    videoMedias.add(video);

                } while (!cursor.isLast() && cursor.moveToNext());
                postMedias(callback, videoMedias, count);
            } else {
                postMedias(callback, videoMedias, 0);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    private void postMedias(@NonNull final IMediaTaskCallback<VideoMedia> callback,
                            final List<VideoMedia> videoMedias, final int count) {
        BoxingExecutor.getInstance().runUI(new Runnable() {
            @Override
            public void run() {
                callback.postMedia(videoMedias, count);
            }
        });
    }

}
