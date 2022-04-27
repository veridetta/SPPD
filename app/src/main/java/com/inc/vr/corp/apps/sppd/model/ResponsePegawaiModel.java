package com.inc.vr.corp.apps.sppd.model;

import java.util.List;

public class ResponsePegawaiModel {
    private String status;
    private String pesan;
    private PegawaiModel data;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getPesan() {
        return pesan;
    }

    public PegawaiModel getData() {
        return data;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setData(PegawaiModel dataObject) {
        this.data = data;
    }
}
