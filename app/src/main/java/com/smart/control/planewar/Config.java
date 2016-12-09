package com.smart.control.planewar;

/**
 */
public class Config {

    /**
     * 飞机攻击间隔时间 单位：次/ms
     */
    public static final int SPEED_Fast = 100;
    public static final int SPEED_MIDDLE = 200;
    public static final int SPEED_LOW = 400;

    /**
     * 子弹飞行速度 单位:在数据刷新间隔时间间隔 子弹飞行的像素数 7.2/10ms
     */
    public static final float BULLET_SPEED_SLOW = 7.5f;
    /**
     *
     */
    public static final float BULLET_SPEED_FAST = 15f;

    /**
     * 数据刷新间隔时间
     */
    public static final int DATA_INTERVAL_REFRESH = 5;
    /**
     * View刷新间隔时间
     */
    public static final int VIEW_INTERVAL_REFRESH = 5;

    /**
     * 刷新敌机概率值
     */
    public static final int VALUE_LITTLE_ENEMY_PLANE = 50;
    public static final int VALUE_NORMAL_ENEMY_PANE = 30;
    public static final int VALUE_BIG_ENEMY_PLANE = 20;
    /**
     * 增强武器刷新间隔 单位：秒
     */
    public static final int VALUE_STRENGTHEN_WEAPON = 15;

    /**
     * 敌军刷新间隔 单位：秒
     */
    public static final int ENEMY_REFRESH_INTERVAL = 500;

    /**
     * 子弹飞行速度 单位:在数据刷新间隔时间间隔 子弹飞行的像素数 7.2/10ms
     */
    public static final float ENEMY_SPEED_SLOW = 2f;
    /**
     *
     */
    public static final float ENEMY_SPEED_FAST = 4f;

}
