package com.smart.control.planewar.widget.plane;

import android.content.Context;

import com.smart.control.planewar.widget.Element;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Plane extends Element {
    public int mSpeed;
    public int mLifeLeft;

    public Plane(Context context) {
        super(context);
    }
}
