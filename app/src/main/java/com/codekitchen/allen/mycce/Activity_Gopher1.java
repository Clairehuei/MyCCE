package com.codekitchen.allen.mycce;


import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_Gopher1 extends AppCompatActivity {

    private ImageView[] imageViewList;
    private TextView textView;
    private int[] gopher;
    private int[] gopher2;
    private boolean play;
    private Handler handler;
    private GopherSprite[] glist;
    private RunGopherSprite[] rglist;
    private SoundPool soundPool;
    private int touchId;
    private int score;


    //以下設置連點參數
    private final int DOUBLE_TAP_TIMEOUT = 200;
    private MotionEvent mCurrentDownEvent;
    private MotionEvent mPreviousUpEvent;

    int a0 = 0;
    int a1 = 1;
    int a2 = 2;
    int a3 = 3;
    int a4 = 4;
    int a5 = 5;


    //設置難度
    boolean isChangeHole = false;


    @InjectView(R.id.btnStartGame)
    Button btnStartGame;

    @InjectView(R.id.imageView)
    ImageView imageView;

    @InjectView(R.id.imageView2)
    ImageView imageView2;

    @InjectView(R.id.imageView3)
    ImageView imageView3;

    @InjectView(R.id.imageView4)
    ImageView imageView4;

    @InjectView(R.id.imageView5)
    ImageView imageView5;

    @InjectView(R.id.imageView6)
    ImageView imageView6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gopher1);
        ButterKnife.inject(this);

        imageViewList = new ImageView[] {
                imageView,
                imageView2,
                imageView3,
                imageView4,
                imageView5,
                imageView6
        };

        textView = (TextView) findViewById(R.id.textView);//imageView2

        // 建立普通地鼠輪詢陣列
        gopher = new int[] {
                R.drawable.hole,
                R.drawable.mole1,
                R.drawable.mole2,
                R.drawable.mole3,
                R.drawable.mole2,
                R.drawable.mole1,
                R.drawable.hole};

        // 建立頭盔地鼠輪詢陣列
        gopher2 = new int[] {
                R.drawable.hole,
                R.drawable.smole1,
                R.drawable.smole2,
                R.drawable.smole3,
                R.drawable.smole2,
                R.drawable.smole1,
                R.drawable.hole};

        handler = new Handler();
        // 建立音效池
        buildSoundPool();
        // 建立地鼠遊戲物件與註冊 onTouch 監聽器
//        glist = new GopherSprite[6];
        rglist = new RunGopherSprite[6];

//        for(int i=0;i<glist.length;i++) {
//            if(i==0 || i==2 || i==4){
//                glist[i] = new GopherSprite(imageViewList[i], i);
//                imageViewList[i].setOnTouchListener(new GopherOnTouchListener(glist[i]));//設定單次點擊
//            }else{
//                glist[i] = new SuperGopherSprite(imageViewList[i], i);
//                imageViewList[i].setOnTouchListener(new GopherOnDoubleTouchListener((SuperGopherSprite) glist[i]));//設定雙擊
//            }
//        }

        for(int i=0;i<rglist.length;i++) {
            if(i==0 || i==2 || i==4){
                SampleGopherSprite s1 = new SampleGopherSprite(imageViewList[i], i);
                rglist[i] = new RunGopherSprite(imageViewList[i], i, s1);
                imageViewList[i].setOnTouchListener(new GopherOnTouchListener(rglist[i]));//設定單次點擊
            }else{
                SampleGopherSprite s2 = new HatGopherSprite(imageViewList[i], i);
                rglist[i] = new RunGopherSprite(imageViewList[i], i, s2);
                imageViewList[i].setOnTouchListener(new GopherOnDoubleTouchListener(rglist[i]));//設定雙擊
            }
        }

    }


    //普通地鼠
    private class SampleGopherSprite {
        ImageView imageView;
        int idx;
        boolean hit;
        int gNumber;
        int type = 0;
        int[] gopher =new int[] {
                R.drawable.hole,
                R.drawable.mole1,
                R.drawable.mole2,
                R.drawable.mole3,
                R.drawable.mole2,
                R.drawable.mole1,
                R.drawable.hole};

        private SampleGopherSprite() {
        }

        SampleGopherSprite(ImageView imageView, int number) {
            this.imageView = imageView;
            this.gNumber = number;
        }
    }


    //帽子地鼠
    private class HatGopherSprite extends SampleGopherSprite {
        HatGopherSprite(ImageView imageView, int number) {
            this.imageView = imageView;
            this.gNumber = number;
            this.type = 1;
            this.gopher = new int[] {
                    R.drawable.hole,
                    R.drawable.smole1,
                    R.drawable.smole2,
                    R.drawable.smole3,
                    R.drawable.smole2,
                    R.drawable.smole1,
                    R.drawable.hole};
        }
    }


    //執行地鼠
    private class RunGopherSprite implements Runnable {
        ImageView imageView;
        int idx;
        int n;
        boolean hit;
        SampleGopherSprite g;

        RunGopherSprite(ImageView imageView, int n) {
            this.imageView = imageView;
            this.n = n;
        }

        RunGopherSprite(ImageView imageView, int n, SampleGopherSprite g) {
            this.imageView = imageView;
            this.n = n;
            this.g = g;
        }

        @Override
        public void run() {
            draw();
        }

        private void draw() {
            if(!play) {
                return;
            }
            int type = g.type;

            if(hit) {

                if(type==0){
                    imageView.setImageResource(R.drawable.mole4);
                }else if(type==1){
                    imageView.setImageResource(R.drawable.smole4);
                }

                hit = false;
                idx = 0;


                handler.postDelayed(this, 1000);
            } else {
                idx = idx % this.g.gopher.length;

                imageView.setImageResource(this.g.gopher[idx]);
                int n = (int)(Math.random() * 1000) % 3 + 1;
                if(idx==6){
                    this.g = changeGopherSprite(imageView, n);
                }

                handler.postDelayed(this, (n*100));
                idx = ++idx % this.g.gopher.length;
            }
        }
    }


    //改變當前地鼠類型
    public SampleGopherSprite changeGopherSprite(ImageView iv, int number){
        SampleGopherSprite g ;
        int i = new Random().nextInt(2);
        if(i==0){
            g = new SampleGopherSprite(iv, number);
        }else if(i==1){
            g = new HatGopherSprite(iv, number);
        }else{
            g = null;
        }
        return g;
    }


    //普通地鼠
    private class GopherSprite implements Runnable {
        ImageView imageView;
        int idx;
        boolean hit;
        int gNumber;
        int type = 0;

        private GopherSprite() {
        }

        GopherSprite(ImageView imageView, int number) {
            this.imageView = imageView;
            this.gNumber = number;
        }

        @Override
        public void run() {
            draw();
        }

        private void draw() {
            if(!play) {
                return;
            }
            if(hit) {
                imageView.setImageResource(R.drawable.mole4);
                hit = false;
                idx = 0;
                changeHole(this);

                handler.postDelayed(this, 1000);
            } else {
                idx = idx % gopher.length;
                imageView.setImageResource(gopher[idx]);
                int n = (int)(Math.random() * 1000) % 3 + 1;
                if(idx==6){
                    changeHole(this);
                }


                handler.postDelayed(this, (n*100));

                idx = ++idx % gopher.length;
            }
        }
    }

    //頭盔地鼠
    private class SuperGopherSprite extends GopherSprite {

        SuperGopherSprite(ImageView imageView, int number) {
            this.imageView = imageView;
            this.gNumber = number;
            this.type = 1;
        }

        @Override
        public void run() {
            draw();
        }

        private void draw() {
            if(!play) {
                return;
            }
            if(hit) {
                imageView.setImageResource(R.drawable.smole4);
                hit = false;
                idx = 0;
                changeHole(this);
                handler.postDelayed(this, 1000);
            } else {
                idx = idx % gopher2.length;
                imageView.setImageResource(gopher2[idx]);
                int n = (int)(Math.random() * 1000) % 3 + 1;
                if(idx==6){
                    changeHole(this);
                }
                handler.postDelayed(this, (n*100));
                idx = ++idx % gopher2.length;
            }
        }
    }


    //單次點擊偵聽物件
    private class GopherOnTouchListener implements View.OnTouchListener {
        GopherSprite g;
        GopherOnTouchListener(GopherSprite g) {
            this.g = g;
        }


        RunGopherSprite rg;
        GopherOnTouchListener(RunGopherSprite rg) {
            this.rg = rg;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            if(play && event.getAction() == MotionEvent.ACTION_DOWN) {
//                if(gopher[g.idx] == R.drawable.mole2 ||
//                   gopher[g.idx] == R.drawable.mole3) {
//                    g.hit = true;
//                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
//                    textView.setText(String.valueOf(++score));
//                } else {
//                    textView.setText(String.valueOf(--score));
//                }
//            }

            if(play && event.getAction() == MotionEvent.ACTION_DOWN) {
                if(rg.g.gopher[rg.idx] == R.drawable.mole2 ||
                        rg.g.gopher[rg.idx] == R.drawable.mole3) {
                    rg.hit = true;
                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
                    textView.setText(String.valueOf(++score));
                } else {
                    textView.setText(String.valueOf(--score));
                }
            }

            return false;
        }
    }


    //雙點擊偵聽物件
    private class GopherOnDoubleTouchListener implements View.OnTouchListener {

        SuperGopherSprite g;
        GopherOnDoubleTouchListener(SuperGopherSprite g) {
            this.g = g;
        }


        RunGopherSprite rg;
        GopherOnDoubleTouchListener(RunGopherSprite rg) {
            this.rg = rg;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
//            if (play && event.getAction() == MotionEvent.ACTION_DOWN) {
//
//                if ((gopher2[g.idx] == R.drawable.smole2 || gopher2[g.idx] == R.drawable.smole3) &&
//                        mPreviousUpEvent != null  && mCurrentDownEvent != null  && isConsideredDoubleTap(mCurrentDownEvent, mPreviousUpEvent, event)) {
//                    g.hit = true;
//                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
//                    textView.setText(String.valueOf(++score));
//                }else {
//                    //textView.setText(String.valueOf(--score));
//                }
//
//                mCurrentDownEvent = MotionEvent.obtain(event);
//                return true;
//            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                mPreviousUpEvent = MotionEvent.obtain(event);
//                return true;
//            }


            if (play && event.getAction() == MotionEvent.ACTION_DOWN) {

                if ((rg.g.gopher[rg.idx] == R.drawable.smole2 || rg.g.gopher[rg.idx] == R.drawable.smole3) &&
                        mPreviousUpEvent != null  && mCurrentDownEvent != null  && isConsideredDoubleTap(mCurrentDownEvent, mPreviousUpEvent, event)) {
                    rg.hit = true;
                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
                    textView.setText(String.valueOf(++score));
                }else {
                    //textView.setText(String.valueOf(--score));
                }

                mCurrentDownEvent = MotionEvent.obtain(event);
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mPreviousUpEvent = MotionEvent.obtain(event);
                return true;
            }

            return false;
        }
    }


    //變更地鼠所在的洞穴以及變更新洞穴的onTouch事件
    public void changeHole(GopherSprite g){
        if(isChangeHole){
            Log.e("gNumber=","=="+g.gNumber+"==");
            //1.取消註冊(自己當前的洞穴)
            cancleRegitHole(g);

            //2.取得當前空的洞穴
            List<ImageView> li = getNholes();

            //3.隨機註冊一個空的洞穴
            ImageView iv = getOneHole(li);
            g.imageView = iv;
            if(g.type==0){//普通地鼠
                iv.setOnTouchListener(new GopherOnTouchListener(g));
            }else if(g.type==1){//頭盔地鼠
                iv.setOnTouchListener(new GopherOnDoubleTouchListener((SuperGopherSprite)g));
            }
        }
    }


    //取消註冊(自己當前的洞穴)
    public void cancleRegitHole(GopherSprite g){

    }


    //取得當前所有空的洞穴
    public List<ImageView> getNholes(){
        List<ImageView> li = new ArrayList();



        return li;
    }


    //從傳入的洞穴中隨機取得一個洞穴
    public ImageView getOneHole(List<ImageView> li){
        Random r = new Random();
        return li.get(r.nextInt(li.size()));
    }



    /**
     * 判斷是否當前為連點動作
     * @param firstDown
     * @param firstUp
     * @param secondDown
     * @return
     */
    private boolean isConsideredDoubleTap(MotionEvent firstDown, MotionEvent firstUp, MotionEvent secondDown) {
        if (secondDown.getEventTime() - firstUp.getEventTime() > DOUBLE_TAP_TIMEOUT) {
            return false;
        }
        int deltaX = (int) firstUp.getX() - (int) secondDown.getX();
        int deltaY = (int) firstUp.getY() - (int) secondDown.getY();
        boolean b = deltaX * deltaX + deltaY * deltaY < 10000;

        return b;
    }



    // 建立音效池
    private void buildSoundPool() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attr = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(attr)
                    .build();
        } else {
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        }
        touchId = soundPool.load(this, R.raw.touch, 1);

    }


    @OnClick(R.id.btnStartGame)
    void fourNineTryClick(){
        startGame();
    }


    public void startGame(){
        play = true;
        score = 0;
        textView.setText("0");
        //item.setEnabled(false);
        btnStartGame.setEnabled(false);
        new CountDownTimer(20*1000, 1000){
            @Override
            public void onFinish() {
                play = false;//時間到,停止遊戲
                //item.setEnabled(true);
                btnStartGame.setEnabled(true);
                setTitle("剩餘時間：0");

                showResult();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                setTitle("剩餘時間：" + millisUntilFinished/1000);
            }
        }.start();
//        for(GopherSprite g : glist) {
//            handler.post(g);
//        }
        //rglist
        for(RunGopherSprite g : rglist) {
            handler.post(g);
        }
    }


    /**
     * 顯示結果
     */
    public void showResult(){
        String s = textView.getText().toString();
        int is = Integer.parseInt(s);
        String result = "你的分數是" + s;
        String comment = "";
        if(is<0){
            comment = "已無力吐槽...";
        }else if(is==0){
            comment = "嗨~遜咖 A_A(@m)";
        }else if(is>=1 && is<=3){
            comment = "小學生都比你強";
        }else if(is>=4 && is<=6){
            comment = "馬馬虎虎啦";
        }else if(is>=7 && is<=9){
            comment = "唉唷~不錯喔";
        }else if(is>=10 && is<=12){
            comment = "豪逆害＼(^o^)／";
        }else if(is>=13 && is<=15){
            comment = "加藤鷹 是你 @o@?";
        }else if(is>16){
            comment = "幹你用外掛喔!?";
        }


        // 建立 MyDialogFragment
        MyDialogFragment dialogfragment = new MyDialogFragment();
        // 建立 Bundle
        Bundle args = new Bundle();
        args.putString("result", result+"   "+comment);
        dialogfragment.setArguments(args);
        // 顯示 MyDialogFragment
        dialogfragment.show(getFragmentManager(), "dialog");
    }



}
