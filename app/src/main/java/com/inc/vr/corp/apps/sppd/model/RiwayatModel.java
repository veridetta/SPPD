package com.inc.vr.corp.apps.sppd.model;

public class RiwayatModel {
    private String id;
    private String no_sppd;
    private String nama_pegawai;
    private String tugas;
    private String tujuan;
    private String id_nppt;

    // Getter Methods
    public String  getId() {
        return id;
    }
    public String  getNo_sppd() {
        return no_sppd;
    }
    public String  getNama_pegawai() {
        return nama_pegawai;
    }
    public String  getTugas() {
        return tugas;
    }
    public String  getTujuan() {
        return tujuan;
    }
    public String  getId_nppt() {
        return id_nppt;
    }



    // Setter Methods
    public void setId(String id) {
        this.id = id;
    }
    public void setNo_sppd(String no_sppd) {
        this.no_sppd = no_sppd;
    }
    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }
    public void setTugas(String tugas) {
        this.tugas = tugas;
    }
    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }
    public void setId_nppt(String id_nppt) {
        this.id_nppt = id_nppt;
    }

}

