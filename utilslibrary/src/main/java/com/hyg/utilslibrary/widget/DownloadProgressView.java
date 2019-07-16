package com.hyg.utilslibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hyg.utilslibrary.R;
import com.hyg.utilslibrary.utils.DensityUtil;


public class DownloadProgressView extends View {

    private long totalLength = 1;
    private long currentPosition = 0;
    private int pColor;
    private int radius;
    private int centerX;
    private int centerY;
    private Paint paint;
    private int inRadius;
    private int sweepAngle = 0;
    public DownloadProgressView(Context context) {
        this(context,null);
    }

    public DownloadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        pColor = getResources().getColor(R.color.white);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(pColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultSize = DensityUtil.dp2px(50);
        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST) {

            setMeasuredDimension(defaultSize,defaultSize);
        }else if (widthMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(defaultSize,heightMeasureSpec);
        }else if (heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,defaultSize);
        }
        radius = Math.min(getMeasuredWidth()/2,getMeasuredHeight()/2);
        centerX = getMeasuredWidth()/2;
        centerY = getMeasuredHeight()/2;
        inRadius = radius - DensityUtil.dp2px(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX,centerY,radius,paint);

        paint.setStyle(Paint.Style.FILL);
        RectF inRectF = new RectF(centerX - inRadius,centerY - inRadius,centerX+inRadius,centerY+inRadius);

        canvas.drawArc(inRectF,270,computeSweepAngle(),true,paint);

    }

    private int computeSweepAngle(){
        int sweepAngle = (int) (currentPosition*360/(float)totalLength);
        return sweepAngle;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public void setCurrentPosition(long currentPosition) {
        this.currentPosition = currentPosition;
        invalidate();
    }
}
