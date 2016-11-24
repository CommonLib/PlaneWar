package com.smart.control.planewar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.smart.control.planewar.BulletQueue;
import com.smart.control.planewar.widget.bullet.Bullet;
import com.smart.control.planewar.widget.plane.FightPlane;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public class GameView extends FrameLayout {

    private FightPlane mFightPlane;
    private int mFightPlaneHorizontalRange;
    private int mFightPlaneVerticalRange;
    private int mParentWidth;
    private int mParentHeight;
    private BulletQueue mBulletQueue;
    private float mLastX;
    private float mLastY;
    private BattleFieldView mBattleFieldView;


    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBulletQueue = new BulletQueue();
        //初始化飞机
        mFightPlane = new FightPlane(getContext());
        addView(mFightPlane, getFightInitParams());
        //初始化战场
        mBattleFieldView = new BattleFieldView(getContext());
        addView(mBattleFieldView, getBattleFiledInitParams());
        post(new Runnable() {
            @Override
            public void run() {
                mParentHeight = getHeight();
                mParentWidth = getWidth();
                mFightPlaneHorizontalRange = mParentWidth - mFightPlane.mWidth;
                mFightPlaneVerticalRange = mParentHeight - mFightPlane.mHeight;
            }
        });
    }

    private LayoutParams getBattleFiledInitParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams
                .MATCH_PARENT);
        return params;
    }

    private LayoutParams getFightInitParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        return params;
    }

    /**
     * 让一个战机发射子弹
     */
    public void shootBullet(FightPlane targetPlane) {
        shootBullet(targetPlane, targetPlane.mBullet);
    }

    public FightPlane getFightPlane() {
        return mFightPlane;
    }

    /**
     * 让一个战机发射子弹
     */
    public void shootBullet(FightPlane targetPlane, Bullet bullet) {
        //获取当前战机的位置，在当前位置发射子弹
        targetPlane.setBullet(bullet);
        Bullet benchFiredBullet = mBulletQueue.getBenchFiredBullet();
        Bullet fireBullet = null;
        if (benchFiredBullet != null) {
            fireBullet = targetPlane.shootBullet(benchFiredBullet);
            Log.d("game", "缓存的子弹");
        } else {
            fireBullet = targetPlane.shootBullet();
            Log.d("game", "新子弹");
            addView(fireBullet);
        }
        mBulletQueue.add(fireBullet);
        fireBullet.setVisibility(INVISIBLE);
        fireBullet.startLaunch();
        fireBullet.setBulletFlyListener(new Bullet.onBulletFlyListener() {
            @Override
            public void onBulletStartFly(Bullet bullet) {
                bullet.setVisibility(VISIBLE);
            }

            @Override
            public void onBulletFly(Bullet bullet, int originX, int targetX, int originY, int targetY, int currentX, int currentY) {
                //判断是否击中，如果击中则从正在飞行的集合中拿出，放入备用集合中
            }

            @Override
            public void onBulletFlyEnd(Bullet bullet) {
                bullet.setVisibility(INVISIBLE);
                mBulletQueue.remove(bullet);
                mBulletQueue.addFiredBullet(bullet);
            }
        });
    }


    public boolean isBulletHit() {
        return false;
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
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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

                int left = mFightPlane.getLeft();
                int top = mFightPlane.getTop();

                int dx = reCorrectLeftPosition(left, (int) diffX);
                int dy = reCorrectTopPosition(top, (int) diffY);

                mFightPlane.mLocationX = left + dx;
                mFightPlane.mLocationY = top + dx;

                mFightPlane.offsetLeftAndRight(dx);
                mFightPlane.offsetTopAndBottom(dy);

                mLastX = currentX;
                mLastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}
