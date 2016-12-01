package com.smart.control.planewar.widget.plane;

import android.content.Context;

import com.smart.control.planewar.ControlRunnable;
import com.smart.control.planewar.ViewDrawManager;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class EnemyPlane extends Plane {

    public EnemyPlane(Context context) {
        super(context);
    }

    @Override
    protected ControlRunnable onFiredDataDealWith() {
        return new ControlRunnable() {
            @Override
            public void run() {
                mLocationY = mLocationY - mSpeed;
                if (mLocationY <= 0) {
                    setGoOn(false);
                    ViewDrawManager.getInstance().removeEnemyPlane(EnemyPlane.this);
                    isOutOfScreen = true;
                }
            }
        };
    }
}
