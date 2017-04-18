package com.smart.control.planewar.widget.plane;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.R;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class LittleEnemyPlane extends EnemyPlane {

    @Override
    protected void init() {
        super.init();
        mLifeLeft = 1;
    }

    @Override
    protected Bitmap getElementIcon() {
        return BitmapFactory.decodeResource(PlaneApplication.getInstance().getResources(), R
                .mipmap.small);
    }

    @Override
    public void onRecycleCleanData() {
        super.onRecycleCleanData();
        mLifeLeft = 1;
    }
}
