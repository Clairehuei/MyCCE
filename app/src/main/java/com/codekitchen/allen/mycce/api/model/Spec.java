package com.codekitchen.allen.mycce.api.model;

import com.codekitchen.allen.mycce.NumberUtil;
import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

/**
 * An object used to keep the detail specification of product.
 */
public class Spec {

    private final DecimalFormat df = new DecimalFormat("0.##");

    @SerializedName("sellSeqId")
    protected String sellSequenceId;

    @SerializedName("sellPrice")
    protected Double price;

    @SerializedName("chargeUnitName")
    protected String unit;

    @SerializedName("maxOrdQty")
    protected String maxOrderQuantity;

    @SerializedName("minOrdQty")
    protected String minOrderQuantity;

    @SerializedName("packageDesc")
    protected String packageDesc;

    public String sellSequenceId() {
        return sellSequenceId;
    }

    public Double price() {
        return price;
    }

    public String unit() {
        return unit;
    }

    public String maxOrderQuantity() {
        return maxOrderQuantity;
    }

    public String minOrderQuantity() {
        return minOrderQuantity;
    }

    public String packageDesc() {
        return packageDesc;
    }

    public String formatPrice() {
        return NumberUtil.formatPrice(price);
    }

    public void setPackageDesc(String packageDesc) {
        this.packageDesc = packageDesc;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setMaxOrderQuantity(String maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
    }

    public void setMinOrderQuantity(String minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
    }
}