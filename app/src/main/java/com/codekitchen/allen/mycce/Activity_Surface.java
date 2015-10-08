package com.codekitchen.allen.mycce;


import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Surface extends AppCompatActivity implements IOnEndOfGameInterface {

    DrawTest vdt;
    Button btnSkill;

    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.inject(this);//目前有surfaceview的activity使用ButterKnife會報錯,暫時無解

        setContentView(R.layout.activity_surface);

        //取得畫面上按鈕映射對象
        vdt = (DrawTest)findViewById(R.id.olaview);
        vdt.setIOnEndOfGame(this);
        vdt.initHandler();//(重點技巧!!!!)將handler 在UI thread 初始化, 因此就可以在 SurfaceView裡面與 UI thread交流
        btnSkill = (Button)findViewById(R.id.btnSkill);
        btnSkill.setOnTouchListener(mOnClicklistenerSk1);

        //開啟背景音樂
        openBackgroundMusic();

        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.setDuration(300);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                Log.e("TAG", "cuurent value is " + currentValue);
            }
        });
        anim.start();
    }


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

    @Override
    public void onEndOfGame() {
        this.finish();
    }

    @Override
    public void showResult(String result){
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

}
