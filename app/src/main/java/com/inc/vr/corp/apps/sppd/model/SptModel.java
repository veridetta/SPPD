package com.inc.vr.corp.apps.sppd.model;

public class SptModel {
    private String id;
    private String no_spt;
    private String nama_pegawai;
    private String tugas;
    private String tgl_pergi;
    private String tgl_kembali;
    private String status;
    private String lama;

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
    public String getTugas() {
        return tugas;
    }
    public String getTgl_pergi() {
        return tgl_pergi;
    }
    public String getTgl_kembali() {
        return tgl_kembali;
    }
    public String getStatus() {
        return status;
    }
    public String getLama() {
        return lama;
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
    public void setTugas(String tugas) {
        this.tugas = tugas;
    }
    public void setTgl_pergi(String tgl_pergi) {
        this.tgl_pergi = tgl_pergi;
    }
    public void setTgl_kembali(String tgl_kembali) {
        this.tgl_kembali = tgl_kembali;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setLama(String lama) {
        this.lama = lama;
    }

}

