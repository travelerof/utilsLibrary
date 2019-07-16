package com.hyg.utilslibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyg.utilslibrary.R;
import com.hyg.utilslibrary.utils.DensityUtil;


public class ProgressView extends View {

    private  static final String TAG = ProgressView.class.getName();
    private int totalLength;
    private int progressColor;
    private float progressHeight;
    private int unProgressColor;
    private float progress;
    private Paint paint;
    private int MARGIN;
    //是否可触摸滑动
    private boolean isFocus;
    private OnProgressScrollListener onProgressScrollListener;
    private int startX;
    private int endX;
    private boolean touching = false;

    public ProgressView(Context context) {
        this(context,null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        totalLength = 100;
        progressColor = getResources().getColor(R.color.colorPrimary);
        unProgressColor = getResources().getColor(R.color.color_f5f5f5);
        progressHeight = 8f;
        MARGIN = DensityUtil.dp2px(5);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ProgressView);
            totalLength = array.getInt(R.styleable.ProgressView_totalLength,totalLength);
            progressColor = array.getColor(R.styleable.ProgressView_progressColor,progressColor);
            unProgressColor = array.getColor(R.styleable.ProgressView_unProgressColor,unProgressColor);
            progressHeight = array.getFloat(R.styleable.ProgressView_progressHeight,progressHeight);
            isFocus = array.getBoolean(R.styleable.ProgressView_focus,isFocus);
            array.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(progressColor);
        paint.setStrokeWidth(progressHeight);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightDefaultMeasureSpecSize = DensityUtil.dp2px(20);
        if (widthMeasureSpecMode == MeasureSpec.AT_MOST && heightMeasureSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthMeasureSpecSize,heightDefaultMeasureSpecSize);
        }else if (widthMeasureSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpecSize,heightMeasureSpec);
        }else if (heightMeasureSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,heightDefaultMeasureSpecSize);
        }
//        startX = getPaddingLeft()+MARGIN;
//        endX = getWidth() - getPaddingRight() - MARGIN;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isFocus) {
            return super.onTouchEvent(event);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touching = true;
                progress = countLength(event.getX());
                invalidate();
                if (onProgressScrollListener != null) {
                    onProgressScrollListener.onStartScroll(progress,totalLength);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                progress = countLength(event.getX());
                invalidate();
                if (onProgressScrollListener != null) {
                    onProgressScrollListener.onScroll(progress,totalLength);
                }
                break;
            case MotionEvent.ACTION_UP:
                progress = countLength(event.getX());
                invalidate();
                touching = false;
                if (onProgressScrollListener != null) {
                    onProgressScrollListener.onEndScroll(progress,totalLength);
                }

                break;
        }
        return true;
    }

    private float countLength(float x) {
        int contentLength = -1;
        if (x < startX || x > endX) {
            return contentLength;
        }
        float f = (x - startX)/((float)(endX - startX));
        return totalLength*f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = getPaddingLeft()+MARGIN;
        endX = getWidth() - getPaddingRight() - MARGIN;

        paint.setColor(unProgressColor);
        canvas.drawLine(startX,getHeight()/2,endX,getHeight()/2,paint);

        paint.setColor(progressColor);
        float contentX = getContentX(startX,endX);
        canvas.drawLine(startX,getHeight()/2,contentX,getHeight()/2,paint);
    }

    private float getContentX(int startX,int endX) {
        float contentX = 0f;
        float f = progress/totalLength;
        SLog.i(TAG,"=======getContentX========="+f);
        if (f <= 0){
            contentX = startX;
        }else if (f >= 1){
            contentX = endX;
        }else{
            contentX = (endX - startX)*f;
        }
        return contentX;
    }

    public void setOnProgressScrollListener(OnProgressScrollListener onProgressScrollListener) {
        this.onProgressScrollListener = onProgressScrollListener;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        SLog.i(TAG,"=============setProgress==============="+progress);
        invalidate();
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }

    public int getTotalLength() {
        return totalLength;
    }

    public boolean isTouching() {
        return touching;
    }

    public interface OnProgressScrollListener{

        /**
         *
         * 开始滑动
         * @param contentLength
         * @param totalLength
         */
        void onStartScroll(float contentLength,int totalLength);

        /**
         * 正在滑动
         *
         * @param contentLength
         * @param totalLength
         */
        void onScroll(float contentLength,int totalLength);

        /**
         *
         * 结束滑动
         * @param contentLength
         * @param totalLength
         */
        void onEndScroll(float contentLength,int totalLength);
    }
}
