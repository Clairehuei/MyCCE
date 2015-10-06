package com.codekitchen.allen.mycce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Activity_Surface extends AppCompatActivity {

//    @InjectView(R.id.btnLeft)
//    Button btnLeft;
//
//    @InjectView(R.id.btnRight)
//    Button btnRight;


//    @InjectView(R.id.olaview)
//    SurfaceView surfaceView;

    DrawTest vdt;

    Button btnLeft;
    Button btnRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.inject(this);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//隱去標題
////
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_surface);

        vdt = (DrawTest)findViewById(R.id.olaview);
        btnLeft = (Button)findViewById(R.id.btnLeft);
        btnRight = (Button)findViewById(R.id.btnRight);

        btnLeft.setOnClickListener(mOnClickListener1);
        btnRight.setOnClickListener(mOnClickListener2);
    }


    private View.OnClickListener mOnClickListener1 = new View.OnClickListener(){
        public void onClick(View v){
            Log.e("btnLeftClick", "*********************************************************");
            vdt.settLeft(true);
        }
    };

    private View.OnClickListener mOnClickListener2 = new View.OnClickListener(){
        public void onClick(View v){
            Log.e("btnRightClick", "*********************************************************");
            vdt.settRight(true);
        }
    };




//    @OnClick(R.id.btnLeft)
//    void btnLeftClick(){
//        Log.e("btnLeftClick","*********************************************************");
//        vdt.settLeft(true);
//    }
//
//    @OnClick(R.id.btnRight)
//    void btnRightClick(){
//        Log.e("btnLeftClick","<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//        vdt.settRight(true);
//    }


    @Override
    protected void onPause (){
        super.onPause();
    }








}
