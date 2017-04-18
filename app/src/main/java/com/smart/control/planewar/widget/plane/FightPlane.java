package com.smart.control.planewar.widget.plane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.PlaneConfig;
import com.smart.control.planewar.R;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.base.RecycleFactory;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.bullet.SingleBullet;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:战斗机
 * 修改:
 */
public class FightPlane extends Plane {
    public int attackInterval;

    @Override
    public void calculate(float diff) {

    }

    @Override
    public boolean isElementFly() {
        return false;
    }

    @Override
    public void init() {
        super.init();
        mSpeed = 0;
        mLifeLeft = 1;
        attackInterval = PlaneConfig.SPEED_MIDDLE;
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(PlaneApplication.getInstance().getResources(), R
                .mipmap.plane);
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet() {
        //获取当前战机的位置，在当前位置发射子弹
        return shootBullet(RecycleFactory.getInstance().getRecycleInstance(SingleBullet.class));
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet(Bullet bullet) {
        //获取当前战机的位置，在当前位置发射子弹
        float originX = mLocationX + mWidth * 0.5f;
        float originY = mLocationY - bullet.mHeight;
        bullet.fire(originX, originY, originX, 0, bullet.mSpeed);
        //告诉View 要刷新我的子弹
        ViewDrawManager.getInstance().drawBullet(bullet);
        return bullet;
    }

    public void stopBullet() {

    }
}
