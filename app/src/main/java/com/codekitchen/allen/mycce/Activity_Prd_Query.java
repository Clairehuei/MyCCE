package com.codekitchen.allen.mycce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Prd_Query extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_query);

        ButterKnife.inject(this);
    }


    @OnClick(R.id.queryButton01)
    void confirmClick(){
        Intent intent = new Intent(this, Activity_Prd_Query_Result.class);
        startActivity(intent);
    }

}
