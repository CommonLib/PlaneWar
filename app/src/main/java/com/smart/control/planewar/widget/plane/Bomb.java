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

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(PlaneApplication.getInstance().getResources(), R.mipmap
                .bomb_award);
    }

    @Override
    public void calculateDiff(float delayMillis) {

    }

    @Override
    public boolean isElementLocationInSide() {
        return false;
    }

}
