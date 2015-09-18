package com.codekitchen.allen.mycce.api.model;

import com.google.gson.annotations.SerializedName;

public class ProductDetailResult {

    @SerializedName("dataObject")
    ProductDetail productDetail;

    public ProductDetail getProductDetail() {
        return productDetail;
    }
}
