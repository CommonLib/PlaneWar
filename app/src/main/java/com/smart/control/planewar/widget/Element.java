package com.smart.control.planewar.widget;

import android.graphics.Bitmap;
import android.graphics.Paint;

import com.smart.control.planewar.base.RecycleAble;

/**
 * @author:dongpo 创建时间: 9/14/2016
 * 描述:
 * 修改:
 */
public abstract class Element implements RecycleAble {
    public Bitmap mStyleBitmap;
    public float mLocationX;
    public float mLocationY;
    public int mWidth;
    public int mHeight;
    public Paint mPaint;
    public Element() {
        init();
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
