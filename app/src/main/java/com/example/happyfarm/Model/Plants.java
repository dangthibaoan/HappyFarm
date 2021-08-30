package com.example.happyfarm.Model;

import java.util.Date;

public class Plants {
    private String uid;         //uid của player
    private int plant_id;       //0 - lúa, 1 - cà chua, 2 - cà rốt
    private int plant_status;   //0 - chưa trồng, 1 - đã trồng chưa chín, 2 - chín
    private boolean watering;   //false - chưa tưới, true - đã tưới
    private boolean bon_phan;   //false - chưa bón, true - đã bón
    private Date time_1;        //thời gian trồng
    private Date time_2;        //thời gian thu hoạch

    public Plants() { }

    public Plants(String uid, int plant_id, int plant_status, boolean watering, boolean bon_phan, Date time_1, Date time_2){
        this.uid = uid;
        this.plant_id = plant_id;
        this.plant_status = plant_status;
        this.watering = watering;
        this.bon_phan = bon_phan;
        this.time_1 = time_1;
        this.time_2 = time_2;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public int getPlant_status() {
        return plant_status;
    }

    public void setPlant_status(int plant_status) {
        this.plant_status = plant_status;
    }

    public boolean isWatering() {
        return watering;
    }

    public void setWatering(boolean watering) {
        this.watering = watering;
    }

    public boolean isBon_phan() {
        return bon_phan;
    }

    public void setBon_phan(boolean bon_phan) {
        this.bon_phan = bon_phan;
    }

    public Date getTime_1() {
        return time_1;
    }

    public void setTime_1(Date time_1) {
        this.time_1 = time_1;
    }

    public Date getTime_2() {
        return time_2;
    }

    public void setTime_2(Date time_2) {
        this.time_2 = time_2;
    }
}
