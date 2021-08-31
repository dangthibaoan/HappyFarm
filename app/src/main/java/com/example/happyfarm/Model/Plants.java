package com.example.happyfarm.Model;

import java.util.Date;

public class Plants {
    private String uid;         //uid của player
    private int plant_id;       //0 - lúa, 1 - cà chua, 2 - cà rốt
    private int plant_lvl;      //1 - cấp 1, 2 - cấp 2,... :cấp bậc giống cây
    private int plant_sl;       //sản lượng cây trồng cơ bản = cấp bậcx10, vd: lúa cấp 1 sản lượng 1x10=10
    private int plant_status;   //0 - chưa trồng, 1 - đã trồng chưa chín, 2 - chín
    private int land_status;    //0 - chưa làm đất, 1 -  đã làm đất, 2 - đã tưới, 3 - đã bón phân, 4 - đã tưới+bón phân
    private Date time_1;        //thời gian trồng

    private String plant_img;
    private int plant_total;

    public Plants() { }

    //giao diện trồng trọt
    public Plants(String uid, int plant_id, int plant_lvl, int plant_sl, int plant_status, int land_status, Date time_1){
        this.uid = uid;
        this.plant_id = plant_id;
        this.plant_lvl = plant_lvl;
        this.plant_sl = plant_sl;
        this.plant_status = plant_status;
        this.land_status = land_status;
        this.time_1 = time_1;
    }

    //giao diện kho hàng
    public Plants(String uid, int plant_id, String plant_img, int plant_total){
        this.uid = uid;
        this.plant_id = plant_id;
        this.plant_img = plant_img;
        this.plant_total = plant_total;
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

    public int getPlant_lvl() {
        return plant_lvl;
    }

    public void setPlant_lvl(int plant_lvl) {
        this.plant_lvl = plant_lvl;
    }

    public int getPlant_sl() {
        return plant_sl;
    }

    public void setPlant_sl(int plant_sl) {
        this.plant_sl = plant_sl;
    }

    public int getPlant_status() {
        return plant_status;
    }

    public void setPlant_status(int plant_status) {
        this.plant_status = plant_status;
    }

    public int getLand_status() {
        return land_status;
    }

    public void setLand_status(int land_status) {
        this.land_status = land_status;
    }

    public Date getTime_1() {
        return time_1;
    }

    public void setTime_1(Date time_1) {
        this.time_1 = time_1;
    }

    public String getPlant_img() {
        return plant_img;
    }

    public void setPlant_img(String plant_img) {
        this.plant_img = plant_img;
    }

    public int getPlant_total() {
        return plant_total;
    }

    public void setPlant_total(int plant_total) {
        this.plant_total = plant_total;
    }
}
