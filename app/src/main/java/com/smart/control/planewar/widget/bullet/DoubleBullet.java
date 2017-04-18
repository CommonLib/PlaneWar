package com.smart.control.planewar.widget.bullet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.PlaneConfig;
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
        mSpeed = PlaneConfig.ENEMY_SPEED_FAST;
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(PlaneApplication.getInstance().getResources(), R.mipmap
                .blue_bullet);
    }
}
