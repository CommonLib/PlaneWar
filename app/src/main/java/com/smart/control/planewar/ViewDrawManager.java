package com.smart.control.planewar;

import com.smart.control.planewar.widget.plane.Plane;

/**
 * Created by byang059 on 11/25/16.
 */

public class ViewDrawManager {
    public static ViewDrawManager manager = new ViewDrawManager();
    private Plane mPlane;
    //private ArrayList<Bullet> mWeapons = new ArrayList<>();

    public static ViewDrawManager getInstance(){
        return manager;
    }

    public void drawPlane(Plane plane){
        mPlane = plane;
    }

    public Plane getPlane(){
        return mPlane;
    }
}
