package com.smart.control.planewar;

import com.smart.control.planewar.widget.GameView;
import com.smart.control.planewar.widget.MoveAbleElement;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.plane.EnemyPlane;
import com.smart.control.planewar.widget.plane.Plane;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/25/16.
 */

public class OperateCalculateManager {

    public static final OperateCalculateManager manager = new OperateCalculateManager();
    private final Thread mThread;
    private final ArrayList<EnemyPlane> mEnemyPlanes = new ArrayList<>();
    private Plane mPlane;
    private final ArrayList<Bullet> mBullets = new ArrayList<>();
    //private ArrayList<Bullet> mWeapons = new ArrayList<>();

    public void startCalculate(MoveAbleElement element) {
        synchronized (manager){
            if (element instanceof Bullet) {
                mBullets.add((Bullet) element);
//                LogUtil.d("startCalculate  + Bullet");
            } else if (element instanceof EnemyPlane) {
                mEnemyPlanes.add((EnemyPlane) element);
            }
        }
    }

    public OperateCalculateManager() {
        //开启线程，处理数据
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!GameView.isGameContinue) {
                        try {
                            synchronized (GameView.class) {
                                GameView.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    synchronized (manager){
                        for (int i = 0; i < mEnemyPlanes.size(); i++) {
                            EnemyPlane enemyPlane = mEnemyPlanes.get(i);
                            enemyPlane.calculate();
                            if (enemyPlane.isOutOfScreen()) {
                                mEnemyPlanes.remove(i);
                                enemyPlane.setRecycle(true);
                                i--;
                                continue;
                            }
                            dealWithEnemyPlaneHit(enemyPlane);
                            if (enemyPlane.isDestroy()) {
                                mEnemyPlanes.remove(i);
                                enemyPlane.setRecycle(true);
                                i--;
                                continue;
                            }

                        }

                        for (int i = 0; i < mBullets.size(); i++) {
                            Bullet bullet = mBullets.get(i);
                            bullet.calculate();
                            if (bullet.isOutOfScreen() || bullet.isDestroy()) {
//                                LogUtil.d("bullet.isOutOfScreen => true");
                                mBullets.remove(i);
                                bullet.setRecycle(true);
                                i--;
                            }
                        }
                    }
                }
            }
        });
    }

    private void dealWithEnemyPlaneHit(EnemyPlane enemyPlane) {
        for (int i = 0; i < mBullets.size(); i++) {
            Bullet bullet = mBullets.get(i);
            if (!bullet.isDestroy()) {
                boolean planeHit = enemyPlane.isPlaneHit(bullet);
                //                Log.d("Log_text", "BattleFieldView+drawGame  planeHit=> " + planeHit);
                if (planeHit) {
                    enemyPlane.onHitReduceLife();
                    bullet.setHit(true);
                    if (enemyPlane.isDestroy()) {
                        break;
                    }
                }
            }
        }
    }

    public Thread getCalculateThread() {
        return mThread;
    }

    public static OperateCalculateManager getInstance() {
        return manager;
    }

    public ArrayList<EnemyPlane> getEnemyPlanes() {
        return mEnemyPlanes;
    }

    public ArrayList<Bullet> getBullets() {
        return mBullets;
    }
}
