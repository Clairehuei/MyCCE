package com.codekitchen.allen.mycce.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageUrl implements Parcelable {

    @SerializedName("imageUrl")
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
    }

    public ImageUrl() {
    }

    private ImageUrl(Parcel in) {
        this.imageUrl = in.readString();
    }

    public static final Creator<ImageUrl> CREATOR = new Creator<ImageUrl>() {
        public ImageUrl createFromParcel(Parcel source) {
            return new ImageUrl(source);
        }

        public ImageUrl[] newArray(int size) {
            return new ImageUrl[size];
        }
    };
}
