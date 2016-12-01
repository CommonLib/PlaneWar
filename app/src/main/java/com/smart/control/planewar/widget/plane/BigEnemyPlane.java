package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.R;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class BigEnemyPlane extends EnemyPlane {

    public BigEnemyPlane(Context context) {
        super(context);
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.big);
    }
}
