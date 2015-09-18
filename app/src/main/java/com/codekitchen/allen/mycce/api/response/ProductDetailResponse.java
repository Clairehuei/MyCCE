package com.codekitchen.allen.mycce.api.response;

import com.codekitchen.allen.mycce.api.model.ProductDetailResult;
import com.google.gson.annotations.SerializedName;

public class ProductDetailResponse {

    @SerializedName("result")
    ProductDetailResult dataResult;

    public ProductDetailResult getDataResult() {
        return dataResult;
    }
}
