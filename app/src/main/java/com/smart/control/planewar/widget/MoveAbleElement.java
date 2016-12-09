package com.smart.control.planewar.widget;

import android.content.Context;

import com.smart.control.planewar.OperateCalculateManager;

/**
 * Created by byang059 on 11/30/16.
 */

public abstract class MoveAbleElement extends Element {

    public float mSpeed;
    public float mOriginX;
    public float mOriginY;
    public float mEndX;
    public float mEndY;
    public boolean isOutOfScreen = false;

    public MoveAbleElement(Context context) {
        super(context);
    }

    public void fire(float startX, float startY, float endX, float endY, float speed) {
        mOriginX = startX;
        mOriginY = startY;
        mEndX = endX;
        mEndY = endY;
        mLocationX = mOriginX;
        mLocationY = mOriginY;
        mSpeed = speed;
        //将初始化数据，和子弹，交给数据处理系统处理数据
        OperateCalculateManager.getInstance().startCalculate(this);
    }

    public abstract void calculate();
}
