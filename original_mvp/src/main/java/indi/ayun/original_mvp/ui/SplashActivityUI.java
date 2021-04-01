package indi.ayun.original_mvp.ui;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import indi.ayun.original_mvp.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import indi.ayun.original_mvp.dialog.FirstMustNoteDialog;
import indi.ayun.original_mvp.mlog.MLog;
import indi.ayun.original_mvp.utils.app.IntentSkipUtil;
import indi.ayun.original_mvp.widgets.CountDownButton;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivityUI {
    private static final String TAG=SplashActivityUI.class.getSimpleName();

    private final Activity mContext;
    private Class<? extends Activity> skipActivity;
    private SimpleExoPlayer simpleExoPlayer;
    private SplashHelper splashHelper;
    //view
    private final View rootView;
    private TextView mComCountDownTV;
    private Button mComSkipBut;
    private SimpleDraweeView mComScreenImg,mSplashDefaultImg;
    private PlayerView videoPlay;

    //state
    private boolean init=false;

    /**
     * 初始化UI
     * @param context
     * @param root
     */
    public SplashActivityUI(Activity context, View root){
        this.mContext=context;
        this.rootView=root;
        findViews();
        mSplashDefaultImg.setVisibility(View.VISIBLE);
        mComCountDownTV.setVisibility(View.GONE);
        mComSkipBut.setVisibility(View.GONE);
        mComScreenImg.setVisibility(View.GONE);
        videoPlay.setVisibility(View.GONE);
        splashHelper = new SplashHelper(context);
        splashHelper.setAppFile();
        splashHelper.firstBasePermission();
    }

    /**
     * 获取SplashHelper对象，SplashActivityUI这里只负责UI方面工作
     * @return
     */
    public SplashHelper getSplashHelper() {
        if (splashHelper==null){
            splashHelper = new SplashHelper(mContext);
            splashHelper.setAppFile();
            splashHelper.firstBasePermission();
        }
        return splashHelper;
    }

    /**
     * 绑定控件
     */
    private void findViews(){
        if (init||rootView==null){
            return;
        }
        mSplashDefaultImg=rootView.findViewById(R.id.splash_default_img);
        mComCountDownTV=rootView.findViewById(R.id.splash_countDown);
        mComSkipBut=rootView.findViewById(R.id.comSkipBut);
        mComScreenImg =rootView.findViewById(R.id.splash_img);
        videoPlay=rootView.findViewById(R.id.splash_video);
        init=true;
    }

    /**
     * 是否立马跳转
     * @param isSkip
     * @param activity
     */
    public void onSkipNow(final Class<? extends Activity> activity,boolean isSkip){
        if (!isSkip){
            return;
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                IntentSkipUtil.skipFinish(mContext,activity);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1500);
    }


    /**
     * 跳转按钮
     * @param activity
     */
    public void onSkipBut(final Class<? extends Activity> activity){
        findViews();
        mComCountDownTV.setVisibility(View.GONE);
        mComSkipBut.setVisibility(View.VISIBLE);

        mComSkipBut.setVisibility(View.VISIBLE);
        mComSkipBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentSkipUtil.skipFinish(mContext,activity);
            }
        });
    }

    /**
     * 倒数跳转按钮
     * @param countDownString
     * @param finishString
     * @param max
     * @param interval
     * @param activity
     * @param isSkipBut
     */
    public void onCountdownBut(final String countDownString, final String finishString, final int max, final int interval, final Class<? extends Activity> activity,boolean isSkipBut){
        findViews();

        mComCountDownTV.setVisibility(View.VISIBLE);
        mComSkipBut.setVisibility(View.GONE);

        mComCountDownTV.setText(countDownString);
        mComCountDownTV.postDelayed(new Runnable() {
            @Override
            public void run() {
                CountDownButton helper = new CountDownButton(mComCountDownTV, countDownString,finishString, max, interval,R.color.white,R.color.white);
                helper.setOnFinishListener(new CountDownButton.OnFinishListener() {
                    @Override
                    public void finish() {
                        if (!isSkipBut) {
                            IntentSkipUtil.skipFinish(mContext, activity);
                        }else {
                            onSkipBut(activity);
                        }
                    }

                    @Override
                    public void onCountDown() {

                    }
                });
                helper.start();
            }
        },1000);

    }

    /**
     * 塞入背景图片地址
     * @param imgPath
     */
    public void onScreenImg(String imgPath){
        findViews();
        mComScreenImg.setVisibility(View.VISIBLE);
        videoPlay.setVisibility(View.GONE);
        mComScreenImg.setImageURI(Uri.parse(imgPath));
    }

    /**
     * 塞入背景图片
     */
    public void onScreenImg(int resId){
        findViews();
        mComScreenImg.setVisibility(View.VISIBLE);
        mComScreenImg.setImageResource(resId);
        videoPlay.setVisibility(View.GONE);
    }

    /**
     * 塞入背景动图地址
     * @param imgPath
     */
    public void onScreenGif(String imgPath){
        findViews();
        mComScreenImg.setVisibility(View.VISIBLE);
        videoPlay.setVisibility(View.GONE);
        ImageRequest ing= ImageRequestBuilder.newBuilderWithSource(Uri.parse(imgPath))
                .setProgressiveRenderingEnabled(true)
                .build();

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setUri(Uri.parse(imgPath))
                .setImageRequest(ing)
                .setAutoPlayAnimations(true)
                .setOldController(mComScreenImg.getController())
                .build();
        mComScreenImg.setController(draweeController);
    }

    /**
     * 播放视频的地址
     * @param imgPath
     * @param activity
     */
    public void onVideo(String imgPath,Class<? extends Activity> activity){
        skipActivity=activity;
        findViews();
        mComScreenImg.setVisibility(View.GONE);
        videoPlay.setVisibility(View.VISIBLE);

        simpleExoPlayer = new SimpleExoPlayer.Builder(mContext).build();
        //player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());
        //创建一个mp4媒体文件

        // 生成数据媒体实例，通过该实例加载媒体数据
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "livingclock"));
        // 创建资源
        Uri uri = Uri.parse(imgPath);
        MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        // 添加事件监听
        simpleExoPlayer.addListener(exoListener);
        videoPlay.setUseController(false);
        // 将播放器附加到view
        videoPlay.setPlayer(simpleExoPlayer);
        // 准备播放
        simpleExoPlayer.prepare(mediaSource);
        // 准备好了之后自动播放，如果已经准备好了，调用该方法实现暂停、开始功能
        simpleExoPlayer.setPlayWhenReady(true);
    }

    private final Player.DefaultEventListener exoListener = new Player.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            // 视频播放状态
            switch (playbackState) {
                case Player.STATE_IDLE:
                    // 空闲
                    MLog.d("空闲");
                    break;
                case Player.STATE_BUFFERING:
                    // 缓冲中
                    MLog.d("缓冲中");
                    break;
                case Player.STATE_READY:
                    // 准备好
                    MLog.d("准备好");
                    break;
                case Player.STATE_ENDED:
                    // 结束
                    MLog.d("结束");
                    IntentSkipUtil.skipFinish(mContext,skipActivity);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            // 报错
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
                    // 加载资源时出错
                    MLog.d("加载资源时出错");
                    break;
                case ExoPlaybackException.TYPE_RENDERER:
                    // 渲染时出错
                    MLog.d("渲染时出错");
                    break;
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    // 意外的错误
                    MLog.d("意外的错误");
                    break;
                case ExoPlaybackException.TYPE_OUT_OF_MEMORY:
                    break;
                case ExoPlaybackException.TYPE_REMOTE:
                    break;
            }
        }
    };

    /**
     * 释放视频资源
     */
    public void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void CountDownVisibility(int v){
        mComCountDownTV.setVisibility(v);
    }
    public void SkipButVisibility(int v){
        mComSkipBut.setVisibility(v);
    }
    public void ScreenImgVisibility(int v){
        mComScreenImg.setVisibility(v);
    }
    public void VideoPlayVisibility(int v){
        videoPlay.setVisibility(v);
    }


}
