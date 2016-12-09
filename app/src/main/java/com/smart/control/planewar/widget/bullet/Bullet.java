package com.smart.control.planewar.widget.bullet;

import android.content.Context;

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
    public void calculate(){
        mLocationY = mLocationY - mSpeed;
        if (!isBulletFly()) {
            ViewDrawManager.getInstance().removeBullet(Bullet.this);
            isOutOfScreen = true;
        }
    }

    public boolean isBulletFly(){
        return mLocationY > mEndY;
    }
}
