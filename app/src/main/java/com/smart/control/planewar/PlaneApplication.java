package com.smart.control.planewar;

import android.app.Application;

/**
 * Created by byang059 on 4/12/17.
 */

public class PlaneApplication extends Application {
    public static  PlaneApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static PlaneApplication getInstance(){
        return instance;
    }
}
