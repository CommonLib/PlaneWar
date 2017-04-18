package com.smart.control.planewar;

import android.os.SystemClock;

import com.smart.control.planewar.widget.GameView;
import com.smart.control.planewar.widget.MoveAbleElement;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by byang059 on 11/25/16.
 */

public class OperateCalculateManager {

    public static final OperateCalculateManager manager = new OperateCalculateManager();
    private final Thread mThread;
    private HashSet<MoveAbleElement> mElements = new HashSet<>();

    public OperateCalculateManager() {
        //开启线程，处理数据
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(!GameView.isGameContinue){
                        try {
                            synchronized(GameView.class) {
                                GameView.class.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    synchronized (manager){
                        Iterator<MoveAbleElement> iterator = mElements.iterator();
                        while (iterator.hasNext()) {
                            MoveAbleElement element = iterator.next();
                            element.calculateTime();
                            if (!element.isOutOfScreen) {
                                iterator.remove();
                            }
                        }
                    }
                    SystemClock.sleep(PlaneConfig.DATA_INTERVAL_REFRESH);
                }
            }
        });
    }

    public Thread getCalculateThread(){
        return mThread;
    }

    public static OperateCalculateManager getInstance() {
        return manager;
    }

    public void startCalculate(final MoveAbleElement element) {
        synchronized (manager){
            mElements.add(element);
        }
    }
}
