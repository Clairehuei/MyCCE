package com.codekitchen.allen.mycce;

import android.content.Intent;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_BigFuTry extends AppCompatActivity {


    @InjectView(R.id.laySwipe)
    SwipeRefreshLayout laySwipe;

    @InjectView(R.id.lstData)
    ListView lstData;

    @InjectView(R.id.txtTitle)
    TextView txtTitle;

    @InjectView(R.id.buttonxxxx)
    Button buttonxxxx;

    @InjectView(R.id.buttonyyyy)
    Button buttonyyyy;

    @InjectView(R.id.buttonzzzz)
    Button buttonzzzz;

    @InjectView(R.id.buttonwwww)
    Button buttonwwww;

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__big_fu_try);

        ButterKnife.inject(this);

        Log.d("RunnableThreadId(1)", Long.toString(Thread.currentThread().getId()));
        Log.d("RunnableThreadName(1)", Thread.currentThread().getName());

//        new Thread(new Runnable() {
//            public void run() {
//                Log.d("RunnableThreadId(2a)", Long.toString(Thread.currentThread().getId()));
//                Log.d("RunnableThreadName(2a)", Thread.currentThread().getName());
//                try {
//                    Log.d("RunnableThreadName(2a)", "start sleep");
//                    Thread.sleep(1000);// 在onCreate中短時間內還可以從其他thread中更新UI,一旦加入延遲(sleep)經過某個狀態,就無法更新UI了
//                    Log.d("RunnableThreadName(2a)", "wake up");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.d("RunnableThreadName(2a)", "111111111111");
//                txtTitle.setText("123");
//                buttonxxxx.setText("123");
//                Log.d("RunnableThreadName(2a)", "2222222222222");
//            }
//        }).start();

//        new Thread(){
//            @Override
//            public void run() {
//                txtTitle.setText("456");
//            }
//        }.start();



        initView();
    }


    private void initView() {
        laySwipe.setOnRefreshListener(onSwipeToRefresh);

        //更新時顯示的動態顏色(如果沒有指定顏色，預色會是以黑色顯示。可以同時指定 4 種顏色，更新橫條會自動以動畫顯示)
        laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,//預設第一個是隨使用者手勢載入的顏色進度條
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        adapter = getAdapter();
        lstData.setAdapter(adapter);
        lstData.setOnScrollListener(onListScroll);
    }


    //實作 OnRefreshListener 接收更新通知
    private OnRefreshListener onSwipeToRefresh = new OnRefreshListener() {

        //當在螢幕上按住往下滑動，到達一定的距離，就會完成下拉手勢，此時就會觸發 OnRefreshListener 偵聽器的 onRefresh() 方法。
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);//讓使用者看到現在要開始更新的動作

            Log.d("RunnableThreadId(2c1)", Long.toString(Thread.currentThread().getId()));
            Log.d("RunnableThreadName(2c1)", Thread.currentThread().getName());

            //錯誤範例 : 另開執行續測試
//            new Thread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Log.d("RunnableThreadId(2c2)", Long.toString(Thread.currentThread().getId()));
//                            Log.d("RunnableThreadName(2c2)", Thread.currentThread().getName());
//
//                            try {
//                                Thread.sleep(1000);// 在短時間內還可以從其他thread中更新UI,一旦加入延遲(sleep)經過某個狀態,就無法更新UI了
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//
//                            buttonxxxx.setText("x");
//
//                            adapter = getAdapter();
//                            lstData.setAdapter(adapter);
//                            laySwipe.setRefreshing(false);//把動畫結束掉
//                            txtTitle.setText("test");
//                            txtTitle.setBackgroundColor(Color.BLUE);
//                        }
//                    }
//            ).start();




            //for demo : 使用 Handler() 來模擬更新，時間為 3 秒
            //官網API說明 : Default constructor associates this handler with the Looper for the current thread
            //當new 一個Handler時,若建構子為空(預設),則此handler為關聯當前thread(若無另開thread,則當前thread即為main thread)
            //故在此範例中,下方之 new Handler 為 關聯 main thread
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Log.d("RunnableThreadId(2b)", Long.toString(Thread.currentThread().getId()));
                    try {
                                Thread.sleep(1000);
                    } catch (InterruptedException e) {
                                e.printStackTrace();
                    }
                    Log.d("RunnableThreadName(2b)", Thread.currentThread().getName());

                    //更新資料
                    adapter = getAdapter();
                    lstData.setAdapter(adapter);
                    //runOnUiThread(adapter::notifyDataSetChanged);//此範例已在main thread中,故無需此行

                    laySwipe.setRefreshing(false);//把動畫結束掉
                    Toast.makeText(getApplicationContext(), "更新完畢!", Toast.LENGTH_SHORT).show();
                }
            }, 3000);



        }
    };


    //取得ListView所需顯示的資料
    private ArrayAdapter<String> getAdapter(){
        //fake data
        String[] data = new String[20];
        int len = data.length;
        for (int i = 0; i < len; i++) {
            data[i] = Double.toString(Math.random() * 1000);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , data);

        return adapter;
    }



    private OnScrollListener onListScroll = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }


        //偵聽 ListView 的捲動事件，判斷只有當第一個可視項目 (列) 為 0 的時候，表示已經捲到頂了，這時才去偵聽下拉更新手勢。開啟及關閉偵聽下拉手勢的方法
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem == 0) {
                laySwipe.setEnabled(true);
            }else{
                laySwipe.setEnabled(false);
            }
        }
    };



    @OnClick(R.id.buttonxxxx)
     void clickButtonxxxx(){

        //錯誤寫法示範: 點擊按鈕,另開執行緒,且企圖修改主執行緒(UI thread 或叫做 main Thread)UI元件,會立刻導致 app 停止運行
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.d("RunnableThreadId(3x)", Long.toString(Thread.currentThread().getId()));
                Log.d("RunnableThreadName(3x)", Thread.currentThread().getName());
                buttonxxxx.setText("打我啊笨蛋");
            }
        }).start();

    }


    @OnClick(R.id.buttonyyyy)
    void clickButtonyyyy(){

        //無另開執行緒,即默認為主執行緒(UI thread 或叫做 main Thread)
        Log.d("RunnableThreadId(3y)", Long.toString(Thread.currentThread().getId()));
        Log.d("RunnableThreadName(3y)", Thread.currentThread().getName());
        buttonyyyy.setText("Y");

    }


    @OnClick(R.id.buttonzzzz)
    void clickButtonzzzz(){

        //另開執行緒,但使用 runOnUiThread() 方法進行UI更新
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    Log.d("RunnableThreadId(3z)", Long.toString(Thread.currentThread().getId()));
                    Log.d("RunnableThreadName(3z)", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("RunnableThreadId(4)", Long.toString(Thread.currentThread().getId()));
                        Log.d("RunnableThreadName(4)", Thread.currentThread().getName());
                        buttonzzzz.setText("Z");
                    }
                });
            }
        }).start();
    }


    @OnClick(R.id.buttonwwww)
    void clickButtonwwww(){

        //使用 Handler
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                Log.d("RunnableThreadId(w)", Long.toString(Thread.currentThread().getId()));
                Log.d("RunnableThreadName(w)", Thread.currentThread().getName());
                buttonwwww.setText("W");
            }

        });
    }


}
