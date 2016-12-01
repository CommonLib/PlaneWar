package com.smart.control.planewar.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.smart.control.planewar.Config;
import com.smart.control.planewar.ViewDrawManager;
import com.smart.control.planewar.widget.plane.BigEnemyPlane;
import com.smart.control.planewar.widget.plane.EnemyPlane;
import com.smart.control.planewar.widget.plane.FightPlane;
import com.smart.control.planewar.widget.plane.LittleEnemyPlane;
import com.smart.control.planewar.widget.plane.NormalEnemyPlane;

import java.util.Random;

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
    private float mLastX;
    private float mLastY;
    private BattleFieldView mBattleFieldView;
    private Random mRandom;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
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
        mRandom = new Random();
    }

    private LayoutParams getBattleFiledInitParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return params;
    }

    private LayoutParams getFightInitParams() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        return params;
    }

    public FightPlane getFightPlane() {
        return mFightPlane;
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

    public void refreshEnemy() {
        //根据随机数刷新敌机
        int randomNum = mRandom.nextInt(100);
        EnemyPlane enemyPlane;
        if (randomNum >= 0 && randomNum < Config.VALUE_LITTLE_ENEMY_PLANE) {
            //刷新小飞机
            enemyPlane = new LittleEnemyPlane(getContext());
        } else if (randomNum >= Config.VALUE_LITTLE_ENEMY_PLANE
                && randomNum < Config.VALUE_NORMAL_ENEMY_PANE) {
            //刷新中型飞机
            enemyPlane = new NormalEnemyPlane(getContext());
        } else {
            //刷新大型飞机
            enemyPlane = new BigEnemyPlane(getContext());
        }
        drawEnemyPlane(enemyPlane);
        int startX = mRandom.nextInt(mParentWidth);
        enemyPlane.fire(startX, 0, startX, mParentHeight, 0 - Config.ENEMY_SPEED_SLOW);
    }

    private void drawEnemyPlane(EnemyPlane plane) {
        ViewDrawManager.getInstance().drawEnemyPlane(plane);
    }
}
