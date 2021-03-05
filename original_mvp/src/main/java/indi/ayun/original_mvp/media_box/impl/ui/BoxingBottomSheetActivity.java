package indi.ayun.original_mvp.media_box.impl.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import indi.ayun.original_mvp.media_box.AbsBoxingActivity;
import indi.ayun.original_mvp.media_box.AbsBoxingViewFragment;
import indi.ayun.original_mvp.media_box.BoxingMediaLoader;
import indi.ayun.original_mvp.media_box.model.entity.BaseMedia;
import indi.ayun.original_mvp.media_box.model.entity.impl.ImageMedia;

import indi.ayun.original_mvp.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

public class BoxingBottomSheetActivity extends AbsBoxingActivity implements View.OnClickListener {
    private BottomSheetBehavior<FrameLayout> mBehavior;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxing_bottom_sheet);
        createToolbar();

        FrameLayout bottomSheet = (FrameLayout) findViewById(R.id.content_layout);
        mBehavior = BottomSheetBehavior.from(bottomSheet);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mImage = (ImageView) findViewById(R.id.media_result);
        mImage.setOnClickListener(this);
    }

    @NonNull
    @Override
    public AbsBoxingViewFragment onCreateBoxingView(ArrayList<BaseMedia> medias) {
        BoxingBottomSheetFragment fragment = (BoxingBottomSheetFragment) getSupportFragmentManager().findFragmentByTag(BoxingBottomSheetFragment.TAG);
        if (fragment == null) {
            fragment = BoxingBottomSheetFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, fragment, BoxingBottomSheetFragment.TAG).commit();
        }
        return fragment;
    }

    private void createToolbar() {
        Toolbar bar = (Toolbar) findViewById(R.id.nav_top_bar);
        setSupportActionBar(bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.boxing_default_album);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean hideBottomSheet() {
        if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            return true;
        }
        return false;
    }

    private boolean collapseBottomSheet() {
        if (mBehavior != null && mBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return true;
        }
        return false;
    }

    private void toggleBottomSheet() {
        if (mBehavior == null) {
            return;
        }
        if (mBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onBackPressed() {
        if (hideBottomSheet()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.media_result) {
            toggleBottomSheet();
        }
    }


    @Override
    public void onBoxingFinish(Intent intent, @Nullable List<BaseMedia> medias) {
        if (mImage != null && medias != null && !medias.isEmpty()) {
            ImageMedia imageMedia = (ImageMedia) medias.get(0);
            BoxingMediaLoader.getInstance().displayRaw(mImage, imageMedia.getPath(), 1080, 720, null);
        }
        hideBottomSheet();
    }
}
