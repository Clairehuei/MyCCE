package com.codekitchen.allen.mycce.api.response;

import com.codekitchen.allen.mycce.api.model.BingoDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 6193 on 2015/9/18.
 */
public class BingoListingResponse {

    @SerializedName("resultA")
    List<BingoDetail> resultAList;

    @SerializedName("resultB")
    List<BingoDetail> resultBList;

    @SerializedName("resultC")
    List<BingoDetail> resultCList;


    public List<BingoDetail> getResultAList() {
        return resultAList;
    }

    public List<BingoDetail> getResultBList() {
        return resultBList;
    }

    public List<BingoDetail> getResultCList() {
        return resultCList;
    }
}
