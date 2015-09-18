package com.codekitchen.allen.mycce;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.codekitchen.allen.mycce.api.response.ProductDetailResponse;

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


    List<BingoDetail> items = new ArrayList<>();

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
                        items = response.getResultAList();
//                        productDetail = response.getDataResult().getProductDetail();
                        runOnUiThread(() -> {
                            setDetail();
                        });
                    }
                });

        Log.d("==[Ac_Bingo]==", "===loadData(end)===");
    }

    private void setDetail() {
        Log.d("==[Ac_Bingo]==", "===setDetail===");
        String temp = "bingoARNO";
        for(int i=0;i<items.size(); i++){

            if(i<9){
                temp = temp + "0" + (i+1);
            }else{
                temp = temp + (i+1);
            }


            if(i==0){
                Log.d("==[Ac_Bingo]==", "i=0 =>"+items.get(i).getNO());
                bingoARNO01.setText(items.get(i).getNO());
            }else if(i==1){
                Log.d("==[Ac_Bingo]==", "i=1 =>"+items.get(i).getNO());
                bingoARNO02.setText(items.get(i).getNO());
            }else if(i==2){
                Log.d("==[Ac_Bingo]==", "i=2 =>"+items.get(i).getNO());
                bingoARNO03.setText(items.get(i).getNO());
            }else if(i==3){
                Log.d("==[Ac_Bingo]==", "i=3 =>"+items.get(i).getNO());
                bingoARNO04.setText(items.get(i).getNO());
            }else if(i==4){
                Log.d("==[Ac_Bingo]==", "i=4 =>"+items.get(i).getNO());
                bingoARNO05.setText(items.get(i).getNO());
            }else if(i==5){
                bingoARNO06.setText(items.get(i).getNO());
            }else if(i==6){
                bingoARNO07.setText(items.get(i).getNO());
            }else if(i==7){
                bingoARNO08.setText(items.get(i).getNO());
            }else if(i==8){
                bingoARNO09.setText(items.get(i).getNO());
            }else if(i==9){
                bingoARNO10.setText(items.get(i).getNO());
            }else if(i==10){
                bingoARNO11.setText(items.get(i).getNO());
            }else if(i==11){
                bingoARNO12.setText(items.get(i).getNO());
            }else if(i==12){
                bingoARNO13.setText(items.get(i).getNO());
            }else if(i==13){
                bingoARNO14.setText(items.get(i).getNO());
            }else if(i==14){
                bingoARNO15.setText(items.get(i).getNO());
            }else if(i==15){
                bingoARNO16.setText(items.get(i).getNO());
            }else if(i==16){
                bingoARNO17.setText(items.get(i).getNO());
            }else if(i==17){
                bingoARNO18.setText(items.get(i).getNO());
            }else if(i==18){
                bingoARNO19.setText(items.get(i).getNO());
            }else if(i==19){
                bingoARNO20.setText(items.get(i).getNO());
            }
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
