package com.smart.control.planewar;

import com.smart.control.planewar.widget.Element;

import java.util.ArrayList;

/**
 * Created by byang059 on 11/25/16.
 */

public class ViewDrawManager {
    public static ViewDrawManager manager = new ViewDrawManager();
    private ArrayList<Element> mElements = new ArrayList<>();

    public static ViewDrawManager getInstance(){
        return manager;
    }

    public ArrayList<Element> getElements(){
        return mElements;
    }

    public void drawElement(Element element) {
        if (mElements.contains(element)) {
            return;
        }
        mElements.add(element);
    }

    public void removeElememt(Element element) {
        mElements.remove(element);
    }
}
