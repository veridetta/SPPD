package com.inc.vr.corp.apps.sppd.api;

import com.inc.vr.corp.apps.sppd.model.PegawaiModel;
import com.inc.vr.corp.apps.sppd.model.ResponsePegawaiModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;

public interface ApiService {
    public static final String BASE_URL = "http://192.168.43.235/sppd/api/";
    public static final String LOGIN_URL = BASE_URL + "login.php";


    //Form Login
    @FormUrlEncoded
    @retrofit2.http.POST(LOGIN_URL)
    Call<ResponsePegawaiModel> login(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("apps") String apps

            );

}
