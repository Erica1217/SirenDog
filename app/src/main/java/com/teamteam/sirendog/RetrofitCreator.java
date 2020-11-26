package com.teamteam.sirendog;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreator {

    private static Retrofit retrofit;
    private static SafetyMapService service;
    private static final String URL = "http://www.safe182.go.kr/api/lcm/";

    public static SafetyMapService getService() {
        if (retrofit == null) {
            retrofit = create();
        }
        if(service == null) {
            service = retrofit.create(SafetyMapService.class);
        }
        return service;
    }

    private static Retrofit create()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
