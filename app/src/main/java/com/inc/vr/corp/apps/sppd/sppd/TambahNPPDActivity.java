package com.inc.vr.corp.apps.sppd.sppd;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.inc.vr.corp.apps.sppd.R;
import com.inc.vr.corp.apps.sppd.api.ApiClient;
import com.inc.vr.corp.apps.sppd.model.PegawaiListModel;
import com.inc.vr.corp.apps.sppd.model.ResponseBasicModel;
import com.inc.vr.corp.apps.sppd.model.TransportasiModel;
import com.inc.vr.corp.apps.sppd.model.TujuanListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class TambahNPPDActivity extends AppCompatActivity {

    EditText inputTujuan, inputPergi, inputPulang, inputLama;
    Spinner inputLokasi;
    MultiSpinnerSearch inputPegawai, inputTransportasi;
    Button btnSimpan, btnClear;

    //variable untuk input ke nppt
    String idPegawai="";
    String idTujuan;
    String idTransportasi = "";
    String durasi;
    String tglPergi;
    String tglKembali;
    String maksud;
    String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_nppdactivity);
        navbar();
        setupView();
        getBasic();
        btnAction();
    }

    protected void navbar(){
        //add back button navbar
        ActionBar actionBar = getSupportActionBar();
    }
    protected void setupView(){
        inputTujuan = findViewById(R.id.input_tujuan);
        inputPergi = findViewById(R.id.input_tanggal_pergi);
        inputPulang = findViewById(R.id.input_tanggal_pulng);
        inputLokasi = findViewById(R.id.input_lokasi);
        inputPegawai = findViewById(R.id.input_pegawai);
        inputTransportasi = findViewById(R.id.input_transportasi);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnClear = findViewById(R.id.btn_clear);
    }

    protected void singleSpinner(
            List<TujuanListModel> tujuanList){
       //spinner adapter
        String[] lokasi = null;
        lokasi = new String[tujuanList.size()];
        for (int p =0; p<tujuanList.size(); p++){
            //insert to lokasi
            lokasi[p] = tujuanList.get(p).getId()+"-"+tujuanList.get(p).getTujuan();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lokasi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputLokasi.setAdapter(adapter);
    }

    protected void multipleSelectSetup(List<PegawaiListModel> pegawaList, List<TransportasiModel> transportasiList){
        inputPegawai.setSearchEnabled(true);
        inputPegawai.setSearchHint("Pilih beberapa pegawai");
        inputPegawai.setEmptyTitle("Data tidak ditemukan");
        inputPegawai.setShowSelectAllButton(true);
        inputPegawai.setClearText("Tutup & Bersihkan");
        //pegawai
        List<KeyPairBoolData> listPegawai = new ArrayList<>();
        //add from pegawaiList
        for (int i = 0; i < pegawaList.size(); i++) {
            listPegawai.add(new KeyPairBoolData(pegawaList.get(i).getId()
                    +"-"+pegawaList.get(i).getNama(), false));
        }
        final Integer[] yy = {0};
        inputPegawai.setItems(listPegawai, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        yy[0]++;
                        if (yy[0] > 1){
                            idPegawai += "-"+items.get(i).getName().split("-")[0].trim();
                        }else{
                            idPegawai += items.get(i).getName().split("-")[0].trim();
                        }
                        Timber.d("Item %d: %s", i, items.get(i).getName());
                    }
                }
            }
            public void onClear() {
                yy[0] = 0;
                Timber.d("Items cleared!");
            }
            public void onItemUnselected(KeyPairBoolData item) {
                yy[0]--;
            }
        });

        inputTransportasi.setSearchEnabled(true);
        inputTransportasi.setSearchHint("Pilih beberapa transportasi");
        inputTransportasi.setEmptyTitle("Data tidak ditemukan");
        inputTransportasi.setShowSelectAllButton(true);
        inputTransportasi.setClearText("Tutup & Bersihkan");
        //transportasi
        List<KeyPairBoolData> listTransportasi = new ArrayList<>();
        //add from pegawaiList
        for (int i = 0; i < transportasiList.size(); i++) {
            listTransportasi.add(new KeyPairBoolData(transportasiList.get(i).getId()
                    +"-"+transportasiList.get(i).getTransportasi(), false));
        }
        //set item
        final Integer[] xx = {0};
        inputTransportasi.setItems(listTransportasi, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                       xx[0]++;
                       if (xx[0] > 1){
                           idTransportasi += "-"+items.get(i).getName().split("-")[0].trim();
                       }else{
                           idTransportasi += items.get(i).getName().split("-")[0].trim();
                       }
                        Timber.d("Item %d: %s", i, items.get(i).getName());
                    }
                }
            }
            //on clear
            public void onClear() {
                xx[0] = 0;
                Timber.d("Items cleared!");
            }
            public void onItemUnselected(KeyPairBoolData item) {
                xx[0]--;
            }
        });
    }
    String durasi(Date tglBaru, Date tglLama){
        long diff = tglLama.getTime() - tglBaru.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        String beda = String.valueOf(days);
        return beda;
    }

    Date konvert(String tanggal){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
             date = format.parse(tanggal);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }
    protected void btnAction(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value spinner
                //show dialog
                ProgressDialog dialog = ProgressDialog.show(TambahNPPDActivity.this, "",
                        "Loading. Please wait...", true);
                idTujuan = inputLokasi.getSelectedItem().toString().split("-")[0].trim();
                tglPergi = inputPergi.getText().toString();
                tglKembali = inputPulang.getText().toString();
                durasi = durasi(konvert(tglPergi),konvert(tglKembali))+" Hari";
                maksud = inputTujuan.getText().toString();
                ApiClient client = new ApiClient();
                String req = "idPegawai="+idPegawai+"&idTujuan="+idTujuan+"&tglPergi="+tglPergi+"&tglKembali="+tglKembali+"&durasi="+durasi+"&maksud="+maksud+"&idTransportasi="+idTransportasi;
                Log.d("request",req);
                client.getServies().input_nppd(idPegawai, idTujuan,idTransportasi,maksud,
                        tglPergi,tglKembali,durasi,"android").enqueue(new retrofit2.Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        Log.d("call", call.toString());
                        if (response.isSuccessful()) {
                           Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                           //clear input
                           inputLokasi.setSelection(0);
                           inputPergi.setText("");
                           inputPulang.setText("");
                           inputTujuan.setText("");
                           inputTransportasi.setSelection(0);
                           inputPegawai.setSelection(0);
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                Timber.d("Simpan");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTujuan.setText("");
                inputPergi.setText("");
                inputPulang.setText("");
                //clear inputPegawai
            }
        });

    }

    private void getBasic() {
        ApiClient client = new ApiClient();
        client.getServies().get_basic("android").enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    ResponseBasicModel responseModel = (ResponseBasicModel) response.body();
                    if (responseModel.getStatus().equals("sukses")) {
                        Toast.makeText(getApplicationContext(), responseModel.getPesan(), Toast.LENGTH_SHORT).show();
                        List<PegawaiListModel> pegawaList = responseModel.getPegawai();
                        List<TujuanListModel> tujuanList = responseModel.getTujuan();
                        List<TransportasiModel> transportasiList = responseModel.getTransportasi();
                        Log.d("pegawai ", pegawaList.get(0).getNama());
                        multipleSelectSetup(pegawaList,transportasiList);
                        singleSpinner(tujuanList);
                         //Log.d("tujuan",tujuanList.toString());
                        // Log.d("transportasi",transportasiList.toString());
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
                //log url dan error
                Log.d("Login", "onFUrlailure: " + call.request().url() + " " + t.getMessage());
                Log.d("Login", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Login Gagal 2", Toast.LENGTH_LONG).show();
            }
        });
    }
}