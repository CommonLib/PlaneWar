package com.smart.control.planewar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smart.control.planewar.widget.GameView;
import com.smart.control.planewar.widget.plane.FightPlane;

public class MainActivity extends AppCompatActivity {

    private GameView mGameMap;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OperateCalculateManager.getInstance();
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mGameMap = (GameView) findViewById(R.id.plane_war);
        findViewById(R.id.shoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FightPlane fightPlane = mGameMap.getFightPlane();
                ViewDrawManager.getInstance().drawPlane(fightPlane);
                mHandler.postDelayed(new ShootLoop(), fightPlane.attackInterval);
                mHandler.post(new EnemyLoop());
                mGameMap.startGame();
            }
        });
    }

    public class ShootLoop implements Runnable {
        @Override
        public void run() {
            FightPlane fightPlane = mGameMap.getFightPlane();
            fightPlane.shootBullet();
            mHandler.postDelayed(this, fightPlane.attackInterval);
        }
    }

    public class EnemyLoop implements Runnable {
        @Override
        public void run() {
            mGameMap.refreshEnemy();
            mHandler.postDelayed(this, Config.ENEMY_REFRESH_INTERVAL);
        }
    }

}
