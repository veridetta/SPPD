package com.inc.vr.corp.apps.sppd.model;

import java.util.List;

public class ResponsePerjalananModel {
    private String status;
    private String pesan;
    private List<PerjalananModel> data;


    // Getter Methods

    public String getStatus() {
        return status;
    }

    public String getPesan() {
        return pesan;
    }

    public List<PerjalananModel> getData() {
        return data;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public void setData(List<PerjalananModel> data) {
        this.data = data;
    }
}
