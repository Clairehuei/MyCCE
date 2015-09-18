package com.codekitchen.allen.mycce.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 6193 on 2015/9/15.
 */
public class ProductListingResult {
    @SerializedName("totalPage")
    private int totalPages;

    @SerializedName("totalRecord")
    private int totalRecords;

    @SerializedName("pageResult")
    List<ProductListingItem> productList;

    public int getTotalRecords() {
        return totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ProductListingItem> getProductList() {
        return productList;
    }
}
