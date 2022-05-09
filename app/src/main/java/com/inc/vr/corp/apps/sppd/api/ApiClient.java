package com.inc.vr.corp.apps.sppd.api;

import java.security.AuthProvider;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiService apiService;

    public ApiClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sppd.epogame.my.id/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getServies(){
        return apiService;
    }

}
