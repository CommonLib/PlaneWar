package com.smart.control.planewar.widget.bullet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class SingleBullet extends Bullet {
    public SingleBullet(Context context){
        super(context);
        mSpeed = Config.ENEMY_SPEED_SLOW;
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.yellow_bullet);
    }
}
