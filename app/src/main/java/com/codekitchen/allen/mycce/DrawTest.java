package com.codekitchen.allen.mycce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
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

    private Thread gameViewThread;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private boolean runFlag = false;
    public static int screen_width, screen_height;
    protected Resources resources;
    private Canvas canvas;

    //****************************英雄屬性************************************
    Bitmap bp;
    int x=240,y=500;//初始座標
    int heroWidth, heroHeight;//人物 寬/高
    private int heroSpeed = 8;//英雄移動速度
    int jscore = 100;//英雄的血量
    Bitmap heroBold;//英雄血條

    //英雄技能1
    Bitmap skill1;//英雄技能1
    private boolean useSkill1 = false;//英雄是否發動技能1
    int xsk1 = 0, ysk1 = 0;//技能的飛行座標
    int hxsk1 = 0, hysk1 = 0;//發動技能1時的英雄位置
    private boolean isSkill1Running = false;//英雄技能1是否進行中
    //************************************************************************


    //+++++++++++++++++++++++++++++魔王屬性++++++++++++++++++++++++++++++++++++
    Bitmap boss;
    int bossX=219, bossY=10, bossW, bossH;//初始座標 &  寬/高
    private int bossSpeed = 10;//魔王移動速度
    public String bossOpt = "asc";//魔王移動路徑(來回)
    int iscore = 10;//魔王的血量
    Bitmap boosBold;//魔王血條

    //魔王技能1
    Bitmap bossSkill1;//魔王技能1
    private boolean bossUseSkill1 = false;//魔王是否發動技能1
    int bossXsk1 = 0, bossYsk1 = 0;//技能的飛行座標
    int bxsk1 = 0, bysk1 = 0;//發動技能1時的魔王位置
    private boolean isBossSkill1Running = false;//魔王技能1是否進行中
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


    //#############################介面屬性####################################


    private Paint scorePaint;//分數畫筆

    //玩家介面方向鍵設置
    Bitmap bmcircle;
    Bitmap bncircle;
    int mcircleX=10, mcircleY=750;//外圈(固定)
    int ncircleX=80, ncircleY=820;//內圈(玩家操控)
    int mainX = 130, mainY = 870;//操控中心座標
    //#########################################################################


    Bitmap background;//背景圖
    Matrix matrix = new Matrix();
    float fWidth, fHeight;
    boolean settingBackground = true; //是否已設置背景(僅執行一次)

    //是否碰撞
    boolean isCollision = false;

    Handler handler;

    public void initHandler()
    {
        handler = new Handler();
    }

    protected IOnEndOfGameInterface mOnEndOfGame ;//callback interface

    public void setIOnEndOfGame(IOnEndOfGameInterface xOnEndOfGame){
        mOnEndOfGame = xOnEndOfGame;
    }

    //***************setter******************
    public void setUseSkill1(boolean useSkill1) {
        this.useSkill1 = useSkill1;
    }


    // DrawTest 建構子 (自行新增時可使用此建構子)
    public DrawTest(Context context) {
        super(context);
        init();
    }

    // DrawTest 建構子 for layout(.xml) 建構使用 ; layout使用<com.codekitchen.allen.mycce.DrawTest> tag時 會自動呼叫此建構子
    public DrawTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        //呼叫getHolder()方法來取得 SurfaceHolder,並指給 surfaceHolder
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        resources = getResources();

        //設置畫筆屬性
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);//設置畫筆顏色為白色

        //設置分數畫筆屬性
        scorePaint = new Paint();
        scorePaint.setAntiAlias(true);
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(40);

        //取得英雄相關圖檔
        bp = BitmapFactory.decodeResource(resources, R.drawable.hero);
        heroWidth = bp.getWidth();
        heroHeight = bp.getHeight();
        skill1 = BitmapFactory.decodeResource(resources, R.drawable.skill1);
        heroBold = BitmapFactory.decodeResource(resources, R.drawable.hero_blod);

        //取得魔王相關圖檔
        boss = BitmapFactory.decodeResource(resources, R.drawable.boss_iron);
        bossW = boss.getWidth();
        bossH = boss.getHeight();
        bossSkill1 = BitmapFactory.decodeResource(resources, R.drawable.boss_skill1);
        bossUseSkill1 = true;

        //取得玩家介面方向控制圖檔
        bmcircle = BitmapFactory.decodeResource(resources, R.drawable.mcircle);
        bncircle = BitmapFactory.decodeResource(resources, R.drawable.ncircle);

        //取得背景圖檔
        background = BitmapFactory.decodeResource(resources, R.drawable.bg);

        setKeepScreenOn(true);
        setFocusable(true);//設置畫面焦點
    }


    public void draw(Canvas canvas, Paint paint){

        if(canvas!=null){

            //清除上次繪製的殘留影像
//            canvas.drawColor(Color.BLACK);
            if(settingBackground){
                fWidth = (float)screen_width/background.getWidth();
                fHeight = (float)screen_height/background.getHeight();
                Log.e("fWidth",fWidth+"");
                Log.e("fHeight",fHeight+"");
                matrix.postScale(fWidth, fHeight);

                settingBackground = false;
            }

            canvas.drawBitmap(background, matrix, null);//繪製背景

            canvas.drawBitmap(bmcircle, mcircleX, mcircleY, null);//方向鍵(外圈)固定
            canvas.drawBitmap(bncircle, ncircleX, ncircleY, null);//方向鍵(內圈)可控制

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


            moveBoss();//設置魔王位置

            canvas.drawBitmap(boss, bossX, bossY, null);//繪製魔王

            updateHeroCoordinate();//調整英雄座標(避免出畫面)
            canvas.drawBitmap(bp, x, y, null);//繪製英雄
            canvas.drawBitmap(heroBold, 5, 700, null);//英雄血條

            //判斷英雄是否使用技能1
            if(useSkill1 || isSkill1Running){
                if(useSkill1){
                    isSkill1Running  = true;
                    xsk1 = x+56;
                    ysk1 = y-56;
                    hxsk1 = xsk1;
                    hysk1 = ysk1;
                }

                if(isSkill1Running){
                    ysk1 = ysk1-56;
                    if(ysk1+50<0){//判斷是否即將射出畫面邊緣限制
                        isSkill1Running = false;
                    }
                }

                canvas.drawBitmap(skill1, xsk1, ysk1, null);//繪製英雄技能1

                //判斷是否擊中魔王
                if(isCollsionWithRect3(xsk1, ysk1, skill1, bossX, bossY, bossW, bossH)){//命中目標
                    isSkill1Running = false;
                    iscore = iscore-1;
                }

                useSkill1 = false;
            }

            //判斷魔王是否使用技能1
            if(bossUseSkill1 || isBossSkill1Running){
                if(bossUseSkill1){
                    isBossSkill1Running  = true;
                    bossXsk1 = bossX+56;
                    bossYsk1 = bossY+56;
                    bxsk1 = bossXsk1;
                    bysk1 = bossYsk1;
                }

                if(isBossSkill1Running){
                    bossYsk1 = bossYsk1+56;
                    if(bossYsk1+50>700){//判斷是否即將射出畫面邊緣限制
                        isBossSkill1Running = false;
                        bossUseSkill1 = true;
                    }
                }

                canvas.drawBitmap(bossSkill1, bossXsk1, bossYsk1, null);//繪製魔王技能1

                //判斷是否擊中英雄
                if(isCollsionWithRect4(bossXsk1, bossYsk1, bossSkill1, x, y, bp.getWidth(), bp.getHeight())){//命中目標
                    isBossSkill1Running = false;
                    bossUseSkill1 = true;
                    jscore = jscore - 1;
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
     * 設定魔王移動路徑
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


    /**
     * 修正英雄座標(避免出畫面)
     */
    public void updateHeroCoordinate(){
        if(x<0){
            x=0;
        }

        if(x>(screen_width-heroWidth)){
            x = screen_width-heroWidth;
        }

        if(y<0){
            y=0;
        }

        if(y>(700-heroHeight)){
            y = 700-heroHeight;
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


        //以下計算方向鍵
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //让矩形1随着触屏位置移动（触屏点设为此矩形的中心点）
            ncircleX = (int) event.getX() - 50;
            ncircleY = (int) event.getY() - 50;

            if(ncircleX>150){
               ncircleX = 150;
            }

            if(ncircleX<10){
                ncircleX = 10;
            }

            if(ncircleY>890){
                ncircleY = 890;
            }

            if(ncircleY<750){
                ncircleY = 750;
            }

            calMoveCoordinate(event.getX(), event.getY());
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //让矩形1随着触屏位置移动（触屏点设为此矩形的中心点）
            Log.e("event.getX()",(int)event.getX()+"");
            Log.e("event.getY()",(int)event.getY()+"");

            ncircleX = (int) event.getX() - 50;
            ncircleY = (int) event.getY() - 50;

            ncircleX = (int) event.getX() - 50;
            ncircleY = (int) event.getY() - 50;

            if(ncircleX>150){
                ncircleX = 150;
            }

            if(ncircleX<10){
                ncircleX = 10;
            }

            if(ncircleY>890){
                ncircleY = 890;
            }

            if(ncircleY<750){
                ncircleY = 750;
            }

            calMoveCoordinate(event.getX(), event.getY());
            return true;
        } else if(event.getAction() == MotionEvent.ACTION_UP){
            //操控鈕返回中央
            ncircleX=80;
            ncircleY=820;
        }

        return false;
    }


    //計算路徑
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
        Log.e("screen_width(xxx)",screen_width+"");
        Log.e("screen_height(xxx)",screen_height+"");

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

                Thread.sleep(30);//畫面刷新頻率 1000ms = 1秒 , 30ms = 1秒刷新約30次 = 30fps
            }catch (Exception e) {
                Log.e("Error", "刷新屏幕出错！"+e);
            }finally{
                if(canvas!=null){
                    //3. 解鎖並po出畫布
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }else{
                    Log.e("finally","canvas is null");
                }

                if(iscore==0){//玩家勝利
                    runFlag = false;

                    //此處的handler因為是在UI thread建立, 因此可以與UI thread交流
                    handler.post(new Runnable(){
                        public void run(){
                            mOnEndOfGame.showResult("You Win!!");
                        }
                    });
                }

                if(jscore==0){//魔王勝利
                    runFlag = false;

                    //此處的handler因為是在UI thread建立, 因此可以與UI thread交流
                    handler.post(new Runnable(){
                        public void run(){
                            mOnEndOfGame.showResult("You Lose!!");
                        }
                    });
                }
            }
        }
    }


}
