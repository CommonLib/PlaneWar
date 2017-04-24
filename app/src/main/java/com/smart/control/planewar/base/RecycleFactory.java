package com.smart.control.planewar.base;

import android.support.annotation.Nullable;

import com.smart.control.planewar.utils.LogUtil;
import com.smart.control.planewar.widget.MoveAbleElement;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by byang059 on 4/13/17.
 */

public class RecycleFactory {

    private RecycleFactory(){}

    private static final RecycleFactory factory = new RecycleFactory();
    private HashMap<String, LinkedList<RecycleAble>> recycleMap = new HashMap<>();

    public static RecycleFactory getInstance(){
        return factory;
    }

    public <T extends RecycleAble> T  getRecycleInstance(Class<T> tClass){
        String key = tClass.getName();
        LinkedList<RecycleAble> queue = recycleMap.get(key);

        if(queue == null){
            queue = new LinkedList<>();
            recycleMap.put(key, queue);
        }
        RecycleAble recycleObj = null;

        while (queue.peekLast() != null){
            recycleObj = queue.pollLast();
            if(recycleObj.isCanRecycle()){
                recycleObj.setRecycle(false);
                recycleObj.onRecycleCleanData();
                break;
                //            LogUtil.d("getRecycleInstance => "+ key +" =>cache recycleObj");
            }
        }

        if (recycleObj == null) {
            LogUtil.d("getRecycleInstance => "+ key +" =>new recycleObj");
            recycleObj = getNewRecycleObj(tClass, key);
        }

        return (T) recycleObj;
    }

    public void addElementRecycle(MoveAbleElement element){
        String key = element.getClass().getName();
        LinkedList<RecycleAble> queue = recycleMap.get(key);

        if(queue == null){
            queue = new LinkedList<>();
            recycleMap.put(key, queue);
        }
        queue.addFirst(element);
    }

    @Nullable
    private <T extends RecycleAble> T getNewRecycleObj(Class<T> tClass, String key) {
        try {
            T instance = tClass.newInstance();
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
