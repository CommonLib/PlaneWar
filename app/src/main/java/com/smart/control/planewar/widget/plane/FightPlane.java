package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;
import com.smart.control.planewar.ViewDrawManager;
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
        mSpeed = 0;
        mLifeLeft = 1;
        attackInterval = Config.SPEED_MIDDLE;
        post(new Runnable() {
            @Override
            public void run() {
                mLocationX = getLeft();
                mLocationY = getTop();
            }
        });
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.plane);
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet() {
        //获取当前战机的位置，在当前位置发射子弹
        mBullet = new SingleBullet(getContext());
        return shootBullet(mBullet);
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet(Bullet bullet) {
        //获取当前战机的位置，在当前位置发射子弹
        int originX = (int) (mLocationX + mWidth * 0.5f);
        int originY = mLocationY - bullet.mHeight;
        bullet.fireBullet(originX, originY, Config.BULLET_SPEED_SLOW);
        //告诉View 要刷新我的子弹
        ViewDrawManager.getInstance().drawElement(bullet);
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
            mFirstLayout = false;
        }
    }
}
