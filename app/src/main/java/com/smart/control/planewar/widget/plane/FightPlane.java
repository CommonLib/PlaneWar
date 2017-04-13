package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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

    public FightPlane(Context context) {
        super(context);
    }

    @Override
    public void calculate(float diff) {

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
        Bullet bullet = mBulletQueue.peekLast();
        if (bullet != null && bullet.isOutOfScreen) {
            mBulletQueue.removeLast();
            bullet.isOutOfScreen = false;
            Log.d("Log_text", "FightP'lane+shootBullet + user cache Bullet to shoot");
        } else {
            Log.d("Log_text", "FightPlane+shootBullet + new Bullet to shoot");
            bullet = new SingleBullet(getContext());
        }
        mBulletQueue.addFirst(bullet);
        return shootBullet(bullet);
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

    @Override
    public void layout(int l, int t, int r, int b) {
        if (mFirstLayout) {
            super.layout(l, t, r, b);
            mFirstLayout = false;
        }
    }
}
