package com.codekitchen.allen.mycce;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.codekitchen.allen.mycce.sqlite.db.DBHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        context = this;

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d("=onCreateOptionsMenu=", "xxxxx");
        getMenuInflater().inflate(R.menu.main_bar, menu);
        return true;
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


    @OnClick(R.id.wayLiTry)
    void wayLiTryClick(){
        Intent intent = new Intent(this, Activity_WayLiTry.class);
        startActivity(intent);
    }


}
