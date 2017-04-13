package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.util.Log;

import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.bullet.Bullet;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class EnemyPlane extends Plane {

    public EnemyPlane(Context context) {
        super(context);
        mLifeLeft = 3;
    }

    @Override
    public void calculate(float diff) {
        mLocationY = mLocationY - mSpeed * diff;
        if (mLocationY <= 0) {
            ViewDrawManager.getInstance().removeEnemyPlane(EnemyPlane.this);
            isOutOfScreen = true;
        }
    }

    public boolean isPlaneHit(Bullet bullet){
        if(!isPlaneInSameRow(bullet)){
            Log.d("Log_text", "EnemyPlane+isPlaneHit => isPlaneInSameRow => false");
            return false;
        }

        boolean isHit = bullet.mLocationY >= (mLocationY - bullet.mHeight) && bullet.mLocationY <= (
                mLocationY + mHeight);
        Log.d("Log_text", "EnemyPlane+isPlaneHit => isPlaneInSameRow => true");
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

}
