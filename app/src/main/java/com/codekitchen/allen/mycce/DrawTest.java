package com.codekitchen.allen.mycce;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 6193 on 2015/10/6.
 */
public class DrawTest extends SurfaceView implements SurfaceHolder.Callback {

    //呼叫getHolder()方法來取得 SurfaceHolder,並指給 holder
    SurfaceHolder holder = getHolder();
    Bitmap bp;
    Canvas canvas;
    int x=50,y=100;  //貼圖在螢幕上的 x,y 座標
    Thread t1;

    // DrawTest 建構子
    public DrawTest(Context context) {
        super(context);
        // TODO Auto-generated constructor stub


    }

    // DrawTest 建構子
    public DrawTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        //把這個 class 本身(extends SurfaceView)
        //透過 holder 的 Callback()方法連結起來
        //下面這行也可寫成 getHolder().addCallback(this);
        holder.addCallback(this);

        //設定圖片來源
        bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire);
    }

    private void draw() {
        //1. 鎖住畫布
        canvas = holder.lockCanvas();

        //2. 在畫布上貼圖
        canvas.drawBitmap(bp, x, y, null);

        //3. 解鎖並po出畫布
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        //在 canvas 畫布上貼圖的三個步驟
        t1 = new Thread() {
            public void run() {

                while (true) {

//                    draw();

                    canvas = getHolder().lockCanvas();

                    if(canvas != null){
                        synchronized (getHolder()) {
                            canvas.drawBitmap(bp, x, y, null);
                        }
                        getHolder().unlockCanvasAndPost(canvas);
                    }

                    if(x<=50){
                        x = x+50;
                    }else if(x>350){
                        x = x-50;
                    }

                    try {
//                        Thread.currentThread().sleep(500);
                        sleep(200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        t1.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
    }
}
