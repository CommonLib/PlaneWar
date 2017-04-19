package com.smart.control.planewar;

import android.app.Application;

import com.smart.control.planewar.utils.LogUtil;

/**
 * Created by byang059 on 4/12/17.
 */

public class PlaneApplication extends Application {
    public static PlaneApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LogUtil.init(getPackageName(), true);
    }

    public static PlaneApplication getInstance() {
        return instance;
    }
}
