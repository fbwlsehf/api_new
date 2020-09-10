package com.example.api;

public class Check_nut {
    private Boolean isdata =true;
    private Integer data = 0;

    public Check_nut(Boolean isdata, Integer data) {
        this.isdata = isdata;
        this.data = data;
    }

    public Check_nut(Integer data) {
        this.data = data;
    }

    public Boolean getIsdata() {
        return isdata;
    }

    public void setIsdata(Boolean isdata) {
        this.isdata = isdata;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
