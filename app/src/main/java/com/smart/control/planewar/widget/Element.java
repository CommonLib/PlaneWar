package com.smart.control.planewar.widget;

import android.graphics.Bitmap;

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
    public boolean mClickable;
    public boolean mLongClickable;
    public Element() {
        init();
    }

    protected void init(){
        mLocationX = 0;
        mLocationY = 0;
        mStyleBitmap = getElementIcon();
        mWidth = mStyleBitmap.getWidth();
        mHeight = mStyleBitmap.getHeight();
    }

    public boolean isClickable() {
        return mClickable;
    }

    public void setClickable(boolean clickable) {
        mClickable = clickable;
    }

    public boolean isLongClickable() {
        return mLongClickable;
    }

    public void setLongClickable(boolean longClickable) {
        mLongClickable = longClickable;
    }

    protected abstract Bitmap getElementIcon();
}
