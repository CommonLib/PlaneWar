package com.smart.control.planewar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smart.control.planewar.widget.GameView;

public class MainActivity extends AppCompatActivity {

    private GameView mGameMap;
    private View mCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mGameMap = (GameView) findViewById(R.id.plane_war);
        mCover = findViewById(R.id.shoot);
        mCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCover.setVisibility(View.GONE);
                mGameMap.setEnabled(true);
                mGameMap.startGame();
            }
        });
        mGameMap.initGame();
        mGameMap.setEnabled(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGameMap.pauseGame();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!mGameMap.isGamePlaying()){
            mCover.setVisibility(View.VISIBLE);
        }
    }
}
