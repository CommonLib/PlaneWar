package com.smart.control.planewar.widget.plane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.R;
import com.smart.control.planewar.widget.MoveAbleElement;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class Bomb extends MoveAbleElement{
    public Bomb() {
        super(PlaneApplication.getInstance());
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.bomb_award);
    }

    @Override
    public void calculate(float delayMillis) {

    }

    @Override
    public boolean isElementFly() {
        return false;
    }
}
