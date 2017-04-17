package com.smart.control.planewar.widget.bullet;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.MoveAbleElement;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Bullet extends MoveAbleElement {

    protected boolean isHit;

    public Bullet() {
        super(PlaneApplication.getInstance());
    }

    @Override
    public void calculate(float diff){
        mLocationY = mLocationY - mSpeed * diff;
        if (!isElementFly()) {
            ViewDrawManager.getInstance().removeBullet(Bullet.this);
            isOutOfScreen = true;
//            Log.d("Log_text", "Bullet+calculate Bullet fly outsize");
        }
    }

    @Override
    public boolean isElementFly() {
        //[100, 0] start 100 end 0
        return mLocationY >= mEndY && mLocationY <= mStartY;
    }

    public boolean isHit() {
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
