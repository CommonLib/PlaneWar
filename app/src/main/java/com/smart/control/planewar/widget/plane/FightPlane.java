package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.bullet.SingleBullet;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:战斗机
 * 修改:
 */
public class FightPlane extends Plane {
    public Bullet mBullet;
    public int attackInterval;
    private boolean mFirstLayout = true;

    public FightPlane(Context context) {
        super(context);
    }

    @Override
    public void init() {
        super.init();
        mStyleBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.plane);
        mSpeed = 0;
        mLifeLeft = 1;
        mBullet = new SingleBullet(getContext());
        attackInterval = Config.SPEED_MIDDLE;
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet() {
        //获取当前战机的位置，在当前位置发射子弹
        Bullet bullet = mBullet.clone();
        return shootBullet(bullet);
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet(Bullet bullet) {
        //获取当前战机的位置，在当前位置发射子弹
        bullet.mOriginX = (int) (mLocationX + mWidth * 0.5f);
        bullet.mOriginY = mLocationY - bullet.mHeight;
        bullet.mTargetX = bullet.mOriginX;
        bullet.mTargetY = 0;
        bullet.mCurrentX = bullet.mOriginX;
        bullet.mCurrentY = bullet.mOriginY;
        return bullet;
    }

    public void stopBullet() {

    }

    public void setBullet(Bullet bullet) {
        mBullet = bullet;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        if (mFirstLayout) {
            super.layout(l, t, r, b);
            mFirstLayout  = false;
        }
    }
}
