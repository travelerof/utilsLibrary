package com.hyg.utilslibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.hyg.utilslibrary.R;


/**
 *
 * 圆角或者圆形图片
 *
 */
public class CircleImageView extends AppCompatImageView {

    private Paint paint;
    //圆角半径
    private int radius;
    private int color;

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        color = context.getResources().getColor(R.color.white);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            radius = array.getDimensionPixelOffset(R.styleable.CircleImageView_cRadius,0);
            color = array.getColor(R.styleable.CircleImageView_bgColor,color);
            array.recycle();
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (radius <= 0) {
            return;
        }
        int width = getWidth();
        int height = getHeight();

        int min = Math.min(width / 2, height / 2);
        if (radius > min) {
            radius = min;
        }

        //左上角的圆角
        RectF leftTopRectF = new RectF(0,0,radius*2,radius*2);
        Path leftTopPath = new Path();
        leftTopPath.addArc(leftTopRectF,180,90);
        leftTopPath.lineTo(0,0);
        leftTopPath.lineTo(0,radius);
        canvas.drawPath(leftTopPath,paint);

        //右上角圆角
        RectF rightTopRectF = new RectF(width - radius*2,0,width,radius*2);
        Path rightTopPath = new Path();
        rightTopPath.addArc(rightTopRectF,270,90);
        rightTopPath.lineTo(width,0);
        rightTopPath.lineTo(width - radius,0);
        canvas.drawPath(rightTopPath,paint);

        //左下角圆角
        RectF leftBottomRectF = new RectF(0,height - radius*2,radius*2,height);
        Path leftBottomPath = new Path();
        leftBottomPath.addArc(leftBottomRectF,90,90);
        leftBottomPath.lineTo(0,height);
        leftBottomPath.lineTo(radius,height);
        canvas.drawPath(leftBottomPath,paint);

        //右下角圆角
        RectF rightBottomRectF = new RectF(width - radius*2,height - radius*2,width,height);
        Path rightBottomPath = new Path();
        rightBottomPath.addArc(rightBottomRectF,0,90);
        rightBottomPath.lineTo(width,height);
        rightBottomPath.lineTo(width,height - radius);
        canvas.drawPath(rightBottomPath,paint);
    }
}
