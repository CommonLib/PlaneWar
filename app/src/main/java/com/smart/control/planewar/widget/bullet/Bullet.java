package com.smart.control.planewar.widget.bullet;

import android.content.Context;

import com.smart.control.planewar.ControlRunnable;
import com.smart.control.planewar.OperateCalculateManager;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.Element;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Bullet extends Element {
    public int mSpeed;
    public int mOriginX;
    public int mOriginY;
    public int mTargetX;
    public int mTargetY;

    public Bullet(Context context) {
        super(context);
    }

    @Override
    protected void init() {
        super.init();
    }

    public void fireBullet(int startX, int startY, final int speed) {
        mOriginX = startX;
        mOriginY = startY;
        mLocationX = mOriginX;
        mLocationY = mOriginY;
        mSpeed = speed;
        //将初始化数据，和子弹，交给数据处理系统处理数据
        OperateCalculateManager.getInstance().calculate(new ControlRunnable() {
            @Override
            public void run() {
                mLocationY = mLocationY - 100;
                if (mLocationY <= 0) {
                    setGoOn(false);
                    ViewDrawManager.getInstance().removeElememt(Bullet.this);
                }
            }
        });
    }

}
