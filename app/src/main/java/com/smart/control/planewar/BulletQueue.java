package com.smart.control.planewar;

import com.smart.control.planewar.widget.bullet.Bullet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author:dongpo 创建时间: 9/14/2016
 * 描述:
 * 修改:
 */
public class BulletQueue {

    /**
     * 正在飞行的子弹
     */
    ArrayList<Bullet> mBullets = new ArrayList<>();

    /**
     * 已经击中或者发射到头的子弹，可复用，且已添加到父view上，不用调用刷新，直接修改位置即可重新发射
     */
    LinkedList<Bullet> mFiredBullets = new LinkedList<>();

    public boolean add(Bullet bullet) {
        return mBullets.add(bullet);
    }


    public boolean remove(Bullet bullet) {
        return mBullets.remove(bullet);
    }

    public Bullet getBenchFiredBullet(){
        Bullet bullet = null;
        try {
            bullet = mFiredBullets.removeLast();
            if(bullet != null){
                mBullets.add(bullet);
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return bullet;
    }

    public boolean addFiredBullet(Bullet bullet) {
        return mFiredBullets.add(bullet);
    }


}
