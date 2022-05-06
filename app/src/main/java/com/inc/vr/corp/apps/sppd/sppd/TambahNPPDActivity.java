package com.inc.vr.corp.apps.sppd.sppd;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.inc.vr.corp.apps.sppd.R;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TambahNPPDActivity extends AppCompatActivity {

    EditText inputTujuan, inputPergi, inputPulang, inputLama;
    Spinner inputLokasi;
    MultiSpinnerSearch inputPegawai, inputTransportasi;
    Button btnSimpan, btnClear;

    //variable untuk input ke nppt
    String idPegawai, idTujuan, idTransportasi, durasi, tglPergi, tglKembali, status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_nppdactivity);
        navbar();
        setupView();
        multipleSelectSetup();
        singleSpinner();
        btnAction();
    }

    protected void navbar(){
        //add back button navbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    protected void setupView(){
        inputTujuan = findViewById(R.id.input_tujuan);
        inputPergi = findViewById(R.id.input_tanggal_pergi);
        inputPulang = findViewById(R.id.input_tanggal_pulng);
        inputLama = findViewById(R.id.input_durasi);
        inputLokasi = findViewById(R.id.input_lokasi);
        inputPegawai = findViewById(R.id.input_pegawai);
        inputTransportasi = findViewById(R.id.input_transportasi);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnClear = findViewById(R.id.btn_clear);

    }

    protected void singleSpinner(){
       //spinner adapter
        String[] lokasi = new String[]{"1-Bandung", "2-Jakarta", "3-Surabaya", "4-Semarang", "5-Yogyakarta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lokasi);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputLokasi.setAdapter(adapter);
    }

    protected void multipleSelectSetup(){
        inputPegawai.setSearchEnabled(true);
        inputPegawai.setSearchHint("Pilih beberapa pegawai");
        inputPegawai.setEmptyTitle("Data tidak ditemukan");
        inputPegawai.setShowSelectAllButton(true);
        inputPegawai.setClearText("Tutup & Bersihkan");
        //pegawai
        List<KeyPairBoolData> listPegawai = new ArrayList<>();
        listPegawai.add(new KeyPairBoolData("1 - Pegawai 1",  false));
        listPegawai.add(new KeyPairBoolData("2 - Pegawai 2",  false));
        listPegawai.add(new KeyPairBoolData("3 - Pegawai 3",  false));
        listPegawai.add(new KeyPairBoolData("4 - Pegawai 4",  false));
        listPegawai.add(new KeyPairBoolData("5 - Pegawai 5",  false));
        final Integer[] yy = {0};
        inputPegawai.setItems(listPegawai, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        yy[0]++;
                        if (yy[0] > 1){
                            idTransportasi = "-"+items.get(i).getName().split("-")[0].trim();
                        }else{
                            idTransportasi = items.get(i).getName().split("-")[0].trim();
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
        listTransportasi.add(new KeyPairBoolData("1 - Transportasi 1",  false));
        listTransportasi.add(new KeyPairBoolData("2 - Transportasi 2",  false));
        listTransportasi.add(new KeyPairBoolData("3 - Transportasi 3",  false));
        listTransportasi.add(new KeyPairBoolData("4 - Transportasi 4",  false));
        listTransportasi.add(new KeyPairBoolData("5 - Transportasi 5",  false));
        //set item
        final Integer[] xx = {0};
        inputTransportasi.setItems(listTransportasi, new MultiSpinnerListener() {
            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                       xx[0]++;
                       if (xx[0] > 1){
                           idTransportasi = "-"+items.get(i).getName().split("-")[0].trim();
                       }else{
                           idTransportasi = items.get(i).getName().split("-")[0].trim();
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

    protected void btnAction(){
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("Simpan");
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTujuan.setText("");
                inputPergi.setText("");
                inputPulang.setText("");
                inputLama.setText("0");
                //clear inputPegawai
            }
        });

    }
}