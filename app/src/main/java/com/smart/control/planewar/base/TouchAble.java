package com.smart.control.planewar.base;

import android.view.MotionEvent;

/**
 * Created by byang059 on 4/18/17.
 */

public interface TouchAble {
    float getLeft();
    float getRight();
    float getTop();
    float getBottom();
    int getZOrder();
    boolean onTouchEvent(MotionEvent event);
}
