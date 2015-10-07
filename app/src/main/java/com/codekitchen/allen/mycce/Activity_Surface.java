package com.codekitchen.allen.mycce;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_Surface extends AppCompatActivity {

    DrawTest vdt;

//    Button btnLeft;
//    Button btnRight;
//    Button btnUp;
//    Button btnDown;
    Button btnSkill;

    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.inject(this);//目前有surfaceview的activity使用ButterKnife會報錯,暫時無解

        setContentView(R.layout.activity_surface);

        //取得畫面上按鈕映射對象
        vdt = (DrawTest)findViewById(R.id.olaview);
//        btnLeft = (Button)findViewById(R.id.btnLeft);
//        btnRight = (Button)findViewById(R.id.btnRight);
//        btnUp = (Button)findViewById(R.id.btnUp);
//        btnDown = (Button)findViewById(R.id.btnDown);
        btnSkill = (Button)findViewById(R.id.btnSkill);


        //設定按鈕偵聽touch事件
//        btnLeft.setOnTouchListener(mOnTouchistenerLeft);
//        btnRight.setOnTouchListener(mOnTouchistenerRight);
//        btnUp.setOnTouchListener(mOnTouchistenerUp);
//        btnDown.setOnTouchListener(mOnTouchistenerDown);

        btnSkill.setOnTouchListener(mOnClicklistenerSk1);

        //開啟背景音樂
        openBackgroundMusic();
    }




    private View.OnTouchListener mOnTouchistenerLeft = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                vdt.settLeft(true);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                vdt.settLeft(true);
                vdt.setIsMoveing(true);
                return true;
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                vdt.setIsMoveing(false);
            }
            return false;
        }
    };


    private View.OnTouchListener mOnTouchistenerRight = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                vdt.settRight(true);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                vdt.settRight(true);
                vdt.setIsMoveing(true);
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                vdt.setIsMoveing(false);
            }
            return false;
        }
    };


    private View.OnTouchListener mOnTouchistenerUp = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                vdt.settUp(true);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                vdt.settUp(true);
                vdt.setIsMoveing(true);
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                vdt.setIsMoveing(false);
            }
            return false;
        }
    };


    private View.OnTouchListener mOnTouchistenerDown = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                vdt.settDown(true);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                vdt.settDown(true);
                vdt.setIsMoveing(true);
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                vdt.setIsMoveing(false);
            }
            return false;
        }
    };


    private View.OnTouchListener mOnClicklistenerSk1 = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //TODO
                vdt.setUseSkill1(true);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                //TODO
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                //TODO
            }
            return false;
        }
    };



    /**
     * 開啟背景音樂
     */
    public void openBackgroundMusic(){
        Log.e("openBackgroundMusic", "openBackgroundMusic");
        backgroundMusic = MediaPlayer.create(Activity_Surface.this, R.raw.tos_battle);
        backgroundMusic.setLooping(true); // Set looping
        backgroundMusic.setVolume(50,50);
        backgroundMusic.start();
    }


    @Override
    protected void onPause (){
        super.onPause();
        if(backgroundMusic!=null){
            backgroundMusic.stop();
        }
    }


    @Override
    protected void onDestroy() {
        if(backgroundMusic!=null){
            backgroundMusic.stop();
        }
        super.onDestroy();
    }


}
