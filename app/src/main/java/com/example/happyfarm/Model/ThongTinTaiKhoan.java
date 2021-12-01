package com.example.happyfarm.Model;

public class ThongTinTaiKhoan {
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
        this.setTongTienNongTrai(150);
        this.setExpLevel(0);
        this.setGiaTriTheLuc(150);
        this.setTongSoLuongLua(0);
        this.setTongSoluongCachua(0);
        this.setTongSoLuongCaRot(0);
    }

}
