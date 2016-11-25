package com.smart.control.planewar.widget.plane;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class NormalEnemyPlane extends EnemyPlane {

    public NormalEnemyPlane(Context context) {
        super(context);
    }

    @Override
    protected Bitmap getElementIcon() {
        return null;
    }
}
