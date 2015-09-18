package com.codekitchen.allen.mycce.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cloud on 5/13/15.
 */
public class ProductItem extends Spec implements Parcelable {

    @SerializedName("prdSeqId")
    protected String sequenceId;

    @SerializedName("prdName")
    protected String name;

    @SerializedName("imageUrlList")
    protected List<ImageUrl> imageUrls = new ArrayList<>();

    @SerializedName("compName")
    protected String companyTitle;

    @SerializedName("prdStatus")
    protected String prdStatus;

    @SerializedName("onlineStatus")
    protected String onlineStatus;

    @SerializedName("onlineStatusTime")
    protected String onlineStatusTime;

    @SerializedName("updateTime")
    protected String updateTime;

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String companyTitle() {
        return companyTitle;
    }

    public String productSequenceId() {
        return sequenceId;
    }

    public String productName() {
        return name != null ? name : "";
    }

    public List<ImageUrl> getImageUrls() {
        return imageUrls;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public String getOnlineStatusTime() {
        return onlineStatusTime;
    }

    public String getPrdStatus() {
        return prdStatus;
    }

    public ProductItem() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sequenceId);
        dest.writeString(this.name);
        dest.writeTypedList(imageUrls);
        dest.writeString(this.companyTitle);
        dest.writeString(this.prdStatus);
        dest.writeString(this.onlineStatus);
        dest.writeString(this.updateTime);
        dest.writeString(this.sellSequenceId);
        dest.writeValue(this.price);
        dest.writeString(this.unit);
        dest.writeString(this.maxOrderQuantity);
        dest.writeString(this.minOrderQuantity);
        dest.writeString(this.packageDesc);
    }

    private ProductItem(Parcel in) {
        this.sequenceId = in.readString();
        this.name = in.readString();
        in.readTypedList(imageUrls, ImageUrl.CREATOR);
        this.companyTitle = in.readString();
        this.prdStatus = in.readString();
        this.onlineStatus = in.readString();
        this.updateTime = in.readString();
        this.sellSequenceId = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.unit = in.readString();
        this.maxOrderQuantity = in.readString();
        this.minOrderQuantity = in.readString();
        this.packageDesc = in.readString();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        public ProductItem createFromParcel(Parcel source) {
            return new ProductItem(source);
        }

        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };
}
