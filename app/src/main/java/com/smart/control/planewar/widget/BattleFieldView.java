package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制
 */

public class BattleFieldView extends Element {
    public ArrayList<Element> mElements = new ArrayList<>();

    public BattleFieldView(Context context) {
        super(context);
    }

    public void drawElement(Element element){
        if(mElements.contains(element)){
            return;
        }
        mElements.add(element);
    }

    public void removeElememt(Element element){
        mElements.remove(element);
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }
}
