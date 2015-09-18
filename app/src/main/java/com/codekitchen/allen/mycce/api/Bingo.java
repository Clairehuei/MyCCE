package com.codekitchen.allen.mycce.api;

import com.codekitchen.allen.mycce.api.response.BingoListingResponse;
import com.codekitchen.allen.mycce.api.response.ProductListingResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by 6193 on 2015/9/18.
 */
public interface Bingo {
    String pathPrefix = "/netWin/lotto/";

    @FormUrlEncoded
    @POST(pathPrefix + "showApi")
    Observable<BingoListingResponse> showApi(
            @Field("perdios") String perdios
    );
}
