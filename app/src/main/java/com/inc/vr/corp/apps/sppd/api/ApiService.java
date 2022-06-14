package com.inc.vr.corp.apps.sppd.api;

import com.inc.vr.corp.apps.sppd.model.PegawaiModel;
import com.inc.vr.corp.apps.sppd.model.PerjalananModel;
import com.inc.vr.corp.apps.sppd.model.ResponseBasicModel;
import com.inc.vr.corp.apps.sppd.model.ResponseNPPD;
import com.inc.vr.corp.apps.sppd.model.ResponsePegawaiModel;
import com.inc.vr.corp.apps.sppd.model.ResponsePerjalananModel;
import com.inc.vr.corp.apps.sppd.model.ResponseRiwayatModel;
import com.inc.vr.corp.apps.sppd.model.ResponseSptModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;

public interface ApiService {
    //public static final String BASE_URL = "http://192.168.43.235/sppd/api/";
    public static final String BASE_URL = "http://sppd.epogame.my.id/api/";
    public static final String LOGIN_URL = BASE_URL + "login.php";
    public static final String RIWAYAT_URL = BASE_URL + "riwayat.php";
    public static final String SPT_URL = BASE_URL + "spt.php";
    public static final String PERJALANAN_URL = BASE_URL + "perjalanan.php";
    public static final String GET_BASIC_URL = BASE_URL + "get_basic.php";
    public static final String INPUT_NPPD_URL = BASE_URL + "input_nppd.php";

    //Form Login
    @FormUrlEncoded
    @retrofit2.http.POST(LOGIN_URL)
    Call<ResponsePegawaiModel> login(
            @retrofit2.http.Field("username") String username,
            @retrofit2.http.Field("password") String password,
            @retrofit2.http.Field("apps") String apps
            );
    //form input nppd
    @FormUrlEncoded
    @retrofit2.http.POST(INPUT_NPPD_URL)
    Call<ResponseNPPD> input_nppd(
            @retrofit2.http.Field("id_pegawai") String id_pegawai,
            @retrofit2.http.Field("id_tujuan") String id_tujuan,
            @retrofit2.http.Field("id_transportasi") String id_transportasi,
            @retrofit2.http.Field("maksud") String maksud,
            @retrofit2.http.Field("tgl_pergi") String tgl_pergi,
            @retrofit2.http.Field("tgl_kembali") String tgl_kembali,
            @retrofit2.http.Field("lama") String lama,
            @retrofit2.http.Field("apps") String apps
    );

    @retrofit2.http.GET(RIWAYAT_URL)
    Call<ResponseRiwayatModel> get_riwayat(
            @retrofit2.http.Query("apps") String apps
    );
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @retrofit2.http.POST(RIWAYAT_URL)
    Call<ResponseRiwayatModel> ubah_riwayat(
            @retrofit2.http.Field("ubah") String ubah,
            @retrofit2.http.Field("id_nppt") String id_nppt,
            @retrofit2.http.Field("maksud") String maksud
    );
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @retrofit2.http.POST(RIWAYAT_URL)
    Call<ResponseRiwayatModel> hapus_riwayat(
            @retrofit2.http.Field("hapus") String hapus,
            @retrofit2.http.Field("id_nppt") String id_nppt
    );

    @retrofit2.http.GET(PERJALANAN_URL)
    Call<ResponsePerjalananModel> get_perjalanan(
            @retrofit2.http.Query("apps") String apps
    );
    @FormUrlEncoded
    @retrofit2.http.POST(PERJALANAN_URL)
    @Headers("Accept: application/json")
    Call<ResponsePerjalananModel> ubah_perjalanan(
            @retrofit2.http.Field("edit") String edit,
            @retrofit2.http.Field("id_lpd") String id_lpd,
            @retrofit2.http.Field("hasil") String hasil
    );
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @retrofit2.http.POST(PERJALANAN_URL)
    Call<ResponsePerjalananModel> hapus_perjalanan(
            @retrofit2.http.Field("hapus") String hapus,
            @retrofit2.http.Field("id_lpd") String id_lpd
    );

    @retrofit2.http.GET(GET_BASIC_URL)
    @Headers("Accept: application/json")
    Call<ResponseBasicModel> get_basic(
            @retrofit2.http.Query("apps") String apps
    );

    @retrofit2.http.GET(SPT_URL)
    @Headers("Accept: application/json")
    Call<ResponseSptModel> get_spt(
            @retrofit2.http.Query("apps") String apps
    );
}
