package com.smart.control.planewar.widget.plane;

import android.util.Log;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.bullet.Bullet;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class EnemyPlane extends Plane {

    public EnemyPlane() {
        super(PlaneApplication.getInstance());
    }

    @Override
    protected void init() {
        super.init();
        mLifeLeft = 3;
    }

    @Override
    public void calculate(float diff) {
        mLocationY = mLocationY - mSpeed;
        if (!isElementFly()) {
            Log.d("Log_text", "EnemyPlane+calculate out of size");
            ViewDrawManager.getInstance().removeEnemyPlane(EnemyPlane.this);
            isOutOfScreen = true;
        }
//        Log.d("Log_text", "EnemyPlane+calculate mLocationY" + mLocationY + " speed "+ mSpeed);
    }

    public boolean isPlaneHit(Bullet bullet){
        if(!isPlaneInSameRow(bullet)){
//            Log.d("Log_text", "EnemyPlane+isPlaneHit => isPlaneInSameRow => false");
            return false;
        }

        boolean isHit = bullet.mLocationY >= (mLocationY - bullet.mHeight) && bullet.mLocationY <= (
                mLocationY + mHeight);
//        Log.d("Log_text", "EnemyPlane+isPlaneHit => isPlaneInSameRow => true");
        return isHit;
    }

    public boolean isPlaneInSameRow(Bullet bullet){
        return bullet.mLocationX >= mLocationX && bullet.mLocationX <= (mLocationX + mWidth);
    }

    public void onHitReduceLife(){
        mLifeLeft -- ;
    }

    public boolean isPlaneCrash(){
        return mLifeLeft <= 0;
    }

    @Override
    public boolean isElementFly() {
        //[0,100]  start 0 end 100
        return mLocationY <= mEndY;
    }

    @Override
    public void onRecycleCleanData() {
        super.onRecycleCleanData();
        mLifeLeft = 3;
    }
}
