package com.smart.control.planewar;

import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.plane.*;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/25/16.
 */

public class ViewDrawManager {
    public static ViewDrawManager manager = new ViewDrawManager();
    private ArrayList<Bullet> mBullets = new ArrayList<>();
    private ArrayList<EnemyPlane> mEnemyPlanes = new ArrayList<>();
    private Plane mPlane;
    //private ArrayList<Bullet> mWeapons = new ArrayList<>();

    public static ViewDrawManager getInstance(){
        return manager;
    }

    public ArrayList<Bullet> getBullets(){
        return mBullets;
    }

    public void drawBullet(Bullet element) {
        if (mBullets.contains(element)) {
            return;
        }
        mBullets.add(element);
    }

    public void removeBullet(Bullet element) {
        mBullets.remove(element);
    }

    public ArrayList<EnemyPlane> getEnemyPlanes(){
        return mEnemyPlanes;
    }

    public void drawEnemyPlane(EnemyPlane element) {
        if (mEnemyPlanes.contains(element)) {
            return;
        }
        mEnemyPlanes.add(element);
    }

    public void removeEnemyPlane(EnemyPlane element) {
        mEnemyPlanes.remove(element);
    }

    public void drawPlane(Plane plane){
        mPlane = plane;
    }

    public Plane getPlane(){
        return mPlane;
    }
}
