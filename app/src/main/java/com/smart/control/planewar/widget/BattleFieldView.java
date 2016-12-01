package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.View;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.plane.EnemyPlane;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制 67帧 每15ms刷新一次数据
 */

public class BattleFieldView extends View {

    private final Paint mPaint;

    public BattleFieldView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList<Bullet> bullets = ViewDrawManager.getInstance().getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            //bug ui and data always read data, sync thread issue.
            canvas.drawBitmap(bullet.mStyleBitmap, bullet.mLocationX, bullet.mLocationY,
                    mPaint);
        }
        ArrayList<EnemyPlane> enemyPlanes = ViewDrawManager.getInstance().getEnemyPlanes();
        for (int i = 0; i < enemyPlanes.size(); i++) {
            EnemyPlane enemyPlane = enemyPlanes.get(i);
            //bug ui and data always read data, sync thread issue.
            canvas.drawBitmap(enemyPlane.mStyleBitmap, enemyPlane.mLocationX, enemyPlane.mLocationY,
                    mPaint);
        }
        SystemClock.sleep(Config.VIEW_INTERVAL_REFRESH);
        invalidate();
    }
}
