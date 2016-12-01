package com.smart.control.planewar.widget.bullet;

import android.content.Context;

import com.smart.control.planewar.ControlRunnable;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.MoveAbleElement;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Bullet extends MoveAbleElement {


    public Bullet(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected ControlRunnable onFiredDataDealWith() {
        return new ControlRunnable() {
            @Override
            public void run() {
                mLocationY = mLocationY - mSpeed;
                if (mLocationY <= mEndY) {
                    setGoOn(false);
                    ViewDrawManager.getInstance().removeBullet(Bullet.this);
                    isOutOfScreen = true;
                }
            }
        };
    }
}
