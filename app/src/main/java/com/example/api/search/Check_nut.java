package com.example.api.search;

public class Check_nut {
    private Boolean isdata =true;
    private float data = 0f;

    public Check_nut(Boolean isdata, float data) {
        this.isdata = isdata;
        this.data = data;
    }

    public Check_nut(float data) {
        this.data = data;
    }

    public Boolean getIsdata() {
        return isdata;
    }

    public void setIsdata(Boolean isdata) {
        this.isdata = isdata;
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }
}
