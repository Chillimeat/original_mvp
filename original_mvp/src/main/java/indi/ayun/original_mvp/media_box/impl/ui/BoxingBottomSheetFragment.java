

package indi.ayun.original_mvp.media_box.impl.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import indi.ayun.original_mvp.R;

import indi.ayun.original_mvp.media_box.AbsBoxingViewFragment;
import indi.ayun.original_mvp.media_box.impl.adapter.BoxingMediaAdapter;
import indi.ayun.original_mvp.media_box.impl.view.HackyGridLayoutManager;
import indi.ayun.original_mvp.media_box.impl.view.SpacesItemDecoration;
import indi.ayun.original_mvp.media_box.model.BoxingManager;
import indi.ayun.original_mvp.media_box.model.entity.BaseMedia;
import indi.ayun.original_mvp.media_box.utils.BoxingFileHelper;

import java.util.ArrayList;
import java.util.List;


public class BoxingBottomSheetFragment extends AbsBoxingViewFragment implements View.OnClickListener {
    public static final String TAG = "com.bilibili.boxing_impl.ui.BoxingBottomSheetFragment";

    private static final int GRID_COUNT = 3;

    private boolean mIsCamera;

    private BoxingMediaAdapter mMediaAdapter;
    private ProgressDialog mDialog;
    private RecyclerView mRecycleView;
    private TextView mEmptyTxt;
    private ProgressBar mLoadingView;

    public static BoxingBottomSheetFragment newInstance() {
        return new BoxingBottomSheetFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMediaAdapter = new BoxingMediaAdapter(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_boxing_bottom_sheet, container, false);
    }

    @Override
    public boolean onAgainVisible(int num) {
        return true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyTxt = (TextView) view.findViewById(R.id.empty_txt);
        mRecycleView = (RecyclerView) view.findViewById(R.id.media_recycleview);
        mRecycleView.setHasFixedSize(true);
        mLoadingView = (ProgressBar) view.findViewById(R.id.loading);
        GridLayoutManager gridLayoutManager = new HackyGridLayoutManager(getActivity(), GRID_COUNT);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        mRecycleView.setLayoutManager(gridLayoutManager);
        mRecycleView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelOffset(R.dimen.boxing_media_margin), GRID_COUNT));
        mRecycleView.setAdapter(mMediaAdapter);
        mRecycleView.addOnScrollListener(new ScrollListener());
        mMediaAdapter.setOnMediaClickListener(new OnMediaClickListener());
        mMediaAdapter.setOnCameraClickListener(new OnCameraClickListener());
        view.findViewById(R.id.finish_txt).setOnClickListener(this);
    }



    @Override
    public void onCameraActivityResult(int requestCode, int resultCode) {
        showProgressDialog();
        super.onCameraActivityResult(requestCode, resultCode);
    }

    private void showProgressDialog() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(getActivity());
            mDialog.setIndeterminate(true);
            mDialog.setMessage(getString(R.string.boxing_handling));
        }
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
    }

    private void dismissProgressDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
            mDialog.dismiss();
        }
    }


    @Override
    public void showMedia(List<BaseMedia> medias, int count) {
        if (medias == null || isEmptyData(medias)
                && isEmptyData(mMediaAdapter.getAllMedias())) {
            showEmptyData();
            return;
        }
        showData();
        mMediaAdapter.addAllData(medias);
    }

    private boolean isEmptyData(List<BaseMedia> medias) {
        return medias.isEmpty() && !BoxingManager.getInstance().getBoxingConfig().isNeedCamera();
    }

    private void showEmptyData() {
        mEmptyTxt.setVisibility(View.VISIBLE);
        mRecycleView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void showData() {
        mLoadingView.setVisibility(View.GONE);
        mEmptyTxt.setVisibility(View.GONE);
        mRecycleView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCameraFinish(BaseMedia media) {
        dismissProgressDialog();
        mIsCamera = false;
        if (media != null) {
            List<BaseMedia> selectedMedias = mMediaAdapter.getSelectedMedias();
            selectedMedias.add(media);
            BoxingBottomSheetFragment.this.onFinish(selectedMedias);
        }
    }

    @Override
    public void onCameraError() {
        mIsCamera = false;
        dismissProgressDialog();
    }

    @Override
    public void startLoading() {
        loadMedias();
    }

    @Override
    public void onRequestPermissionError(String[] permissions, Exception e) {
        if (permissions.length > 0) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showEmptyData();
                Toast.makeText(getContext(), R.string.boxing_storage_permission_deny, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionSuc(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions[0].equals(STORAGE_PERMISSIONS[0])) {
            startLoading();
        }
    }


    @Override
    public void clearMedia() {
        mMediaAdapter.clearData();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.finish_txt == id) {
            onFinish(null);
        }
    }


    private class OnMediaClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ArrayList<BaseMedia> iMedias = new ArrayList<>();
            BaseMedia media = (BaseMedia) v.getTag();
            iMedias.add(media);
            onFinish(iMedias);
        }
    }

    private class OnCameraClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (!mIsCamera) {
                mIsCamera = true;
                startCamera(getActivity(), BoxingBottomSheetFragment.this, BoxingFileHelper.DEFAULT_SUB_DIR);
            }
        }
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();
            if (childCount > 0) {
                View lastChild = recyclerView.getChildAt(childCount - 1);
                RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
                int lastVisible = recyclerView.getChildAdapterPosition(lastChild);
                if (lastVisible == outerAdapter.getItemCount() - 1 && hasNextPage() && canLoadNextPage()) {
                    onLoadNextPage();
                }
            }
        }
    }

}
