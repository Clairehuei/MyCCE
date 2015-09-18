package com.codekitchen.allen.mycce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.codekitchen.allen.mycce.api.ApiComponent;
import com.codekitchen.allen.mycce.api.ApplicationModule;
import com.codekitchen.allen.mycce.api.DaggerApiComponent;
import com.codekitchen.allen.mycce.api.Product;
import com.codekitchen.allen.mycce.api.model.ProductDetail;
import com.codekitchen.allen.mycce.api.response.ApiModule;
import com.codekitchen.allen.mycce.api.response.ProductDetailResponse;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Subscriber;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Activity_Prd_Detail extends AppCompatActivity {

    public static final String ID_DATA = "product_id_key";

    @InjectView(R.id.product_name)
    TextView productName;

    @InjectView(R.id.product_origin)
    TextView productOrigin;

    @Inject
    Product product;

    String productSequenceId;
    private ProductDetail productDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prd_detail);
        ButterKnife.inject(this);

        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);


        productSequenceId = getIntent().getStringExtra(ID_DATA);
        loadProductDetail();
    }



    private void loadProductDetail() {


        String tokenId = "FQ0NLFMTSQyMSDU0ZNE0W6ODMoxMPTAxQMDEDyMDIE0MIDEwGMTEX5MTCQ65NYirY5oCA76IBKh5WLu9E5pySJ6ZQmQ5HYWsD5Y%2BJ4%0AOEjIwUMTUYwOTEE4MIDk1GNDQJ5MjQk0OTjIwGMTUPwOTLE5MNDk1ENDQD5MjCk0";

        Log.d("=productSequenceId=", productSequenceId);

        AppObservable.bindActivity(this, product.getDetail("v1", tokenId, productSequenceId))
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductDetailResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d("loadProductDetail", "===onCompleted123===");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        runOnUiThread(dialog::dismiss);
                        Log.d("loadProductDetail", "===onError===");
                    }

                    @Override
                    public void onNext(ProductDetailResponse response) {

                        productDetail = response.getDataResult().getProductDetail();
                        runOnUiThread(() -> {
                            setDetail();
//                            setImage();
                        });
                    }
                });
    }


    private void setDetail() {
        productName.setText(productDetail.productName());
        productOrigin.setText(productDetail.getOrgnCity());
    }


}
