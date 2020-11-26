package com.teamteam.sirendog;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SafetyMapService {

    @FormUrlEncoded
    @POST("safeMap.do")
    Call<SafetyMapResponse> getSafetyMap(@Field("esntlId") String s,
                                         @Field("authKey") String authoKey,
                                         @Field("pageIndex") int pageIndex,
                                         @Field("pageUnit") int pageUnit,
                                         @Field("minX") Double minX,
                                         @Field("maxX") Double maxX,
                                         @Field("minY") Double minY,
                                         @Field("maxY") Double maxY,
                                         @Field("detailDate1")  String[] datailDate,
                                         @Field("xmlUseYN") String xmlUseYN);

    @FormUrlEncoded
    @POST("safeMap.do")
    Call<SafetyMapResponse> getSafetyMap(@Field("esntlId") String s,
                                         @Field("authKey") String authoKey,
                                         @Field("pageIndex") int pageIndex,
                                         @Field("pageUnit") int pageUnit,
                                         @Field("detailDate1")  String[] datailDate,
                                         @Field("xmlUseYN") String xmlUseYN);


}
