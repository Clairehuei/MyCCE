package com.codekitchen.allen.mycce;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.codekitchen.allen.mycce.api.ApiComponent;
import com.codekitchen.allen.mycce.api.ApplicationModule;
import com.codekitchen.allen.mycce.api.Bingo;
import com.codekitchen.allen.mycce.api.DaggerApiComponent;
import com.codekitchen.allen.mycce.api.model.BingoDetail;
import com.codekitchen.allen.mycce.api.response.ApiModule;
import com.codekitchen.allen.mycce.api.response.BingoListingResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity_Bingo extends AppCompatActivity {

    @Inject
    Bingo bingo;

    @InjectView(R.id.bingoARNO01)
    TextView bingoARNO01;
    @InjectView(R.id.bingoARNO02)
    TextView bingoARNO02;
    @InjectView(R.id.bingoARNO03)
    TextView bingoARNO03;
    @InjectView(R.id.bingoARNO04)
    TextView bingoARNO04;
    @InjectView(R.id.bingoARNO05)
    TextView bingoARNO05;
    @InjectView(R.id.bingoARNO06)
    TextView bingoARNO06;
    @InjectView(R.id.bingoARNO07)
    TextView bingoARNO07;
    @InjectView(R.id.bingoARNO08)
    TextView bingoARNO08;
    @InjectView(R.id.bingoARNO09)
    TextView bingoARNO09;
    @InjectView(R.id.bingoARNO10)
    TextView bingoARNO10;
    @InjectView(R.id.bingoARNO11)
    TextView bingoARNO11;
    @InjectView(R.id.bingoARNO12)
    TextView bingoARNO12;
    @InjectView(R.id.bingoARNO13)
    TextView bingoARNO13;
    @InjectView(R.id.bingoARNO14)
    TextView bingoARNO14;
    @InjectView(R.id.bingoARNO15)
    TextView bingoARNO15;
    @InjectView(R.id.bingoARNO16)
    TextView bingoARNO16;
    @InjectView(R.id.bingoARNO17)
    TextView bingoARNO17;
    @InjectView(R.id.bingoARNO18)
    TextView bingoARNO18;
    @InjectView(R.id.bingoARNO19)
    TextView bingoARNO19;
    @InjectView(R.id.bingoARNO20)
    TextView bingoARNO20;

    @InjectView(R.id.bingoBRNO01)
    TextView bingoBRNO01;
    @InjectView(R.id.bingoBRNO02)
    TextView bingoBRNO02;
    @InjectView(R.id.bingoBRNO03)
    TextView bingoBRNO03;
    @InjectView(R.id.bingoBRNO04)
    TextView bingoBRNO04;
    @InjectView(R.id.bingoBRNO05)
    TextView bingoBRNO05;
    @InjectView(R.id.bingoBRNO06)
    TextView bingoBRNO06;
    @InjectView(R.id.bingoBRNO07)
    TextView bingoBRNO07;
    @InjectView(R.id.bingoBRNO08)
    TextView bingoBRNO08;
    @InjectView(R.id.bingoBRNO09)
    TextView bingoBRNO09;
    @InjectView(R.id.bingoBRNO10)
    TextView bingoBRNO10;

    @InjectView(R.id.bingoCRNO01)
    TextView bingoCRNO01;
    @InjectView(R.id.bingoCRNO02)
    TextView bingoCRNO02;
    @InjectView(R.id.bingoCRNO03)
    TextView bingoCRNO03;
    @InjectView(R.id.bingoCRNO04)
    TextView bingoCRNO04;
    @InjectView(R.id.bingoCRNO05)
    TextView bingoCRNO05;
    @InjectView(R.id.bingoCRNO06)
    TextView bingoCRNO06;
    @InjectView(R.id.bingoCRNO07)
    TextView bingoCRNO07;
    @InjectView(R.id.bingoCRNO08)
    TextView bingoCRNO08;
    @InjectView(R.id.bingoCRNO09)
    TextView bingoCRNO09;
    @InjectView(R.id.bingoCRNO10)
    TextView bingoCRNO10;



    List<BingoDetail> itemsA = new ArrayList<>();
    List<BingoDetail> itemsB = new ArrayList<>();
    List<BingoDetail> itemsC = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("==[Ac_Bingo]==", "===onCreate(start)===");
        setContentView(R.layout.activity_bingo);

        ButterKnife.inject(this);

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);
        Log.d("==[Ac_Bingo]==", "===onCreate(end)===");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("==[Ac_Bingo]==", "===onResume(start)===");
        refreshData();
        Log.d("==[Ac_Bingo]==", "===onResume(end)===");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("==[Ac_Bingo]==", "===onPause(start)===");
        Log.d("==[Ac_Bingo]==", "===onPause(end)===");
    }


    private void refreshData() {
//        item.clear();
//        pagesLoaded = 1;
//        onScrollListener.reset();
        Log.d("==[Ac_Bingo]==", "===refreshData(start)===");
        loadData();
        Log.d("==[Ac_Bingo]==", "===refreshData(end)===");
    }


    private void loadData(){
        ProgressDialog progressDialog = showLoadingProgressDialog(this, getString(R.string.loadingMessage));
        progressDialog.show();

        Log.d("==[Ac_Bingo]==", "===loadData(start)===");

        //建立API代理
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://104.155.211.29:8081").build();
        Bingo bgApi = restAdapter.create(Bingo.class);

        AppObservable.bindActivity(this, bgApi.showApi("123456"))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BingoListingResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("loadData", "===onCompleted123===");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        runOnUiThread(dialog::dismiss);
                        Log.d("loadData", "===onError===");
                    }

                    @Override
                    public void onNext(BingoListingResponse response) {
                        Log.d("loadData", "===onNext===");
                        itemsA = response.getResultAList();
                        itemsB = response.getResultBList();
                        itemsC = response.getResultCList();
                        runOnUiThread(() -> {
                            setResultA();
                            setResultB();
                            setResultC();
                        });
                    }
                });

        Log.d("==[Ac_Bingo]==", "===loadData(end)===");
    }

    private void setResultA() {
        Log.d("==[Ac_Bingo]==", "===setResultA===");
        String temp = "bingoARNO";
        for(int i=0;i<itemsA.size(); i++){

            if(i<9){
                temp = temp + "0" + (i+1);
            }else{
                temp = temp + (i+1);
            }

            if(i==0){
                bingoARNO01.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO01, itemsA.get(i).getISC());
            }else if(i==1){
                bingoARNO02.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO02, itemsA.get(i).getISC());
            }else if(i==2){
                bingoARNO03.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO03, itemsA.get(i).getISC());
            }else if(i==3){
                bingoARNO04.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO04, itemsA.get(i).getISC());
            }else if(i==4){
                bingoARNO05.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO05, itemsA.get(i).getISC());
            }else if(i==5){
                bingoARNO06.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO06, itemsA.get(i).getISC());
            }else if(i==6){
                bingoARNO07.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO07, itemsA.get(i).getISC());
            }else if(i==7){
                bingoARNO08.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO08, itemsA.get(i).getISC());
            }else if(i==8){
                bingoARNO09.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO09, itemsA.get(i).getISC());
            }else if(i==9){
                bingoARNO10.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO10, itemsA.get(i).getISC());
            }else if(i==10){
                bingoARNO11.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO11, itemsA.get(i).getISC());
            }else if(i==11){
                bingoARNO12.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO12, itemsA.get(i).getISC());
            }else if(i==12){
                bingoARNO13.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO13, itemsA.get(i).getISC());
            }else if(i==13){
                bingoARNO14.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO14, itemsA.get(i).getISC());
            }else if(i==14){
                bingoARNO15.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO15, itemsA.get(i).getISC());
            }else if(i==15){
                bingoARNO16.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO16, itemsA.get(i).getISC());
            }else if(i==16){
                bingoARNO17.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO17, itemsA.get(i).getISC());
            }else if(i==17){
                bingoARNO18.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO18, itemsA.get(i).getISC());
            }else if(i==18){
                bingoARNO19.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO19, itemsA.get(i).getISC());
            }else if(i==19){
                bingoARNO20.setText(itemsA.get(i).getNO());
                setHighLight(bingoARNO20, itemsA.get(i).getISC());
            }
        }
    }


    private void setResultB() {
        Log.d("==[Ac_Bingo]==", "===setResultB===");
        String temp = "bingoBRNO";
        for(int i=0;i<itemsB.size(); i++){

            if(i<9){
                temp = temp + "0" + (i+1);
            }else{
                temp = temp + (i+1);
            }

            if(i==0){
                bingoBRNO01.setText(itemsB.get(i).getNO());
            }else if(i==1){
                bingoBRNO02.setText(itemsB.get(i).getNO());
            }else if(i==2){
                bingoBRNO03.setText(itemsB.get(i).getNO());
            }else if(i==3){
                bingoBRNO04.setText(itemsB.get(i).getNO());
            }else if(i==4){
                bingoBRNO05.setText(itemsB.get(i).getNO());
            }else if(i==5){
                bingoBRNO06.setText(itemsB.get(i).getNO());
            }else if(i==6){
                bingoBRNO07.setText(itemsB.get(i).getNO());
            }else if(i==7){
                bingoBRNO08.setText(itemsB.get(i).getNO());
            }else if(i==8){
                bingoBRNO09.setText(itemsB.get(i).getNO());
            }else if(i==9){
                bingoBRNO10.setText(itemsB.get(i).getNO());
            }
        }
    }


    private void setResultC() {
        Log.d("==[Ac_Bingo]==", "===setResultC===");
        String temp = "bingoCRNO";
        for(int i=0;i<itemsC.size(); i++){

            if(i<9){
                temp = temp + "0" + (i+1);
            }else{
                temp = temp + (i+1);
            }

            if(i==0){
                bingoCRNO01.setText(itemsC.get(i).getNO());
            }else if(i==1){
                bingoCRNO02.setText(itemsC.get(i).getNO());
            }else if(i==2){
                bingoCRNO03.setText(itemsC.get(i).getNO());
            }else if(i==3){
                bingoCRNO04.setText(itemsC.get(i).getNO());
            }else if(i==4){
                bingoCRNO05.setText(itemsC.get(i).getNO());
            }else if(i==5){
                bingoCRNO06.setText(itemsC.get(i).getNO());
            }else if(i==6){
                bingoCRNO07.setText(itemsC.get(i).getNO());
            }else if(i==7){
                bingoCRNO08.setText(itemsC.get(i).getNO());
            }else if(i==8){
                bingoCRNO09.setText(itemsC.get(i).getNO());
            }else if(i==9){
                bingoCRNO10.setText(itemsC.get(i).getNO());
            }
        }
    }

    void setHighLight(TextView t, int isc){
        if(isc==3){
            t.setTextColor(Color.RED);
        }
    }


    //顯示loading畫面
    public ProgressDialog showLoadingProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }


}
