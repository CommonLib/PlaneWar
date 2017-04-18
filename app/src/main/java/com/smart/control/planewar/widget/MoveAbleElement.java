package com.smart.control.planewar.widget;

import com.smart.control.planewar.OperateCalculateManager;
import com.smart.control.planewar.PlaneConfig;

/**
 * Created by byang059 on 11/30/16.
 */

public abstract class MoveAbleElement extends Element {

    public float mSpeed;
    public float mStartX;
    public float mStartY;
    public float mEndX;
    public float mEndY;
    public volatile boolean isOutOfScreen = false;
    public long mLastInvokeTime = 0;

    public void fire(float startX, float startY, float endX, float endY, float speed) {
        mStartX = startX;
        mStartY = startY;
        mEndX = endX;
        mEndY = endY;
        mLocationX = mStartX;
        mLocationY = mStartY;
        mSpeed = speed;
        //将初始化数据，和子弹，交给数据处理系统处理数据
        OperateCalculateManager.getInstance().startCalculate(this);
        mLastInvokeTime = System.currentTimeMillis();
    }

    public void calculateTime(){
        long currentTime = System.currentTimeMillis();
        float v = (currentTime - mLastInvokeTime) * 1.0f;
//        Log.d("Log_text", "calculateTime+calculate diff =>" + v);
        float diff = v / PlaneConfig.DATA_INTERVAL_REFRESH;
//        Log.d("Log_text", "Bullet+calculate diff =>" + diff);
        calculate(diff);
        mLastInvokeTime = currentTime;
    }

    @Override
    public boolean isCanRecycle() {
        return isOutOfScreen;
    }

    public abstract void calculate(float delayMillis);

    @Override
    public void setRecycle(boolean canRecycle) {
        isOutOfScreen = canRecycle;
    }

    @Override
    public void onRecycleCleanData() {
        mLocationY = mStartY;
        mLocationX = mStartX;
    }

    public abstract boolean isElementFly();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MoveAbleElement that = (MoveAbleElement) o;

        if (Float.compare(that.mSpeed, mSpeed) != 0) {
            return false;
        }
        if (Float.compare(that.mStartX, mStartX) != 0) {
            return false;
        }
        if (Float.compare(that.mStartY, mStartY) != 0) {
            return false;
        }
        if (Float.compare(that.mEndX, mEndX) != 0) {
            return false;
        }
        if (Float.compare(that.mEndY, mEndY) != 0) {
            return false;
        }
        if (isOutOfScreen != that.isOutOfScreen) {
            return false;
        }
        return mLastInvokeTime == that.mLastInvokeTime;

    }

    @Override
    public int hashCode() {
        int result = (mSpeed != +0.0f ? Float.floatToIntBits(mSpeed) : 0);
        result = 31 * result + (mStartX != +0.0f ? Float.floatToIntBits(mStartX) : 0);
        result = 31 * result + (mStartY != +0.0f ? Float.floatToIntBits(mStartY) : 0);
        result = 31 * result + (mEndX != +0.0f ? Float.floatToIntBits(mEndX) : 0);
        result = 31 * result + (mEndY != +0.0f ? Float.floatToIntBits(mEndY) : 0);
        result = 31 * result + (isOutOfScreen ? 1 : 0);
        result = 31 * result + (int) (mLastInvokeTime ^ (mLastInvokeTime >>> 32));
        return result;
    }
}
