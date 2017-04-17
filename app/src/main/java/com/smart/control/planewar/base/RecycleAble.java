package com.smart.control.planewar.base;

/**
 * Created by byang059 on 4/13/17.
 */

public interface RecycleAble {
    boolean isCanRecycle();
    void setRecycle(boolean canRecycle);
    void onRecycleCleanData();
}
