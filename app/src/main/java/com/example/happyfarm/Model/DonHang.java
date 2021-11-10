package com.example.happyfarm.Model;

import java.util.concurrent.ThreadLocalRandom;

import static com.example.happyfarm.LoginScreen.FARMLEVEL;

public class DonHang {
    //private String uid;
    private int donHangID;
    private int nongSanID;
    private int soLuongMua;
    private int tienHang;

    public DonHang(){}

    public DonHang(int donHangID, int nongSanID, int soLuongMua, int tienHang) {
        this.donHangID = donHangID;
        this.nongSanID = nongSanID;
        this.soLuongMua = soLuongMua;
        this.tienHang = tienHang;
    }

    public int getDonHangID() {
        return donHangID;
    }

    public void setDonHangID(int donHangID) {
        this.donHangID = donHangID;
    }

    public int getNongSanID() {
        return nongSanID;
    }

    public void setNongSanID(int nongSanID) {
        this.nongSanID = nongSanID;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public int getTienHang() {
        return tienHang;
    }

    public void setTienHang(int tienHang) {
        this.tienHang = tienHang;
    }

    public void Create(){
        if (FARMLEVEL<4){
            this.setNongSanID(1);
            this.setSoLuongMua(ThreadLocalRandom.current().nextInt(1,11));
            this.setTienHang(this.getSoLuongMua()*5);
        } else if (FARMLEVEL < 11){
            this.setNongSanID(ThreadLocalRandom.current().nextInt(1,3));
            this.setSoLuongMua(ThreadLocalRandom.current().nextInt(10,50));
            this.setTienHang(this.getSoLuongMua()*6);
        } else if (FARMLEVEL < 21){
            this.setNongSanID(ThreadLocalRandom.current().nextInt(1,4));
            this.setSoLuongMua(ThreadLocalRandom.current().nextInt(40,100));
            this.setTienHang(this.getSoLuongMua()*7);
        } else {
            this.setNongSanID(ThreadLocalRandom.current().nextInt(1,4));
            this.setSoLuongMua(ThreadLocalRandom.current().nextInt(50,200));
            this.setTienHang(this.getSoLuongMua()*10);
        }
    }
}
