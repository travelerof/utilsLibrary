package com.hyg.utilslibrary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hyg.utilslibrary.R;
import com.hyg.utilslibrary.utils.DensityUtil;


/**
 * Created by DELL on 2018/3/16.
 */

public class SwitchButton extends View implements View.OnClickListener {

    public static final int OPEN = 0x0001;
    public static final int CLOSE = 0x0002;
    private static final int MARGIN = 5;
    private Context context;
    private Paint paint;
    private float startX;
    private int type = CLOSE;
    private boolean isOpen;
    private OnCheckedChangedListener onCheckedChangedListener;

    public SwitchButton(Context context) {
        super(context);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init(Context context){
        this.context = context;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        setOnClickListener(this);
    }


    public void setOnCheckedChangedListener(OnCheckedChangedListener onCheckedChangedListener) {
        this.onCheckedChangedListener = onCheckedChangedListener;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        int defaultWidthSize = DensityUtil.dp2px(50);
        int defaultHeightSize = DensityUtil.dp2px(24);
        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST){

            setMeasuredDimension(defaultWidthSize,defaultHeightSize);
        }else if (widthMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultWidthSize,heightMeasureSpec);
        }else if (heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSpec,defaultHeightSize);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isOpen) {
            startX = getWidth()/2;
        }
        paint.setColor(getResources().getColor(R.color.color_aaaaaa));
        RectF bg_RectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(bg_RectF,2,2,paint);
        if (startX != 0){
            paint.setColor(getResources().getColor(R.color.colorPrimary));
            RectF rectF = new RectF(0,0,startX+getWidth()/2,getHeight());
            canvas.drawRoundRect(rectF,2,2,paint);
        }

        RectF rect = new RectF(startX+getPaddingLeft()+1,getPaddingTop()+1,startX+(getWidth() - getPaddingRight()*2)/2 -1,getHeight() - getPaddingBottom()-1);
        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawRoundRect(rect,2,2,paint);
        if (type == OPEN){
            if (startX < getWidth()/2){
                startX += MARGIN;
                invalidate();
            }
            if (startX >= getWidth()/2){
                startX = getWidth()/2;
                isOpen = false;
            }
        }else{
            if (startX > 0){
                startX -= MARGIN;
                invalidate();
            }
            if (startX <= 0){
                startX = 0;
            }
        }


    }

    public void onOpen(boolean isOpoen){
        this.isOpen = isOpoen;

        open(isOpoen);
    }

    public void open(boolean status){
        if (status){
            type = OPEN;
        }else{
            type = CLOSE;
        }
        invalidate();
    }

    @Override
    public void onClick(View v) {

        if (type == OPEN){
            type = CLOSE;
        }else{
            type = OPEN;
        }
        if (onCheckedChangedListener != null){

            if (type == OPEN){
                onCheckedChangedListener.open();
            }else {
                onCheckedChangedListener.close();
            }
        }
        invalidate();
    }

    public interface OnCheckedChangedListener{

        void open();
        void close();
    }
}
