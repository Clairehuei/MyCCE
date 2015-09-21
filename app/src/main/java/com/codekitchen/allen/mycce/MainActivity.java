package com.codekitchen.allen.mycce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.classify)
    void confirmClick(){
        Intent intent = new Intent(this, Activity_Prd_Query.class);
        startActivity(intent);
    }

    @OnClick(R.id.bingo)
    void bingoClick(){
        Intent intent = new Intent(this, Activity_Bingo.class);
        startActivity(intent);
    }


    @OnClick(R.id.bigFuTry)
    void bigFuTryClick(){
        Intent intent = new Intent(this, Activity_BigFuTry.class);
        startActivity(intent);
    }



}
