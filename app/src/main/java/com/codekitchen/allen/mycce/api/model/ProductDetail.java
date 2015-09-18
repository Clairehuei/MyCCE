package com.codekitchen.allen.mycce.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetail implements Parcelable {

    @SerializedName("prdSeqId")
    protected String sequenceId;

    @SerializedName("prdName")
    protected String name;

    @SerializedName("orgnCity")
    String orgnCity;


    public String getOrgnCity() {
        return orgnCity;
    }

    public void setOrgnCity(String orgnCity) {
        this.orgnCity = orgnCity;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String productName() {
        return name != null ? name : "";
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orgnCity);
        dest.writeString(this.sequenceId);
        dest.writeString(this.name);
    }

    private ProductDetail(Parcel in) {
        this.orgnCity = in.readString();
        this.sequenceId = in.readString();
        this.name = in.readString();
//        in.readTypedList(imageUrls, ImageUrl.CREATOR);
    }

    public static final Creator<ProductDetail> CREATOR = new Creator<ProductDetail>() {
        public ProductDetail createFromParcel(Parcel source) {
            return new ProductDetail(source);
        }

        public ProductDetail[] newArray(int size) {
            return new ProductDetail[size];
        }
    };
}
