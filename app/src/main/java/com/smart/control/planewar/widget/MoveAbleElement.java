package com.smart.control.planewar.widget;

import com.smart.control.planewar.OperateCalculateManager;
import com.smart.control.planewar.PlaneConfig;
import com.smart.control.planewar.base.RecycleAble;
import com.smart.control.planewar.base.RecycleFactory;

/**
 * Created by byang059 on 11/30/16.
 */

public abstract class MoveAbleElement extends Element implements RecycleAble {

    public float mSpeed;
    public float mStartX;
    public float mStartY;
    public float mEndX;
    public float mEndY;
    public long mLastInvokeTime = 0;
    public boolean mIsOutOfScreen = false;

    public void fire(float startX, float startY, float endX, float endY, float speed) {
        mStartX = startX;
        mStartY = startY;
        mEndX = endX;
        mEndY = endY;
        mLocationX = mStartX;
        mLocationY = mStartY;
        mSpeed = speed;
        setRecycle(false);
        //将初始化数据，和子弹，交给数据处理系统处理数据
        OperateCalculateManager.getInstance().startCalculate(this);
        mLastInvokeTime = System.currentTimeMillis();
    }

    public void calculate(){
        long currentTime = System.currentTimeMillis();
        float v = (currentTime - mLastInvokeTime) * 1.0f;
//        Log.d("Log_text", "calculate+calculateDiff diff =>" + v);
        float diff = v / PlaneConfig.DATA_INTERVAL_REFRESH;
//        Log.d("Log_text", "Bullet+calculateDiff diff =>" + diff);
        calculateDiff(diff);
        mLastInvokeTime = currentTime;
    }

    @Override
    public boolean isCanRecycle() {
        return mIsOutOfScreen || isDestroy();
    }

    public abstract void calculateDiff(float delayMillis);

    @Override
    public void setRecycle(boolean canRecycle) {
        if(canRecycle){
            RecycleFactory.getInstance().addElementRecycle(this);
        }
    }

    @Override
    public void onRecycleCleanData() {
        mLocationY = mStartY;
        mLocationX = mStartX;
        setRecycle(false);
    }

    public boolean isOutOfScreen(){
        mIsOutOfScreen = !isElementLocationInSide();
        return mIsOutOfScreen;
    }

    public abstract boolean isElementLocationInSide();

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
        return mLastInvokeTime == that.mLastInvokeTime;

    }

    @Override
    public int hashCode() {
        int result = (mSpeed != +0.0f ? Float.floatToIntBits(mSpeed) : 0);
        result = 31 * result + (mStartX != +0.0f ? Float.floatToIntBits(mStartX) : 0);
        result = 31 * result + (mStartY != +0.0f ? Float.floatToIntBits(mStartY) : 0);
        result = 31 * result + (mEndX != +0.0f ? Float.floatToIntBits(mEndX) : 0);
        result = 31 * result + (mEndY != +0.0f ? Float.floatToIntBits(mEndY) : 0);
        result = 31 * result + (int) (mLastInvokeTime ^ (mLastInvokeTime >>> 32));
        return result;
    }
}
