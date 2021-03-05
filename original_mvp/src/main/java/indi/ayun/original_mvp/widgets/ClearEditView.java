package indi.ayun.original_mvp.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import indi.ayun.original_mvp.utils.transformation.DensityConvertUtil;

import androidx.annotation.RequiresApi;

/**
 * Created by ayun on 2018/6/14.
 */

@SuppressLint("AppCompatCustomView")
public class ClearEditView extends EditText {
    private int ICON_DIMEN= 50;

    public ClearEditView(Context context) {
        super(context);
    }
    public ClearEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDrawable(int d,int iconDimen){
        ICON_DIMEN=iconDimen;
        final Drawable drawable=getResources().getDrawable(d);//获取系统的×图片
        drawable.setBounds(0, 0, DensityConvertUtil.dip2px(ICON_DIMEN),DensityConvertUtil.dip2px(ICON_DIMEN));//设置绘制范围

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!TextUtils.isEmpty(s)) {
                    ClearEditView.this.setCompoundDrawables(null, null, drawable, null);
                    //ClearEditView.this.setCompoundDrawablePadding(DensityConvertUtil.dip2px(16));
                    ClearEditView.this.setPadding(DensityConvertUtil.dip2px(27),0,DensityConvertUtil.dip2px(27),0);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

        });
    }
    //为自定义EditText添加触摸事件

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            float x= event.getX();//点击的横坐标
            float y= event.getY();//点击的纵坐标
            int width = getWidth();//获取控件的宽度
            int height = getHeight();//获取控件的高度
            int top=(height-ICON_DIMEN)/2;
            int btm=top+ICON_DIMEN;
            if(x>=width-ICON_DIMEN-getPaddingRight()-20&&x<=width&&y>=0&&y<=height) {
                setText("");
            }
        }

        return super.onTouchEvent(event);

    }

    public ClearEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClearEditView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
