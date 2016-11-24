package com.smart.control.planewar.widget.bullet;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;

import com.smart.control.planewar.widget.Element;

/**
 * @author:dongpo 创建时间: 9/13/2016
 * 描述:
 * 修改:
 */
public abstract class Bullet extends Element implements Cloneable {
    public int mSpeed;
    public int mOriginX;
    public int mOriginY;
    public int mTargetX;
    public int mTargetY;
    public int mCurrentX;
    public int mCurrentY;
    public int refreshInterval;
    public onBulletFlyListener mBulletFlyListener;
    private boolean mIsAdd;

    public void setBulletFlyListener(onBulletFlyListener bulletFlyListener) {
        mBulletFlyListener = bulletFlyListener;
    }

    public Bullet(Context context) {
        super(context);
    }

    @Override
    public Bullet clone() {
        Bullet clone = null;
        try {
            clone = (Bullet) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("copy bullet failure");
        }
        clone.mStyleBitmap = this.mStyleBitmap;
        return clone;
    }

    public void startLaunch() {
        ValueAnimator launchAnimatorY = ValueAnimator.ofFloat(mOriginY, mTargetY);
        launchAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (mBulletFlyListener != null) {
                    mBulletFlyListener.onBulletFly(Bullet.this, mOriginX, mTargetX, mOriginY, mTargetY, mOriginX, (int) value);
                }
                //[0,-value]
                mCurrentY = (int) value;
                mCurrentX = mOriginX;
                setTop((int) value);
            }
        });

        launchAnimatorY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mBulletFlyListener != null) {
                    mBulletFlyListener.onBulletStartFly(Bullet.this);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mBulletFlyListener != null) {
                    mBulletFlyListener.onBulletFlyEnd(Bullet.this);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        launchAnimatorY.setDuration(mSpeed);
        launchAnimatorY.start();
    }

    public interface onBulletFlyListener {
        void onBulletStartFly(Bullet bullet);

        void onBulletFly(Bullet bullet, int originX, int targetX, int originY, int targetY, int currentX, int currentY);

        void onBulletFlyEnd(Bullet bullet);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(mCurrentX, mCurrentY, mCurrentX + mWidth, mCurrentY + mHeight);
    }
}
