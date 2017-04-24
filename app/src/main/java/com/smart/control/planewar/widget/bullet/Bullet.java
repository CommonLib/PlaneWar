package com.smart.control.planewar.widget.bullet;

import com.smart.control.planewar.widget.MoveAbleElement;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Bullet extends MoveAbleElement {

    protected boolean isHit;

    @Override
    public void calculateDiff(float diff){
        mLocationY = mLocationY - mSpeed * diff;
    }

    @Override
    public boolean isElementLocationInSide() {
        //[100, 0] start 100 end 0
        return mLocationY >= mEndY;
    }

    @Override
    public boolean isDestroy() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    @Override
    public void onRecycleCleanData() {
        super.onRecycleCleanData();
        isHit = false;
    }
}
