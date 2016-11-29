package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.bullet.SingleBullet;

import java.util.LinkedList;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:战斗机
 * 修改:
 */
public class FightPlane extends Plane {
    public LinkedList<Bullet> mBulletQueue;
    public int attackInterval;
    private boolean mFirstLayout = true;
    private Bullet mBullet;

    public FightPlane(Context context) {
        super(context);
    }

    @Override
    public void init() {
        super.init();
        mSpeed = 0;
        mLifeLeft = 1;
        attackInterval = Config.SPEED_MIDDLE;
        mBulletQueue = new LinkedList<>();
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
        Bullet bullet = mBulletQueue.pollLast();
        if(bullet!= null && bullet.isOutOfScreen){
            bullet.isOutOfScreen = false;
            mBullet = bullet;
        }else{
            mBullet = new SingleBullet(getContext());
            mBulletQueue.addFirst(mBullet);
        }
        return shootBullet(mBullet);
    }

    /**
     * 发射子弹
     */
    public Bullet shootBullet(Bullet bullet) {
        //获取当前战机的位置，在当前位置发射子弹
        float originX = mLocationX + mWidth * 0.5f;
        float originY = mLocationY - bullet.mHeight;
        bullet.fireBullet(originX, originY, bullet.mSpeed);
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
