package com.codekitchen.allen.mycce.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 6193 on 2015/9/18.
 */
public class BingoDetail implements Parcelable {

    protected BingoDetail(Parcel in) {
        this.NO = in.readString();
        this.ISC = in.readInt();
        this.PERIODS = in.readString();
    }


    @SerializedName("NO")
    protected String NO;

    @SerializedName("ISC")
    protected int ISC;

    @SerializedName("PERIODS")
    protected String PERIODS;

    public void setNO(String NO) {
        this.NO = NO;
    }

    public void setISC(int ISC) {
        this.ISC = ISC;
    }

    public void setPERIODS(String PERIODS) {
        this.PERIODS = PERIODS;
    }

    public String getNO() {
        return NO;
    }

    public int getISC() {
        return ISC;
    }

    public String getPERIODS() {
        return PERIODS;
    }


    public static final Creator<BingoDetail> CREATOR = new Creator<BingoDetail>() {
        @Override
        public BingoDetail createFromParcel(Parcel in) {
            return new BingoDetail(in);
        }

        @Override
        public BingoDetail[] newArray(int size) {
            return new BingoDetail[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.NO);
        dest.writeInt(this.ISC);
        dest.writeString(this.PERIODS);
    }
}
