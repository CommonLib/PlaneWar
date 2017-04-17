package com.smart.control.planewar.widget.bullet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class DoubleBullet extends Bullet {

    @Override
    protected void init() {
        super.init();
        mSpeed = Config.ENEMY_SPEED_FAST;
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.blue_bullet);
    }
}
