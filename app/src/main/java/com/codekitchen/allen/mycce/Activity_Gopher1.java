package com.codekitchen.allen.mycce;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_Gopher1 extends AppCompatActivity {

    private ImageView[] imageViewList;
    private TextView textView;
    private int[] gopher;
    private boolean play;
    private Handler handler;
    private GopherSprite[] glist;
    private SoundPool soundPool;
    private int touchId;
    private int score;

    @InjectView(R.id.btnStartGame)
    Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gopher1);
        ButterKnife.inject(this);

        imageViewList = new ImageView[] {
                (ImageView) findViewById(R.id.imageView),
                (ImageView) findViewById(R.id.imageView2),
                (ImageView) findViewById(R.id.imageView3),
                (ImageView) findViewById(R.id.imageView4),
                (ImageView) findViewById(R.id.imageView5),
                (ImageView) findViewById(R.id.imageView6)
        };

        textView = (TextView) findViewById(R.id.textView);

        // 建立地鼠輪詢陣列
        gopher = new int[] {
                R.drawable.hole,
                R.drawable.mole1,
                R.drawable.mole2,
                R.drawable.mole3,
                R.drawable.mole2,
                R.drawable.mole1,
                R.drawable.hole};

        handler = new Handler();
        // 建立音效池
        buildSoundPool();
        // 建立地鼠遊戲物件與註冊 onTouch 監聽器
        glist = new GopherSprite[6];
        for(int i=0;i<glist.length;i++) {
            glist[i] = new GopherSprite(imageViewList[i]);
            imageViewList[i].setOnTouchListener(new GopherOnTouchListener(glist[i]));
        }
    }


    private class GopherSprite implements Runnable {
        ImageView imageView;
        int idx;
        boolean hit;

        GopherSprite(ImageView imageView) {
            this.imageView = imageView;
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
                handler.postDelayed(this, 1000);
            } else {
                idx = idx % gopher.length;
                imageView.setImageResource(gopher[idx]);
                int n = (int)(Math.random() * 1000) % 3 + 1;
                handler.postDelayed(this, (n*100));
                idx = ++idx % gopher.length;
            }
        }
    }

    private class GopherOnTouchListener implements View.OnTouchListener {
        GopherSprite g;
        GopherOnTouchListener(GopherSprite g) {
            this.g = g;
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(play && event.getAction() == MotionEvent.ACTION_DOWN) {
                if(gopher[g.idx] == R.drawable.mole2 ||
                   gopher[g.idx] == R.drawable.mole3) {
                    g.hit = true;
                    soundPool.play(touchId, 1.0F, 1.0F, 0, 0, 1.0F);
                    textView.setText(String.valueOf(++score));
                } else {
                    textView.setText(String.valueOf(--score));
                }
            }
            return false;
        }
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
                play = false;
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
        for(GopherSprite g : glist) {
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
            comment = "小學生都比你強";
        }else if(is>=1 && is<=3){
            comment = "嗨~遜咖 A_A(@m)";
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
