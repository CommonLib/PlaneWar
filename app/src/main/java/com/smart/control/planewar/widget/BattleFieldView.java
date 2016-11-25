package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.FrameLayout;

import com.smart.control.planewar.ViewDrawManager;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制 67帧 每15ms刷新一次数据
 */

public class BattleFieldView extends FrameLayout {

    private final Paint mPaint;

    public BattleFieldView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ArrayList<Element> elements = ViewDrawManager.getInstance().getElements();
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            canvas.drawBitmap(element.getElementIcon(), element.mLocationX, element.mLocationY,
                    mPaint);
        }
        invalidate();
    }
}
