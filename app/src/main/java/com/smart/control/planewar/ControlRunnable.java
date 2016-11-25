package com.smart.control.planewar;

/**
 * Created by byang059 on 11/25/16.
 */

public abstract class ControlRunnable implements Runnable{
    public boolean isGoOn;
    public void setGoOn(boolean isGoOn){
        this.isGoOn = isGoOn;
    }
}