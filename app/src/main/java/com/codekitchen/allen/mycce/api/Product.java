package com.codekitchen.allen.mycce.api;

import com.codekitchen.allen.mycce.api.response.ProductDetailResponse;
import com.codekitchen.allen.mycce.api.response.ProductListingResponse;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by 6193 on 2015/9/15.
 */
public interface Product {
    String pathPrefix = "/APCCE/api/{version}/product/";

    @FormUrlEncoded
    @POST(pathPrefix + "queryByVendor")
    Observable<ProductListingResponse> queryByVendor(
            @Path("version") String version,
            @Field("tokenId") String tokenId,
            @Field("compId") String companyId,
            @Field("compBan") String vatNumber,
            @Field("currentPage") int page,
            @Field("pageSize") int size
    );


    @FormUrlEncoded
    @POST(pathPrefix + "getDetail")
    Observable<ProductDetailResponse> getDetail(
            @Path("version") String version,
            @Field("tokenId") String tokenId,
            @Field("prdSeqId") String prdseqid
    );
}
