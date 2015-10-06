package com.codekitchen.allen.mycce;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Activity_Pili_Main extends AppCompatActivity {

    private Handler handler;

    @InjectView(R.id.bossimageView)
    ImageView bossimageView;

    RunBoss rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__pili__main);
        ButterKnife.inject(this);

        handler = new Handler();

        rb = new RunBoss(bossimageView, 1, new Boss(bossimageView));

        handler.post(rb);
    }

    //腳色
    private class PiliRole {
        ImageView imageView;
        int idx;
        boolean hit;
        int type = 0;
        int[] imgList ;

        private PiliRole() {
        }

        PiliRole(ImageView imageView) {
            this.imageView = imageView;
        }
    }


    //魔王
    private class Boss extends PiliRole {

        Boss(ImageView imageView) {
            this.imageView = imageView;
            this.type = 1;
            this.imgList = new int[] {
                    R.drawable.boss1,
                    R.drawable.boss2,
                    R.drawable.boss3,
                    R.drawable.boss4,
                    R.drawable.boss3,
                    R.drawable.boss2,
                    R.drawable.boss1};
        }
    }


    //英雄
    private class Hero extends PiliRole {
        ImageView imageView;
        int idx;
        boolean hit;
        int type = 0;
        int[] imgList ;

        private Hero() {
        }

        Hero(ImageView imageView) {
            this.imageView = imageView;
        }
    }


    //執行魔王動作
    private class RunBoss implements Runnable {
        ImageView imageView;
        int idx;
        int x;
        boolean hit;
        Boss boss;

        RunBoss(ImageView imageView, int x, Boss boss) {
            this.imageView = imageView;
            this.x = x;
            this.boss = boss;
        }

        @Override
        public void run() {
            draw();
        }

        private void draw() {
//            if(!play) {
//                return;
//            }
            int currentType = boss.type;
            int newType;

            if(hit) {

//                if(currentType==0){
//                    imageView.setImageResource(R.drawable.mole4);
//                }else if(currentType==1){
//                    imageView.setImageResource(R.drawable.smole4);
//                }

                hit = false;
                idx = 0;



//                newType = g.type;

//                if(newType==0){
//                    imageView.setOnTouchListener(new GopherOnTouchListener(this));//設定單次點擊
//                }else if(newType==1){
//                    imageView.setOnTouchListener(new GopherOnDoubleTouchListener(this));//設定雙擊
//                }

//                handler.postDelayed(this, 1000);
            } else {
                idx = idx % this.boss.imgList.length;

                imageView.setImageResource(this.boss.imgList[idx]);
//                int n = (int)(Math.random() * 1000) % 3 + 1;
                int n = 5;
//                if(idx==6){
//                    this.g = changeGopherSprite(imageView);
//                    newType = g.type;
//
//                    if(newType==0){
//                        imageView.setOnTouchListener(new GopherOnTouchListener(this));//設定單次點擊
//                    }else if(newType==1){
//                        imageView.setOnTouchListener(new GopherOnDoubleTouchListener(this));//設定雙擊
//                    }
//                }

                handler.postDelayed(this, (n*100));
                idx = ++idx % this.boss.imgList.length;
            }
        }
    }




    @Override
    protected void onPause() {
        super.onPause();

        if (handler != null) {
            handler.removeCallbacks(rb);
        }
    }


    @Override
    protected void onDestroy() {

        if (handler != null) {
            handler.removeCallbacks(rb);
        }
        super.onDestroy();
    }


}
