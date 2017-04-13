package com.smart.control.planewar;

import android.os.Handler;
import android.os.Looper;

import com.smart.control.planewar.widget.MoveAbleElement;

/**
 * Created by byang059 on 11/25/16.
 */

public class OperateCalculateManager {

    public static OperateCalculateManager manager = new OperateCalculateManager();
    private final Thread mThread;
    private Handler mCalculateHandler;

    public OperateCalculateManager() {
        //开启线程，处理数据
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mCalculateHandler = new Handler();
                Looper.loop();
            }
        });
        mThread.start();
    }

    public static OperateCalculateManager getInstance() {
        return manager;
    }

    public void startCalculate(final MoveAbleElement element) {
        Runnable calculateRun = new Runnable() {
            @Override
            public void run() {
                element.calculateTime();
                if (!element.isOutOfScreen) {
                    mCalculateHandler.postDelayed(this, Config.DATA_INTERVAL_REFRESH);
                }
            }
        };
        mCalculateHandler.post(calculateRun);
    }
}
