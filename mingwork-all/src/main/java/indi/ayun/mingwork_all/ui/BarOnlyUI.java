package indi.ayun.mingwork_all.ui;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayun.mingwork_all.R;

import indi.ayun.mingwork_all.base.UtilBase;
import indi.ayun.mingwork_all.utils.verification.IsNothing;


public class BarOnlyUI extends UtilBase {
    private static final String TAG=BarOnlyUI.class.getSimpleName();

    private Activity mActivity;

    //view
    private View rootView;
    private View statusBar;
    private RelativeLayout barRelativeLayout;
    private ImageView BarLeftImg;
    private TextView BarLeftText;
    private TextView BarTitleText;
    private ImageView BarRlBgImg;
    private TextView BarRightText;
    private ImageView BarRightImg;
    //state
    private boolean init=false;

    //Listener
    public interface onBarOnClick{
        void Left();
        void Right();
    }


    public BarOnlyUI(Activity activity, View rootView){
        mActivity=activity;
        this.rootView=rootView;
    }

    private void findViews(){
        if (init||rootView==null){
            return;
        }
        statusBar=rootView.findViewById(R.id.StatusBarView);
        barRelativeLayout=rootView.findViewById(R.id.BarRl);
        BarLeftImg=rootView.findViewById(R.id.BarLeftIv);
        BarLeftText=rootView.findViewById(R.id.BarLeftTv);
        BarTitleText=rootView.findViewById(R.id.BarTitle);
        BarRlBgImg=rootView.findViewById(R.id.BarRlBg);
        BarRightText=rootView.findViewById(R.id.BarRightTv);
        BarRightImg=rootView.findViewById(R.id.BarRightIv);
        init=true;
    }

    /**
     *
     * @param color String #F5F5DC
     */
    public void setStatusBar(String color){
        findViews();
        statusBar.setVisibility(View.VISIBLE);
        statusBar.setBackgroundColor(Color.parseColor(color));

    }
    public void setStatusBar(int color){
        findViews();
        statusBar.setVisibility(View.VISIBLE);
        statusBar.setBackgroundColor(color);

    }
    /**
     *
     * @param color
     * @param titleText
     * @param titleColor
     * @param bgImgId
     */
    public void setBar(String color,String titleText,String titleColor,int bgImgId){
        findViews();
        if (IsNothing.onAnything(color)) {
            barRelativeLayout.setBackgroundColor(Color.parseColor(color));
        }
        if (IsNothing.onAnything(titleText)){
            BarTitleText.setVisibility(View.VISIBLE);
            BarTitleText.setText(titleText);
            BarTitleText.setTextColor(Color.parseColor(titleColor));
        }else BarTitleText.setVisibility(View.GONE);
        if (IsNothing.onAnything(bgImgId)){
            BarRlBgImg.setVisibility(View.VISIBLE);
            BarRlBgImg.setImageDrawable(mActivity.getResources().getDrawable(bgImgId));
        }else BarRlBgImg.setVisibility(View.GONE);
    }

    /**
     *
     * @param imgId
     * @param clickListener
     * @param leftTextStr
     * @param leftTextcolor
     */
    public void setBarLeft(int imgId, final onBarOnClick clickListener,String leftTextStr,String leftTextcolor){
        if (IsNothing.onAnything(imgId)){
            BarLeftImg.setVisibility(View.VISIBLE);
            BarLeftImg.setImageDrawable(mActivity.getResources().getDrawable(imgId));
            BarLeftImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.Left();
                }
            });
            if (IsNothing.onAnything(leftTextStr)){
                BarLeftText.setVisibility(View.VISIBLE);
                BarLeftText.setText(leftTextStr);
                BarLeftText.setTextColor(Color.parseColor(leftTextcolor));
            }else BarLeftText.setVisibility(View.GONE);

        }else {
            BarLeftImg.setVisibility(View.GONE);
            if (IsNothing.onAnything(leftTextStr)){
                BarLeftText.setVisibility(View.VISIBLE);
                BarLeftText.setText(leftTextStr);
                BarLeftText.setTextColor(Color.parseColor(leftTextcolor));
                BarLeftText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.Left();
                    }
                });
            }else BarLeftText.setVisibility(View.GONE);
        }
    }
    /**
     *
     * @param imgId
     * @param clickListener
     * @param rightTextStr
     * @param rightTextcolor
     */
    public void setBarRight(int imgId, final onBarOnClick clickListener,String rightTextStr,String rightTextcolor){
        if (IsNothing.onAnything(imgId)){
            BarRightImg.setVisibility(View.VISIBLE);
            BarRightImg.setImageDrawable(mActivity.getResources().getDrawable(imgId));
            BarRightImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.Right();
                }
            });
            if (IsNothing.onAnything(rightTextStr)){
                BarRightText.setVisibility(View.VISIBLE);
                BarRightText.setText(rightTextStr);
                BarRightText.setTextColor(Color.parseColor(rightTextcolor));
            }else BarRightText.setVisibility(View.GONE);
        }else{
            BarRightImg.setVisibility(View.GONE);
            if (IsNothing.onAnything(rightTextStr)){
                BarRightText.setVisibility(View.VISIBLE);
                BarRightText.setText(rightTextStr);
                BarRightText.setTextColor(Color.parseColor(rightTextcolor));
                BarRightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickListener.Right();
                    }
                });
            }else BarRightText.setVisibility(View.GONE);
        }

    }
}
