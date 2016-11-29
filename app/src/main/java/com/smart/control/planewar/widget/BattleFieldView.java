package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.view.View;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.ViewDrawManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制 67帧 每15ms刷新一次数据
 */

public class BattleFieldView extends View {

    private final Paint mPaint;
    private final Random mRandom;

    public BattleFieldView(Context context) {
        super(context);
        mPaint = new Paint();
        mRandom = new Random(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList<Element> elements = ViewDrawManager.getInstance().getElements();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            //bug ui and data always read data, sync thread issue.
            canvas.drawBitmap(element.mStyleBitmap, element.mLocationX, element.mLocationY,
                    mPaint);
        }
        SystemClock.sleep(Config.VIEW_INTERVAL_REFRESH);
        invalidate();
    }

    public void refreshEnemy(){
        //根据随机数刷新敌机
        int type = mRandom.nextInt();
        switch (type){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                break;
            case 6:
            case 7:
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }
}
