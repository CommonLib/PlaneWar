package com.smart.control.planewar.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smart.control.planewar.OperateCalculateManager;
import com.smart.control.planewar.PlaneApplication;
import com.smart.control.planewar.PlaneConfig;
import com.smart.control.planewar.R;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.base.RecycleFactory;
import com.smart.control.planewar.widget.plane.BigEnemyPlane;
import com.smart.control.planewar.widget.plane.EnemyPlane;
import com.smart.control.planewar.widget.plane.FightPlane;
import com.smart.control.planewar.widget.plane.LittleEnemyPlane;
import com.smart.control.planewar.widget.plane.NormalEnemyPlane;
import com.smart.control.planewar.widget.plane.Plane;

import java.util.ArrayList;
import java.util.Random;

import static com.smart.control.planewar.R.mipmap.bg;

/**
 * Created by byang059 on 11/24/16.
 * 存在子弹 敌机 弹药，根据他们的坐标进行绘制 67帧 每15ms刷新一次数据
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private int mCanvasWidth;
    private int mCanvasHeight;
    private Bitmap mScreenShot;
    private float mLastX;
    private float mLastY;
    private int mParentWidth;
    private int mParentHeight;
    private int mFightPlaneHorizontalRange;
    private int mFightPlaneVerticalRange;
    private FightPlane mFightPlane;
    private Random mRandom;
    public volatile static boolean isGameContinue = false;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //初始化飞机
        mFightPlane = new FightPlane();
        mRandom = new Random();
        mPaint = new Paint();
        holder = this.getHolder();
        holder.addCallback(this);
        mDrawThread = new DrawThread(holder);//创建一个绘图线程
        setBackgroundResource(bg);
        setZOrderOnTop(true);//使surfaceview放到最顶层
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        post(new Runnable() {
            @Override
            public void run() {
                mParentHeight = getHeight();
                mParentWidth = getWidth();
                mFightPlaneHorizontalRange = mParentWidth - mFightPlane.mWidth;
                mFightPlaneVerticalRange = mParentHeight - mFightPlane.mHeight;
                mFightPlane.mLocationX = mFightPlaneHorizontalRange / 2;
                mFightPlane.mLocationY = mFightPlaneVerticalRange * 0.9f;
            }
        });
    }

    private SurfaceHolder holder;
    private DrawThread mDrawThread;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (mScreenShot != null) {
            setBackground(new BitmapDrawable(mScreenShot));
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        GameView.isGameContinue = false;
    }


    private void drawGame(Canvas canvas) {
        Plane plane = ViewDrawManager.getInstance().getPlane();
        canvas.drawBitmap(plane.mStyleBitmap, plane.mLocationX, plane.mLocationY, mPaint);

        drawElements(canvas, OperateCalculateManager.getInstance().getEnemyPlanes());
        drawElements(canvas, OperateCalculateManager.getInstance().getBullets());
    }

    private void drawElements(Canvas canvas, ArrayList<? extends Element> elements) {
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            if (!element.isDestroy()) {
                canvas.drawBitmap(element.mStyleBitmap, element.mLocationX, element.mLocationY,
                        mPaint);

            }
        }
    }

    //线程内部类
    private class DrawThread extends Thread {
        private SurfaceHolder holder;

        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        @Override
        public void run() {
            while (true) {
                if (!GameView.isGameContinue) {
                    mScreenShot = saveScreenShot();
                    try {
                        synchronized (GameView.class) {
                            GameView.class.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (holder) {
                    Canvas c = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
                    if (c != null) {
                        mCanvasWidth = c.getWidth();
                        mCanvasHeight = c.getHeight();
                        c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        drawGame(c);
                        holder.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                    }
                }
                SystemClock.sleep(PlaneConfig.VIEW_INTERVAL_REFRESH);
            }
        }
    }

    private Bitmap saveScreenShot() {
        if (mCanvasHeight <= 0 || mCanvasWidth <= 0) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(mCanvasWidth, mCanvasHeight, Bitmap.Config.ARGB_8888);
        Bitmap bg = BitmapFactory
                .decodeResource(PlaneApplication.getInstance().getResources(), R.mipmap.bg);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bg, 0, 0, mPaint);
        Plane plane = ViewDrawManager.getInstance().getPlane();
        canvas.drawBitmap(plane.mStyleBitmap, plane.mLocationX, plane.mLocationY, mPaint);
        drawGame(canvas);
        return bitmap;
    }

    private int reCorrectLeftPosition(int left, int dx) {
        if (left > 0 && left + dx <= 0) {
            return -left;
        } else if (left == 0 && dx < 0) {
            return 0;
        } else if (left < mFightPlaneHorizontalRange && left + dx >= mFightPlaneHorizontalRange) {
            return mFightPlaneHorizontalRange - left;
        } else if (left == mFightPlaneHorizontalRange && dx > 0) {
            return 0;
        }
        return dx;
    }

    private int reCorrectTopPosition(int top, int dy) {
        if (top > 0 && top + dy <= 0) {
            return -top;
        } else if (top == 0 && dy < 0) {
            return 0;
        } else if (top < mFightPlaneVerticalRange && top + dy >= mFightPlaneVerticalRange) {
            return mFightPlaneVerticalRange - top;
        } else if (top == mFightPlaneVerticalRange && dy > 0) {
            return 0;
        }
        return dy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handle = super.onTouchEvent(event);
        if (handle) {
            return true;
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = event.getX();
                float currentY = event.getY();

                float diffX = currentX - mLastX;
                float diffY = currentY - mLastY;

                int left = (int) mFightPlane.mLocationX;
                int top = (int) mFightPlane.mLocationY;

                int dx = reCorrectLeftPosition(left, (int) diffX);
                int dy = reCorrectTopPosition(top, (int) diffY);

                mFightPlane.mLocationX = left + dx;
                mFightPlane.mLocationY = top + dy;

                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    public void refreshEnemy() {
        //根据随机数刷新敌机
        int randomNum = mRandom.nextInt(100);
        EnemyPlane enemyPlane;
        if (randomNum >= 0 && randomNum < PlaneConfig.VALUE_LITTLE_ENEMY_PLANE) {
            //刷新小飞机
            enemyPlane = RecycleFactory.getInstance().getRecycleInstance(LittleEnemyPlane.class);
        } else if (randomNum >= PlaneConfig.VALUE_LITTLE_ENEMY_PLANE && randomNum
                < PlaneConfig.VALUE_LITTLE_ENEMY_PLANE + PlaneConfig.VALUE_NORMAL_ENEMY_PANE) {
            //刷新中型飞机
            enemyPlane = RecycleFactory.getInstance().getRecycleInstance(NormalEnemyPlane.class);
        } else {
            //刷新大型飞机
            enemyPlane = RecycleFactory.getInstance().getRecycleInstance(BigEnemyPlane.class);
        }
        int startX = mRandom.nextInt(mParentWidth);
        enemyPlane.fire(startX, -50, startX, mParentHeight, 0 - PlaneConfig.ENEMY_SPEED_SLOW);
    }

    public void initGame() {
        ShootLoop mShootRunnable = new ShootLoop();
        EnemyLoop mEnemyRunnable = new EnemyLoop();

        mDrawThread.start();
        OperateCalculateManager.getInstance().getCalculateThread().start();
        new Thread(mEnemyRunnable).start();
        new Thread(mShootRunnable).start();
        ViewDrawManager.getInstance().drawPlane(mFightPlane);
    }

    public boolean isGamePlaying() {
        return isGameContinue;
    }

    public void pauseGame() {
        isGameContinue = false;
    }

    public void startGame() {
        setBackgroundResource(R.mipmap.bg);
        synchronized (GameView.class) {
            isGameContinue = true;
            GameView.class.notifyAll();
        }
    }

    public class ShootLoop implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (!GameView.isGameContinue) {
                    try {
                        synchronized (GameView.class) {
                            GameView.class.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mFightPlane.shootBullet();
                SystemClock.sleep(mFightPlane.attackInterval);
            }
        }
    }

    public class EnemyLoop implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (!GameView.isGameContinue) {
                    try {
                        synchronized (GameView.class) {
                            GameView.class.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                refreshEnemy();
                SystemClock.sleep(PlaneConfig.ENEMY_REFRESH_INTERVAL);
            }
        }
    }


}
