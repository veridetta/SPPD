package com.inc.vr.corp.apps.sppd.model;

import java.util.List;

public class ResponseRiwayatModel {
    private String status;
    private String pesan;
    private List<RiwayatModel> data;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getPesan() {
        return pesan;
    }

    public List<RiwayatModel> getData() {
        return data;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setData(List<RiwayatModel> data) {
        this.data = data;
    }
}
