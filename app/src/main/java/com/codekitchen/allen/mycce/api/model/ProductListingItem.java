package com.codekitchen.allen.mycce.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 6193 on 2015/9/15.
 */
public class ProductListingItem {
    @SerializedName("prdSeqId")
    String id;

    @SerializedName("prdName")
    String name;

    @SerializedName("imageUrl")
    String imageUrls;

    @SerializedName("cateLevel")
    String category;

    @SerializedName("sellPrice")
    String price;

    @SerializedName("chargeUnitName")
    String unit;

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    private Double getPrice() {
        return Double.parseDouble(price);
    }
}
