package com.example.happyfarm.Model;

import static com.example.happyfarm.LoginScreen.CACHUA;
import static com.example.happyfarm.LoginScreen.CAROT;
import static com.example.happyfarm.LoginScreen.FARMCOIN;
import static com.example.happyfarm.LoginScreen.FARMEXP;
import static com.example.happyfarm.LoginScreen.LUA;
import static com.example.happyfarm.LoginScreen.STAMINA;
import static com.example.happyfarm.LoginScreen.USERID;

public class ThongTinTaiKhoan {
    private String uID;
    private int tongTienNongTrai;
    private int expLevel;
    private int giaTriTheLuc;
    private int tongSoLuongLua;
    private int tongSoluongCachua;
    private int tongSoLuongCaRot;

    public ThongTinTaiKhoan(){}

    public int getTongTienNongTrai() {
        return tongTienNongTrai;
    }

    public void setTongTienNongTrai(int tongTienNongTrai) {
        this.tongTienNongTrai = tongTienNongTrai;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getGiaTriTheLuc() {
        return giaTriTheLuc;
    }

    public void setGiaTriTheLuc(int giaTriTheLuc) {
        this.giaTriTheLuc = giaTriTheLuc;
    }

    public int getTongSoLuongLua() {
        return tongSoLuongLua;
    }

    public void setTongSoLuongLua(int tongSoLuongLua) {
        this.tongSoLuongLua = tongSoLuongLua;
    }

    public int getTongSoluongCachua() {
        return tongSoluongCachua;
    }

    public void setTongSoluongCachua(int tongSoluongCachua) {
        this.tongSoluongCachua = tongSoluongCachua;
    }

    public int getTongSoLuongCaRot() {
        return tongSoLuongCaRot;
    }

    public void setTongSoLuongCaRot(int tongSoLuongCaRot) {
        this.tongSoLuongCaRot = tongSoLuongCaRot;
    }

    public void Create(){
        this.setuID(USERID);
        this.setTongTienNongTrai(0);
        this.setExpLevel(0);
        this.setGiaTriTheLuc(150);
        this.setTongSoLuongLua(0);
        this.setTongSoluongCachua(0);
        this.setTongSoLuongCaRot(0);
    }

    public void GetData(){
        this.setuID(USERID);
        this.setTongTienNongTrai(FARMCOIN);
        this.setExpLevel(FARMEXP);
        this.setGiaTriTheLuc(STAMINA);
        this.setTongSoLuongLua(LUA);
        this.setTongSoluongCachua(CACHUA);
        this.setTongSoLuongCaRot(CAROT);
    }
}
