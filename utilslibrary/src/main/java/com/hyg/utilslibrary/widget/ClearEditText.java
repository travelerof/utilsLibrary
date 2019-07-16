package com.hyg.utilslibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hyg.utilslibrary.R;
import com.hyg.utilslibrary.utils.DensityUtil;

/**
 *
 *
 */
public class ClearEditText extends AppCompatEditText implements TextWatcher {


    private boolean showDelete = false;
    private Bitmap bitmap;
    private int deleteLeft = 0;
    boolean down = false;
    boolean up = false;

    public ClearEditText(Context context) {
        this(context,null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        addTextChangedListener(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.delete);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (showDelete) {
                    if (event.getX() > deleteLeft && event.getX() < getWidth()) {
                        down = true;
                        return true;
                    }else{
                        down = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (showDelete) {
                    if (event.getX() > deleteLeft && event.getX() < getWidth()) {
                        up = true;
                        if (down && up) {
                            setText("");
                        }
                        return true;
                    }else{
                        up = false;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (showDelete) {
            down = false;
            up = false;
            int width = getWidth();
            int height = getHeight();
            deleteLeft = width - DensityUtil.dp2px(10) - bitmap.getWidth();
            canvas.drawBitmap(bitmap,deleteLeft,height/2 - bitmap.getHeight()/2,null);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (TextUtils.isEmpty(s.toString())) {
            if (showDelete){
                showDelete = false;
                invalidate();
            }
        }else{
            if (!showDelete) {
                showDelete = true;
                invalidate();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
