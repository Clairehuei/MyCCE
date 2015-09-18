package com.codekitchen.allen.mycce.api.response;

import com.codekitchen.allen.mycce.api.model.ProductListingResult;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 6193 on 2015/9/15.
 */
public class ProductListingResponse {


    @SerializedName("result")
    ProductListingResult result;

    public ProductListingResult getResult() {
        return result;
    }
}
