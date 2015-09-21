package com.codekitchen.allen.mycce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.os.Handler;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;

public class Activity_BigFuTry extends AppCompatActivity {


    private SwipeRefreshLayout laySwipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__big_fu_try);

        initView();
    }


    private void initView() {
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(onSwipeToRefresh);

        //更新時顯示的動態顏色
        laySwipe.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        ListView lstData = (ListView) findViewById(R.id.lstData);
        lstData.setAdapter(getAdapter());
        lstData.setOnScrollListener(onListScroll);
    }


    //實作 OnRefreshListener 接收更新通知
    private OnRefreshListener onSwipeToRefresh = new OnRefreshListener() {

        //當在螢幕上按住往下滑動，到達一定的距離，就會完成下拉手勢，此時就會觸發 OnRefreshListener 偵聽器的 onRefresh() 方法。
        @Override
        public void onRefresh() {
            laySwipe.setRefreshing(true);//讓使用者看到現在要開始更新的動作


            //使用 Handler() 來模擬更新，時間為 3 秒
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    laySwipe.setRefreshing(false);//把動畫結束掉
                    Toast.makeText(getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
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


}
