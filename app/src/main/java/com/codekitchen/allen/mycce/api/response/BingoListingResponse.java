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


    public List<BingoDetail> getResultAList() {
        return resultAList;
    }
}
