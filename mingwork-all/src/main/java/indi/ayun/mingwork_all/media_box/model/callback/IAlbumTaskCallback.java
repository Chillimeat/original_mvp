

package indi.ayun.mingwork_all.media_box.model.callback;



import indi.ayun.mingwork_all.media_box.model.entity.AlbumEntity;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * A callback for load album.
 *
 * @author ChenSL
 */
public interface IAlbumTaskCallback {

    /**
     * get all album in database
     *
     * @param list album list
     */
    void postAlbumList(@Nullable List<AlbumEntity> list);

}
