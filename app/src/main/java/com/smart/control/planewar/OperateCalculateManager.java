package com.smart.control.planewar;

import android.os.Handler;
import android.os.Looper;

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

    public void calculate(final ControlRunnable runnable) {
        Runnable calculateRun = new Runnable() {
            @Override
            public void run() {
                runnable.run();
                if(runnable.isGoOn){
                    mCalculateHandler.postDelayed(this, 15);
                }
            }
        };
        mCalculateHandler.post(calculateRun);
    }
}
