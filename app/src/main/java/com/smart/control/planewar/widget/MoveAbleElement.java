package com.smart.control.planewar.widget;

import android.content.Context;

import com.smart.control.planewar.Config;
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
    public volatile boolean isOutOfScreen = false;
    public long mLastInvokeTime = 0;

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
        mLastInvokeTime = System.currentTimeMillis();
    }

    public void calculateTime(){
        long currentTime = System.currentTimeMillis();
        float v = (currentTime - mLastInvokeTime) * 1.0f;
//        Log.d("Log_text", "calculateTime+calculate diff =>" + v);
        float diff = v /Config.DATA_INTERVAL_REFRESH;
//        Log.d("Log_text", "Bullet+calculate diff =>" + diff);
        calculate(diff);
        mLastInvokeTime = currentTime;
    }

    public abstract void calculate(float delayMillis);
}
