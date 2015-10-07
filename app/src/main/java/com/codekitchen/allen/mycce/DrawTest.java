package com.codekitchen.allen.mycce;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

/**
 * Created by 6193 on 2015/10/6.
 */
public class DrawTest extends SurfaceView implements Runnable ,SurfaceHolder.Callback {

    Bitmap bp;
    int x=240,y=500;  //貼圖在螢幕上的 x,y 座標
    private Thread gameViewThread;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean runFlag = false;
    public static int screen_width, screen_height;
    protected Resources resources;
    private Canvas canvas;
    public String opt = "asc";
    public String bossOpt = "asc";
    private boolean tLeft = false;
    private boolean tRight = false;
    private boolean tUp = false;
    private boolean tDown = false;

    private int heroSpeed = 10;
    private int bossSpeed = 17;

    //是否碰撞
    boolean isCollision = false;
    //定义两个矩形的宽高坐标
    private int x1 = 10,y1 = 110,w1 = 40,h1 = 40;
    private int x2 = 300,y2 = 50,w2 = 40,h2 = 40;//目標

    public int bMapNumber = 0;
    public boolean isMoveing = false;

    //英雄血條
    Bitmap heroBold;

    //技能1
    Bitmap skill1;
    int xsk1 = 0, ysk1 = 0;//技能的飛行座標

    //發動技能時的本體位置
    int hxsk1 = 0, hysk1 = 0;

    //打王分數
    int iscore = 0;

    //被打分數
    int jscore = 100;

    //分數畫筆
    private Paint scorePaint;


    //魔王
    Bitmap boss;
    int bossX=219, bossY=10, bossW, bossH;  //初始座標

    //魔王技能1
    Bitmap bossSkill1;
    int bossXsk1 = 0, bossYsk1 = 0;//技能的飛行座標

    //發動技能時的魔王位置
    int bxsk1 = 0, bysk1 = 0;


    //英雄是否發動技能1
    private boolean useSkill1 = false;

    //英雄技能1是否進行中
    private boolean isSkill1Running = false;


    //魔王是否發動技能1
    private boolean bossUseSkill1 = false;

    //魔王技能1是否進行中
    private boolean isBossSkill1Running = false;


    //方向鍵設置
    Bitmap bmcircle;
    Bitmap bncircle;
    int mcircleX=10, mcircleY=750;
    int ncircleX=80, ncircleY=820;
    int mainX = 130, mainY = 870;//操控中心座標


    public void setUseSkill1(boolean useSkill1) {
        this.useSkill1 = useSkill1;
    }

    public void setIsSkill1Running(boolean isSkill1Running) {
        this.isSkill1Running = isSkill1Running;
    }

    public void settLeft(boolean tLeft) {
        this.tLeft = tLeft;
    }

    public void settRight(boolean tRight) {
        this.tRight = tRight;
    }

    public void settUp(boolean tUp) {
        this.tUp = tUp;
    }

    public void settDown(boolean tDown) {
        this.tDown = tDown;
    }

    public void setIsMoveing(boolean isMoveing) {
        this.isMoveing = isMoveing;
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

        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(40);

        bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire);
        heroBold = BitmapFactory.decodeResource(getResources(), R.drawable.hero_blod);
        skill1 = BitmapFactory.decodeResource(getResources(), R.drawable.skill1);
        bossSkill1 = BitmapFactory.decodeResource(getResources(), R.drawable.boss_skill1);
        boss = BitmapFactory.decodeResource(getResources(), R.drawable.boss);
        bossW = boss.getWidth();
        bossH = boss.getHeight();
        bossUseSkill1 = true;

        bmcircle = BitmapFactory.decodeResource(getResources(), R.drawable.mcircle);
        bncircle = BitmapFactory.decodeResource(getResources(), R.drawable.ncircle);

        Log.e("ball_width",bp.getWidth()+"");
        Log.e("ball_height",bp.getHeight()+"");

        setKeepScreenOn(true);

        //设置画笔颜色为白色
        paint.setColor(Color.WHITE);
        //设置焦点
        setFocusable(true);
    }


    public void draw(Canvas canvas, Paint paint){

        if(canvas!=null){

            //清除上次繪製的殘留影像
            canvas.drawColor(Color.BLACK);

            canvas.drawBitmap(bmcircle, mcircleX, mcircleY, null);//方向鍵(外圈)固定
            canvas.drawBitmap(bncircle, ncircleX, ncircleY, null);//方向鍵(內圈)可控制

            if(tLeft){
                x = x-heroSpeed;
            }else if(tRight){
                x = x+heroSpeed;
            }else if(tUp){
                y = y-heroSpeed;
            }else if(tDown){
                y = y+heroSpeed;
            }

//            if(isCollsionWithRect2(x2, y2, w2, h2)){
//                isCollision = true;//已碰撞
//            }else{
//                isCollision = false; //無碰撞
//            }
//
//            //判断是否發生碰撞
//            if(isCollision){//已碰撞
//                paint.setColor(Color.RED);
//                paint.setTextSize(20);
//                canvas.drawText("Collision!", 0, 30, paint);
//            } else{//無碰撞
//                paint.setColor(Color.WHITE);
//            }
//
//            //繪製方塊
//            canvas.drawRect(x2, y2, x2 + w2, y2 + h2, paint);

            tLeft = false;
            tRight = false;
            tUp = false;
            tDown = false;



            //判斷當前是否移動中
            if(isMoveing){//移動中
                //動態選圖(旋轉)
                if(bMapNumber==0){
                    opt = "asc";
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire);
                    bMapNumber = bMapNumber+1;
                }else if(bMapNumber==1){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_right);
                    if(opt.equals("asc")){
                        bMapNumber = bMapNumber+1;
                    }else{
                        bMapNumber = bMapNumber-1;
                    }
                }else if(bMapNumber==2){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_down);
                    if(opt.equals("asc")){
                        bMapNumber = bMapNumber+1;
                    }else{
                        bMapNumber = bMapNumber-1;
                    }
                }else if(bMapNumber==3){
                    opt = "desc";
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_left);
                    bMapNumber = bMapNumber-1;
                }
            }else{//靜止
                if(bMapNumber==0){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire);
                }else if(bMapNumber==1){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_right);
                }else if(bMapNumber==2){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_down);
                }else if(bMapNumber==3){
                    bp = BitmapFactory.decodeResource(getResources(), R.drawable.btn_ball_fire_left);
                }
            }


            //設置魔王位置
            moveBoss();

            //繪製主圖
            canvas.drawBitmap(boss, bossX, bossY, null);//魔王
            canvas.drawBitmap(bp, x, y, null);//英雄
            canvas.drawBitmap(heroBold, 5, 700, null);//英雄血條

            //判斷是否使用技能1
            if(useSkill1 || isSkill1Running){

                if(useSkill1){
                    Log.e("skill_width",skill1.getWidth()+"");
                    Log.e("skill_height",skill1.getHeight()+"");
                    Log.e("ball_width",bp.getWidth()+"");
                    Log.e("ball_height",bp.getHeight()+"");
                    isSkill1Running  = true;
                    xsk1 = x+56;
                    ysk1 = y-56;
                    hxsk1 = xsk1;
                    hysk1 = ysk1;
                }

                if(isSkill1Running){
                    ysk1 = ysk1-56;
                    if(ysk1+50<0){
                        isSkill1Running = false;
                    }
                }


                canvas.drawBitmap(skill1, xsk1, ysk1, null);

                //判斷是否擊中魔王
                if(isCollsionWithRect3(xsk1, ysk1, skill1, bossX, bossY, bossW, bossH)){//命中目標
                    isSkill1Running = false;
                    iscore = iscore+1;
                }else{//未命中

                }


                useSkill1 = false;
            }

            //判斷是否被魔王擊中
            //TODO...
            //判斷是否使用技能1
            //判斷是否使用技能1
            if(bossUseSkill1 || isBossSkill1Running){

                if(bossUseSkill1){
//                    Log.e("skill_width",skill1.getWidth()+"");
//                    Log.e("skill_height",skill1.getHeight()+"");
//                    Log.e("ball_width",bp.getWidth()+"");
//                    Log.e("ball_height",bp.getHeight()+"");

                    isBossSkill1Running  = true;
                    bossXsk1 = bossX+56;
                    bossYsk1 = bossY+56;
                    bxsk1 = bossXsk1;
                    bysk1 = bossYsk1;
                }

                if(isBossSkill1Running){
                    bossYsk1 = bossYsk1+56;
                    if(bossYsk1+50>700){
                        isBossSkill1Running = false;
                        bossUseSkill1 = true;
                    }
                }


                canvas.drawBitmap(bossSkill1, bossXsk1, bossYsk1, null);

                //判斷是否擊中英雄
                if(isCollsionWithRect4(bossXsk1, bossYsk1, bossSkill1, x, y, bp.getWidth(), bp.getHeight())){//命中目標
                    isBossSkill1Running = false;
                    bossUseSkill1 = true;
                    jscore = jscore - 1;
                }else{//未命中

                }


                if(isBossSkill1Running){
                    bossUseSkill1 = false;
                }

            }

            //繪製分數
            canvas.drawText(String.valueOf(iscore), 580, 60, scorePaint);
            canvas.drawText(String.valueOf(jscore), 580, 120, scorePaint);

        }else{
            Log.e("daw.canvas","canvas is null");
        }
    }


    /**
     * 設定魔王座標
     */
    public void moveBoss(){
            //自動來回
            if(bossX<=10){
                bossOpt = "asc";
                bossX = bossX + bossSpeed;
            }else if(bossX>428){
                bossOpt = "desc";
                bossX = bossX-bossSpeed;
            }else if(bossX>10 && bossX<=428){
                if(bossOpt.equals("asc")){
                    bossX = bossX + bossSpeed;
                }else{
                    bossX = bossX-bossSpeed;
                }
            }
    }


    public void draw2(Canvas canvas, Paint paint){
        if(canvas!=null){
            //清除上次繪製的殘留影像
            canvas.drawColor(Color.BLACK);

            //判断是否发生了碰撞
            if(isCollision){//发生碰撞
                paint.setColor(Color.RED);
                paint.setTextSize(20);
                canvas.drawText("Collision!", 0, 30, paint);
            } else{//没发生碰撞
                paint.setColor(Color.WHITE);
            }
            //绘制两个矩形
            canvas.drawRect(x1, y1, x1 + w1, y1 + h1, paint);
            canvas.drawRect(x2, y2, x2 + w2, y2 + h2, paint);


            //繪製主圖
//            canvas.drawBitmap(bp, x, y, null);


        }else{
            Log.e("daw.canvas","canvas is null");
        }
    }

    /**
     * 触屏事件监听
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        // TODO Auto-generated method stub
//        //让矩形1随着触屏位置移动（触屏点设为此矩形的中心点）
//        x1 = (int) event.getX() - w1/2;
//        y1 = (int) event.getY() - h1/2;
//        //当矩形之间发生碰撞
//        if(isCollsionWithRect(x1,y1,w1,h1,x2,y2,w2,h2)){
//            isCollision = true;//设置标志位为真
//            //当矩形之间没有发生碰撞
//        }else{
//            isCollision = false; //设置标志位为假
//        }
//        return true;


        Log.e("onTouchEvent","onTouchEvent");

        //以下計算方向鍵
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.e("onTouchEvent","ACTION_DOWN");
            calMoveCoordinate(event.getX(), event.getY());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.e("onTouchEvent","ACTION_MOVE");
            calMoveCoordinate(event.getX(), event.getY());
            return true;
        }

        return false;
    }


    public void calMoveCoordinate(float x, float y){

        int p = (int)x - mainX, q = (int)y - mainY;
        int moveX, moveY;


        //垂直上下移動
        if(p==0){
            if(q<0){
                moveY = -heroSpeed;
            }else {
                moveY = heroSpeed;
            }
            this.y = this.y + moveY;
            return;
        }

        //水平左右移動
        if(q==0){
            if(p<0){
                moveX = -heroSpeed;
            }else {
                moveX = heroSpeed;
            }
            this.x = this.x + moveX;
            return;
        }



        double z = Math.sqrt((p*p)+(q*q));

        moveX = (int)(p*heroSpeed/z);
        moveY = (int)Math.sqrt((heroSpeed*heroSpeed)-(moveX*moveX));

        if(q<0){
            moveY = -moveY;
        }



        this.x = this.x + moveX;
        this.y = this.y + moveY;
    }


    /**
     * 矩形碰撞的函数
     * @param x1 第一个矩形的X坐标
     * @param y1 第一个矩形的Y坐标
     * @param w1 第一个矩形的宽
     * @param h1 第一个矩形的高
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     */
    public boolean isCollsionWithRect(int x1,int y1,int w1,int h1,int x2,int y2,int w2,int h2){
        //当矩形1位于矩形2的左侧
        if (x1 >= x2 && x1>= x2 + w2){
            return false;
            //当矩形1位于矩形2的右侧
        } else if (x1<= x2 && x1 + w1 <= x2){
            return false;
            //当矩形1位于矩形2的上方
        } else if (y1 >= y2 && y1>= y2 + h2){
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2){
            return false;
        }

        //所有不会发生碰撞都不满足时，肯定就是碰撞了
        return true;
    }


    /**
     * 矩形碰撞的函数
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     */
    public boolean isCollsionWithRect2(int x2,int y2,int w2,int h2){

        if(x>= x2+w2){
            return false;
        }else if(x+bp.getWidth()<=x2){
            return false;
        }else if(y+bp.getHeight()<=y2){
            return false;
        }else if(y>=y2+h2){
            return false;
        }

        //所有不會發生碰撞都不滿足時，就是碰撞
        return true;
    }



    /**
     * 判斷 Skill 1 是否有擊中魔王
     * 矩形碰撞的函数
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     */
    public boolean isCollsionWithRect3(int sx, int sy, Bitmap tbp, int x2, int y2,int w2,int h2){

        if(sx>= x2+w2){
            return false;
        }else if(sx+tbp.getWidth()<=x2){
            return false;
        }else if(sy+tbp.getHeight()<=y2){
            return false;
        }else if(sy>=y2+h2){
            return false;
        }

        //所有不會發生碰撞都不滿足時，就是碰撞
        return true;
    }


    /**
     * 判斷 Skill 1 是否有擊中英雄
     * 矩形碰撞的函数
     * @param x2 第二个矩形的X坐标
     * @param y2 第二个矩形的Y坐标
     * @param w2 第二个矩形的宽
     * @param h2 第二个矩形的高
     */
    public boolean isCollsionWithRect4(int sx, int sy, Bitmap tbp, int x2, int y2,int w2,int h2){

        if(sx>= x2+w2){
            return false;
        }else if(sx+tbp.getWidth()<=x2){
            return false;
        }else if(sy+tbp.getHeight()<=y2){
            return false;
        }else if(sy>=y2+h2){
            return false;
        }

        //所有不會發生碰撞都不滿足時，就是碰撞
        return true;
    }


    /**
     * 按键事件监听
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        screen_width = getWidth();
        screen_height = getHeight();
        Log.e("screen_width",screen_width+"");
        Log.e("screen_height",screen_height+"");
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

        while(runFlag){
            try{
                //1. 鎖住畫布
                canvas = surfaceHolder.lockCanvas();

                //2. 開始繪圖
                draw(canvas, paint);
//                draw2(canvas, paint);

                Thread.sleep(30);
            }catch (Exception e) {
                Log.e("Error", "刷新屏幕出错！"+e);
            }finally{
                if(canvas!=null){
                    //3. 解鎖並po出畫布
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }else{
                    Log.e("finally","canvas is null");
                }
            }
        }
    }


}
