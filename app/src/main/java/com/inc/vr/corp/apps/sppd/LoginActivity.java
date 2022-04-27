package com.inc.vr.corp.apps.sppd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inc.vr.corp.apps.sppd.api.ApiClient;
import com.inc.vr.corp.apps.sppd.api.ApiService;
import com.inc.vr.corp.apps.sppd.model.PegawaiModel;
import com.inc.vr.corp.apps.sppd.model.ResponsePegawaiModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnLogin;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        username.setText("196103111 198804 1 001");
        password.setText("196103111 198804 1 001");
        btnLogin.setOnClickListener(v -> {
            if(cekKoneksi(getApplicationContext())){
                //jika koneksi ada
                if (validateLogin(username.getText().toString(), password.getText().toString())){
                    //jika validasi login berhasil
                    dologin(username.getText().toString(), password.getText().toString());
                }
            }
        });
    }
    //fungsi validate login
    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    //fungsi login
    private void dologin(String xusername, String xpassword){
        ApiClient client = new ApiClient();
        client.getServies().login(xusername, xpassword,"android").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    //jika login berhasil
                    ResponsePegawaiModel responseModel = (ResponsePegawaiModel) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        Log.d("Login", "onResponse: " + responseModel.getData().getNama());
                        //save value to sharedpreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", xusername);
                        editor.putString("password", xpassword);
                        editor.putString("nama", responseModel.getData().getNama());
                        editor.putString("nip", responseModel.getData().getNip());
                        editor.putString("jabatan", responseModel.getData().getJabatan());
                        editor.putString("id", responseModel.getData().getId());
                        editor.putString("id_golongan", responseModel.getData().getId_golongan());
                        //pangkat
                        editor.putString("pangkat", responseModel.getData().getPangkat());
                        //unitkerja
                        editor.putString("unitkerja", responseModel.getData().getUnitkerja());
                        //isLogin
                        editor.putBoolean("isLogin", true);
                        editor.apply();
                        //pindah activity
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Log.d("Login", "onResponse: " + response.message());
                    //jika login gagal
                    Toast.makeText(getApplicationContext(), "Login Gagal 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Login Gagal 2", Toast.LENGTH_LONG).show();
            }
        });
    }
    //fungsi cek koneksi
    public boolean cekKoneksi(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //for api 29 and above
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if(capabilities != null){
                if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)){
                    return true;
                }
                else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    return true;
                }
                else if(capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }else{
            //for api level below 29
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED){
                return true;
            }else{
                return false;
            }
        }
    }

}