package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.smart.control.planewar.base.RecycleAble;

/**
 * @author:dongpo 创建时间: 9/14/2016
 * 描述:
 * 修改:
 */
public abstract class Element extends View implements RecycleAble {
    public Bitmap mStyleBitmap;
    public float mLocationX;
    public float mLocationY;
    public int mWidth;
    public int mHeight;
    public Paint mPaint;
    public Element(Context context) {
        super(context);
        init();
    }

    public Element(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mStyleBitmap.getWidth(),mStyleBitmap.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mStyleBitmap, 0, 0, mPaint);
    }

    protected void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLocationX = 0;
        mLocationY = 0;
        mStyleBitmap = getElementIcon();
        mWidth = mStyleBitmap.getWidth();
        mHeight = mStyleBitmap.getHeight();
    }

    protected abstract Bitmap getElementIcon();
}
