package com.inc.vr.corp.apps.sppd.model;

import java.util.List;

public class ResponseBasicModel {
    private String status;
    private String pesan;
    private List<PegawaiListModel> pegawai;
    private List<TujuanListModel> tujuan;
    private List<TransportasiModel> transportasi;



    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getPesan() {
        return pesan;
    }

    public List<PegawaiListModel> getPegawai() {
        return pegawai;
    }

    public List<TujuanListModel> getTujuan() {
        return tujuan;
    }

    public List<TransportasiModel> getTransportasi(){return transportasi;}

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setPegawai(List<PegawaiListModel> pegawai) {
        this.pegawai = pegawai;
    }
    public void setTujuan(List<TujuanListModel> tujuan) {
        this.tujuan = tujuan;
    }
    public void setTransportasi(List<TransportasiModel> transportasi){this.transportasi = transportasi;}
}
