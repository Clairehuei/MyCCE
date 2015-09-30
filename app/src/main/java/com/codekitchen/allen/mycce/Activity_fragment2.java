package com.codekitchen.allen.mycce;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Activity_fragment2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment2);
    }

    public void onClick(View view) {
        // 建立 Fragment 交易服務機制
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch(view.getId()) {
            case R.id.buttonA:
                // 置換 fragment
                ft.replace(R.id.fragment_addin_linearlayout, new MyFragmentA(), "f_a");
                break;
            case R.id.buttonB:
                // 置換 fragment
                ft.replace(R.id.fragment_addin_linearlayout, new MyFragmentB(), "f_b");
                break;
        }
        // 提交,由主執行續main thread執行
        ft.commit();
    }

}
