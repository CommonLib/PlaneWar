package com.smart.control.planewar.base;

import android.support.annotation.Nullable;
import android.util.Log;

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

        RecycleAble recycleObj = queue.peekLast();
        if (recycleObj != null && recycleObj.isCanRecycle()) {
            queue.removeLast();
            recycleObj.setRecycle(false);
            recycleObj.onRecycleCleanData();
            Log.d("Log_text", "getRecycleInstance => "+ key +" =>cache recycleObj");
        } else {
            Log.d("Log_text", "getRecycleInstance => "+ key +" =>new recycleObj");
            recycleObj = getNewRecycleObj(tClass, key);
        }
        queue.addFirst(recycleObj);

        return (T) recycleObj;
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
