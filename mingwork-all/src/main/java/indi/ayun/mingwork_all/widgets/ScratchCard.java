package indi.ayun.mingwork_all.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ayun.mingwork_all.R;

/**
 * @Description:主要功能: 自定义刮刮乐
 */

public class ScratchCard extends View {

    //手指触摸屏幕，
    private Paint paint;
    private Path path;
    //隐藏的背景图片
    private Bitmap bgBitmap;
    private Canvas bgCanvas;
    //覆盖在上层的灰色图层
    private Bitmap fgBitmap;
    private Canvas fgCanvas;

    private Drawable surfaceDrawable;
    private Drawable bottomDrawable;
    private int surfaceColor;
    private int mMHeight,mMWidth;
    public ScratchCard(Context context) {
        super(context);
        initPaint(context,null,0);
    }
    public ScratchCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context,attrs,0);
    }
    public ScratchCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context,attrs,defStyleAttr);
    }

    private void initPaint(Context context,AttributeSet attrs,int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScratchCard);
        surfaceColor = a.getColor(R.styleable.ScratchCard_surfaceColor, 0XD8D8D8D8);
        surfaceDrawable=a.getDrawable(R.styleable.ScratchCard_surfaceImage);
        mMHeight=a.getLayoutDimension(R.styleable.ScratchCard_android_layout_height,1);
        mMWidth=a.getLayoutDimension(R.styleable.ScratchCard_android_layout_width,1);
        a.recycle();


        paint = new Paint();
        paint.setAlpha(0);
        //在已有的图像上绘图将会在其上面添加一层新的图层，如果新图层的paint是不透明的,那么它将遮挡住下面的paint；
        //如果新图层它是部分透明的,那么它不透明的地方将会被染上下面的颜色
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setStyle(Paint.Style.STROKE);
        //设置笔触和连接处能更加圆滑
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(60);
        path = new Path();

        Resources resources=getResources();
        bgBitmap = Bitmap.createBitmap(mMWidth, mMHeight, Bitmap.Config.ARGB_8888);
        bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawColor(0Xffffffff);

        fgBitmap = Bitmap.createBitmap(mMWidth, mMHeight, Bitmap.Config.ARGB_8888);
        fgCanvas = new Canvas(fgBitmap);
        //在图层上绘制一层
        if (null!=surfaceDrawable){
            surfaceDrawable.setBounds(0,0,mMWidth,mMHeight);
            surfaceDrawable.draw(fgCanvas);
        }else {
            fgCanvas.drawColor(surfaceColor);
        }
    }
    //设置底部图片
    public void setBottomDrawableId(int bottomDrawableId) {
        Resources resources=getResources();
        bottomDrawable=resources.getDrawable(bottomDrawableId);
        bgCanvas = new Canvas(bgBitmap);
        bottomDrawable.setBounds(0,0,mMWidth,mMHeight);
        bottomDrawable.draw(bgCanvas);
    }
    //设置底部颜色
    public void setBottomColor(int bottomColor) {
        //bgCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        bgCanvas = new Canvas(bgBitmap);
        bgCanvas.drawColor(bottomColor);
    }
    //设置底部图片,位置
    public void setBottomDrawable(int bottomDrawableId,int left,int top,int right,int bottom) {
        Resources resources=getResources();
        bottomDrawable=resources.getDrawable(bottomDrawableId);
        bgBitmap = Bitmap.createBitmap(mMWidth, mMHeight, Bitmap.Config.ARGB_8888);
        bgCanvas = new Canvas(bgBitmap);
        bottomDrawable.setBounds(left,top,right,bottom);
        bottomDrawable.draw(bgCanvas);
    }

    //设置顶部图片
    public void setSurfaceDrawableId(int surfaceDrawableId) {
        Resources resources=getResources();
        surfaceDrawable=resources.getDrawable(surfaceDrawableId);
        fgCanvas = new Canvas(fgBitmap);
        surfaceDrawable.setBounds(0,0,mMWidth,mMHeight);
        surfaceDrawable.draw(fgCanvas);
    }
    //设置顶部颜色
    public void setSurfaceColor(int surfaceColor) {
        fgCanvas = new Canvas(fgBitmap);
        fgCanvas.drawColor(surfaceColor);
    }
    //设置顶部图片,位置
    public void setSurfaceDrawable(int surfaceDrawableId,int left,int top,int right,int bottom) {
        Resources resources=getResources();
        surfaceDrawable=resources.getDrawable(surfaceDrawableId);
        fgBitmap = Bitmap.createBitmap(mMWidth, mMHeight, Bitmap.Config.ARGB_8888);
        fgCanvas = new Canvas(fgBitmap);
        surfaceDrawable.setBounds(left,top,right,bottom);
        surfaceDrawable.draw(fgCanvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景图层
        canvas.drawBitmap(bgBitmap, 0, 0, null);
        //绘制遮罩图层
        canvas.drawBitmap(fgBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(event.getX(), event.getY());
                //实现点击檫除
                path.lineTo(event.getX() + 1, event.getY() + 1);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        fgCanvas.drawPath(path, paint);
        invalidate();
        return true;

    }
}
