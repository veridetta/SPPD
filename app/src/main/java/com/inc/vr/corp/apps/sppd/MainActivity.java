package com.inc.vr.corp.apps.sppd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.inc.vr.corp.apps.sppd.laporan.LaporanPerjalananActivity;
import com.inc.vr.corp.apps.sppd.sppd.SptActivity;
import com.inc.vr.corp.apps.sppd.sppd.TambahNPPDActivity;

public class MainActivity extends AppCompatActivity {

    CardView navBuatNPPD, NavDataSPT, navDataPerjalanan, NavRiwayatPerjalanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        setupView();
        onClick();

    }
    protected void setupView(){
        navBuatNPPD = findViewById(R.id.nav_tambah_nppd);
        NavDataSPT = findViewById(R.id.nav_spt);
        navDataPerjalanan = findViewById(R.id.nav_data_perjalanan);
        NavRiwayatPerjalanan = findViewById(R.id.nav_riwayat_perjalanan);
    }
    protected void onClick() {
        //button onclick
        navBuatNPPD.setOnClickListener(v -> {
            Intent intent = new Intent(this, TambahNPPDActivity.class);
            startActivity(intent);
        });
        NavDataSPT.setOnClickListener(v -> {
            Intent intent = new Intent(this, SptActivity.class);
            startActivity(intent);
        });
        navDataPerjalanan.setOnClickListener(v -> {
            Intent intent = new Intent(this, LaporanPerjalananActivity.class);
            startActivity(intent);
        });
        NavRiwayatPerjalanan.setOnClickListener(v -> {
            Intent intent = new Intent(this, LaporanPerjalananActivity.class);
            startActivity(intent);
        });
    }
}