package com.inc.vr.corp.apps.sppd.model;

public class PerjalananModel {
    private String id;
    private String no_spt;
    private String nama_pegawai;
    private String hasil;
    private String tanggal;

    // Getter Methods
    public String getId() {
        return id;
    }
    public String getNo_spt() {
        return no_spt;
    }
    public String getNama_pegawai() {
        return nama_pegawai;
    }
    public String getHasil() {
        return hasil;
    }
    public String getTanggal() {
        return tanggal;
    }


    // Setter Methods
    public void setId(String id) {
        this.id = id;
    }
    public void setNo_spt(String no_spt) {
        this.no_spt = no_spt;
    }
    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }
    public void setHasil(String hasil) {
        this.hasil = hasil;
    }
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}

