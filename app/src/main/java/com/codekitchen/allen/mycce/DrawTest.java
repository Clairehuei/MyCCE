package com.codekitchen.allen.mycce;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by 6193 on 2015/10/6.
 */
public class DrawTest extends SurfaceView implements Runnable ,SurfaceHolder.Callback {

    Bitmap bp;
    int x=50,y=100;  //貼圖在螢幕上的 x,y 座標
    private Thread gameViewThread;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean runFlag = false;
    public static int screen_width, screen_height;
    protected Resources resources;
    private Canvas canvas;
    public String opt = "asc";
    private boolean tLeft = false;
    private boolean tRight = false;
    private boolean tUp = false;
    private boolean tDown = false;

    public void settLeft(boolean tLeft) {
        this.tLeft = tLeft;
    }

    public void settRight(boolean tRight) {
        Log.e("settRight","ppppppppppppppppppppppppp");
        this.tRight = tRight;
    }

    public void settUp(boolean tUp) {
        this.tUp = tUp;
    }

    public void settDown(boolean tDown) {
        this.tDown = tDown;
    }

    // DrawTest 建構子 (自行新增時可使用此建構子)
    public DrawTest(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    // DrawTest 建構子 for layout(.xml) 建構使用 ; layout使用<com.codekitchen.allen.mycce.DrawTest> tag時 會自動呼叫此建構子
    public DrawTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init(){
        //呼叫getHolder()方法來取得 SurfaceHolder,並指給 surfaceHolder
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        resources = getResources();

        paint = new Paint();
        paint.setAntiAlias(true);

        bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire);
        setKeepScreenOn(true);
    }


    public void draw(Canvas canvas, Paint paint){

        if(canvas!=null){
            Log.e("daw.canvas","canvas != null");
            Log.e("daw.canvas(tLeft)",tLeft+"");
            Log.e("daw.canvas(tRight)",tRight+"");

            //清除上次繪製的殘留影像
            canvas.drawColor(Color.BLACK);

            if(tLeft){
                x = x-10;
            }else if(tRight){
                x = x+10;
            }else if(tUp){
                y = y-10;
            }else if(tDown){
                y = y+10;
            }

            tLeft = false;
            tRight = false;
            tUp = false;
            tDown = false;


            //繪製主圖
            canvas.drawBitmap(bp, x, y, null);


//            //自動來回
//            if(x<=50){
//                opt = "asc";
//                x = x+50;
//            }else if(x>350){
//                opt = "desc";
//                x = x-50;
//            }else if(x>50 && x<=350){
//                if(opt.equals("asc")){
//                    x = x+50;
//                }else{
//                    x = x-50;
//                }
//            }

        }else{
            Log.e("daw.canvas","canvas is null");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        screen_width = getWidth();
        screen_height = getHeight();
        runFlag = true;
        gameViewThread = new Thread(this);
        gameViewThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        runFlag = false;

        try {
            gameViewThread.join();
        } catch (InterruptedException e) {
            Log.e("surfaceDestroyed", e+"");
        }
    }


    @Override
    public void run() {
        Log.e("runFlag(1)",runFlag+"");
        while(runFlag){
            try{
                Log.e("while","----------------------------------------------------");
                //1. 鎖住畫布
                canvas = surfaceHolder.lockCanvas();

                //2. 開始繪圖
                draw(canvas, paint);

                Thread.sleep(100);
            }catch (Exception e) {
                Log.e("Error", "刷新屏幕出错！"+e);
            }finally{
                if(canvas!=null){
                    Log.e("finally","canvas!=null");
                    //3. 解鎖並po出畫布
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }else{
                    Log.e("finally","canvas is null");
                }
            }
        }
        Log.e("runFlag(2)",runFlag+"");
    }


}
