package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.R;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.plane.EnemyPlane;
import com.smart.control.planewar.widget.plane.Plane;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制 67帧 每15ms刷新一次数据
 */

public class BattleFieldView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private Bitmap mBackground;

    public BattleFieldView(Context context) {
        super(context);
        mPaint = new Paint();
        holder = this.getHolder();
        holder.addCallback(this);
        myThread = new DrawThread(holder);//创建一个绘图线程
        post(new Runnable() {
            @Override
            public void run() {
                Bitmap bg = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
                mBackground = Bitmap.createScaledBitmap(bg, getWidth(), getHeight(), false);
            }
        });
    }

    private SurfaceHolder holder;
    private DrawThread myThread;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        myThread.isRun = false;
    }


    private void drawGame(Canvas canvas) {
        Plane plane = ViewDrawManager.getInstance().getPlane();
        canvas.drawBitmap(mBackground, 0, 0, mPaint);
        canvas.drawBitmap(plane.mStyleBitmap, plane.mLocationX, plane.mLocationY, mPaint);
        ArrayList<EnemyPlane> enemyPlanes = ViewDrawManager.getInstance().getEnemyPlanes();
        ArrayList<Bullet> bullets = ViewDrawManager.getInstance().getBullets();
        for (int i = 0; i < enemyPlanes.size(); i++) {
            EnemyPlane enemyPlane = enemyPlanes.get(i);
            boolean isCrash = false;
            for (int j = 0; j < bullets.size(); j++) {
                Bullet bullet = bullets.get(j);
                boolean planeHit = enemyPlane.isPlaneHit(bullet);
                Log.d("Log_text", "BattleFieldView+drawGame  planeHit=> " + planeHit);
                if(planeHit){
                    enemyPlane.onHitReduceLife();
                }
                if(enemyPlane.isPlaneCrash()){
                    isCrash = true;
                    enemyPlanes.remove(enemyPlane);
                    i--;
                    bullets.remove(bullet);
                    break;
                }
            }

            if(!isCrash){
                //bug ui and data always read data, sync thread issue.
                canvas.drawBitmap(enemyPlane.mStyleBitmap, enemyPlane.mLocationX, enemyPlane.mLocationY,
                        mPaint);
            }
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            //bug ui and data always read data, sync thread issue.
            canvas.drawBitmap(bullet.mStyleBitmap, bullet.mLocationX, bullet.mLocationY, mPaint);
        }
        SystemClock.sleep(Config.VIEW_INTERVAL_REFRESH);
    }

    public void startDraw() {
        myThread.isRun = true;
        myThread.start();
    }

    //线程内部类
    class DrawThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            while (isRun) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        drawGame(c);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。

                    }
                }
            }
        }
    }

    //线程内部类
    class CalculateThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public CalculateThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            while (isRun) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        //处理数据
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。

                    }
                }
            }
        }
    }
}
