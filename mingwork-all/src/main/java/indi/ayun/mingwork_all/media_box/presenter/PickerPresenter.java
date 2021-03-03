

package indi.ayun.mingwork_all.media_box.presenter;

import android.content.ContentResolver;
import android.text.TextUtils;

import indi.ayun.mingwork_all.media_box.model.BoxingManager;
import indi.ayun.mingwork_all.media_box.model.callback.IAlbumTaskCallback;
import indi.ayun.mingwork_all.media_box.model.callback.IMediaTaskCallback;
import indi.ayun.mingwork_all.media_box.model.entity.AlbumEntity;
import indi.ayun.mingwork_all.media_box.model.entity.BaseMedia;
import indi.ayun.mingwork_all.media_box.model.entity.impl.ImageMedia;
import indi.ayun.mingwork_all.media_box.model.task.IMediaTask;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PickerPresenter implements PickerContract.Presenter {
    private PickerContract.View mTasksView;

    private int mTotalPage;
    private int mCurrentPage;
    private boolean mIsLoadingNextPage;

    private String mCurrentAlbumId;
    private LoadMediaCallback mLoadMediaCallback;
    private LoadAlbumCallback mLoadAlbumCallback;

    public PickerPresenter(PickerContract.View tasksView) {
        this.mTasksView = tasksView;
        this.mTasksView.setPresenter(this);
        this.mLoadMediaCallback = new LoadMediaCallback(this);
        this.mLoadAlbumCallback = new LoadAlbumCallback(this);
    }

    @Override
    public void loadMedias(int page, String albumId) {
        mCurrentAlbumId = albumId;
        if (page == 0) {
            mTasksView.clearMedia();
            mCurrentPage = 0;
        }
        ContentResolver cr = mTasksView.getAppCr();
        BoxingManager.getInstance().loadMedia(cr, page, albumId, mLoadMediaCallback);
    }

    @Override
    public void loadAlbums() {
        ContentResolver cr = mTasksView.getAppCr();
        BoxingManager.getInstance().loadAlbum(cr, mLoadAlbumCallback);
    }

    @Override
    public void destroy() {
        mTasksView = null;
    }

    @Override
    public boolean hasNextPage() {
        return mCurrentPage < mTotalPage;
    }

    @Override
    public boolean canLoadNextPage() {
        return !mIsLoadingNextPage;
    }

    @Override
    public void onLoadNextPage() {
        mCurrentPage++;
        mIsLoadingNextPage = true;
        loadMedias(mCurrentPage, mCurrentAlbumId);
    }

    @Override
    public void checkSelectedMedia(List<BaseMedia> allMedias, List<BaseMedia> selectedMedias) {
        if (allMedias == null || allMedias.size() == 0) {
            return;
        }
        Map<String, ImageMedia> map = new HashMap<>(allMedias.size());
        for (BaseMedia allMedia : allMedias) {
            if (!(allMedia instanceof ImageMedia)) {
                return;
            }
            ImageMedia media = (ImageMedia) allMedia;
            media.setSelected(false);
            map.put(media.getPath(), media);
        }
        if (selectedMedias == null || selectedMedias.size() < 0) {
            return;
        }
        for (BaseMedia media : selectedMedias) {
            if (map.containsKey(media.getPath())) {
                map.get(media.getPath()).setSelected(true);
            }
        }
    }

    private static class LoadMediaCallback implements IMediaTaskCallback<BaseMedia> {
        private WeakReference<PickerPresenter> mWr;

        LoadMediaCallback(PickerPresenter presenter) {
            mWr = new WeakReference<>(presenter);
        }

        private PickerPresenter getPresenter() {
            return mWr.get();
        }

        @Override
        public void postMedia(List<BaseMedia> medias, int count) {
            PickerPresenter presenter = getPresenter();
            if (presenter == null) {
                return;
            }
            PickerContract.View view = presenter.mTasksView;
            if (view != null) {
                view.showMedia(medias, count);
            }
            presenter.mTotalPage = count / IMediaTask.PAGE_LIMIT;
            presenter.mIsLoadingNextPage = false;
        }

        @Override
        public boolean needFilter(String path) {
            return TextUtils.isEmpty(path) || !(new File(path).exists());
        }
    }

    private static class LoadAlbumCallback implements IAlbumTaskCallback {
        private WeakReference<PickerPresenter> mWr;

        LoadAlbumCallback(PickerPresenter presenter) {
            mWr = new WeakReference<>(presenter);
        }

        private PickerPresenter getPresenter() {
            return mWr.get();
        }

        @Override
        public void postAlbumList(List<AlbumEntity> list) {
            PickerPresenter presenter = getPresenter();
            if (presenter == null) {
                return;
            }
            if (presenter.mTasksView != null) {
                presenter.mTasksView.showAlbum(list);
            }
        }
    }

}
