package com.codekitchen.allen.mycce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codekitchen.allen.mycce.adapter.ProductListingItemAdapter;
import com.codekitchen.allen.mycce.api.ApiComponent;
import com.codekitchen.allen.mycce.api.ApplicationModule;
import com.codekitchen.allen.mycce.api.DaggerApiComponent;
import com.codekitchen.allen.mycce.api.Product;
import com.codekitchen.allen.mycce.api.model.ProductListingItem;
import com.codekitchen.allen.mycce.api.response.ApiModule;
import com.codekitchen.allen.mycce.api.response.ProductListingResponse;
import com.codekitchen.allen.mycce.events.ProductListingItemClickedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Activity_Prd_Query_Result extends AppCompatActivity {


    @InjectView(R.id.recycler_view)
    RecyclerView recyclerView;

    LinearLayoutManager layoutManager;

    @Inject
    Bus bus;

    @Inject
    Product product;

    List<ProductListingItem> items = new ArrayList<>();
    ProductListingItemAdapter adapter;

    //[呼叫api返回查詢結果list顯示流程->01]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("==[Ac_Prd]==","===onCreate(start)===");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_query_result);
        ButterKnife.inject(this);

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);

        if (layoutManager == null) {//若不設定 會報 NullpointException
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
        }

        adapter = new ProductListingItemAdapter(this, bus, items);
        recyclerView.setAdapter(adapter);
        Log.d("==[Ac_Prd]==", "===onCreate(end)===");
    }

    //[呼叫api返回查詢結果list顯示流程->03]
    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        Log.d("==[Ac_Prd]==", "===onResume(start)===");
        refreshData();
        Log.d("==[Ac_Prd]==", "===onResume(end)===");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("==[Ac_Prd]==", "===onPause(start)===");
        bus.unregister(this);
        Log.d("==[Ac_Prd]==", "===onPause(end)===");
    }

    //[呼叫api返回查詢結果list顯示流程->04]
    private void refreshData() {
//        item.clear();
//        pagesLoaded = 1;
//        onScrollListener.reset();
        Log.d("==[Ac_Prd]==", "===refreshData(start)===");
        loadData();
        Log.d("==[Ac_Prd]==", "===refreshData(end)===");
    }

    //[呼叫api返回查詢結果list顯示流程->05]
    private void loadData(){
        ProgressDialog progressDialog = showLoadingProgressDialog(this, getString(R.string.loadingMessage));
        progressDialog.show();

        Log.d("==[Ac_Prd]==", "===loadData(start)===");

        String tokenId = "FQ0NLFMTSQyMSDU0ZNE0W6ODMoxMPTAxQMDEDyMDIE0MIDEwGMTEX5MTCQ65NYirY5oCA76IBKh5WLu9E5pySJ6ZQmQ5HYWsD5Y%2BJ4%0AOEjIwUMTUYwOTEE4MIDk1GNDQJ5MjQk0OTjIwGMTUPwOTLE5MNDk1ENDQD5MjCk0";

        //建立API代理
//        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://202.173.38.84").build();
//        Product pdApi = restAdapter.create(Product.class);

        AppObservable.bindActivity(this, product.queryByVendor("v1", tokenId, "9", "110101201401018737", 1, 20))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductListingResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("loadData", "===onCompleted123===");
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("loadData", "===onError===");
                    }


                    //[呼叫api返回查詢結果list顯示流程->06]
                    @Override
                    public void onNext(ProductListingResponse response) {
                        Log.d("==[Ac_Prd]==", "===loadData(onNext)-1===");
                        if (response != null) {
                            Log.d("==[Ac_Prd]==", "===loadData(onNext)-2===");
                            items = response.getResult().getProductList();
                            Log.d("==[Ac_Prd]==", "===loadData(onNext)-3===");
                            adapter.updateItems(items);
                            Log.d("==[Ac_Prd]==", "===loadData(onNext)-4===");

                            //[呼叫api返回查詢結果list顯示流程->08]
                            runOnUiThread(adapter::notifyDataSetChanged);
                            Log.d("==[Ac_Prd]==", "===loadData(onNext)-5===");
                        } else {
                            Log.d("loadData", "===response is null===");
                        }
                    }
                });

        Log.d("==[Ac_Prd]==", "===loadData(end)===");
    }


    //顯示loading畫面
    public ProgressDialog showLoadingProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        return progressDialog;
    }


    @Subscribe
    public void onEvent(ProductListingItemClickedEvent event) {
        Intent intent = new Intent(this, Activity_Prd_Detail.class);
        intent.putExtra(Activity_Prd_Detail.ID_DATA, event.getSequenceId());
        startActivity(intent);
    }
}
