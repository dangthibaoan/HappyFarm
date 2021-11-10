package com.example.happyfarm.Model;

public class ODat {
    private int oDatID;         //11,12,13,14 - lúa; 21,22,23,24 - cà chua; 31,32,33,34 - cà rốt
    private boolean moKhoa;
    private boolean lamDat;
    private boolean gieoHat;
    private boolean tuoiNuoc;
    private boolean bonPhan;
    private int soNgayThuHoach;
    private int sanLuongThuHoach;

    public ODat() { }

    public ODat(int oDatID,
                boolean moKhoa,
                boolean lamDat,
                boolean gieoHat,
                boolean tuoiNuoc,
                boolean bonPhan,
                int soNgayThuHoach,
                int sanLuongThuHoach)
    {
        this.oDatID = oDatID;
        this.moKhoa = moKhoa;
        this.lamDat = lamDat;
        this.gieoHat = gieoHat;
        this.tuoiNuoc = tuoiNuoc;
        this.bonPhan = bonPhan;
        this.soNgayThuHoach = soNgayThuHoach;
        this.sanLuongThuHoach = sanLuongThuHoach;
    }

    public int getoDatID() {
        return oDatID;
    }

    public void setoDatID(int oDatID) {
        this.oDatID = oDatID;
    }

    public boolean isMoKhoa() {
        return moKhoa;
    }

    public void setMoKhoa(boolean moKhoa) {
        this.moKhoa = moKhoa;
    }

    public boolean isLamDat() {
        return lamDat;
    }

    public void setLamDat(boolean lamDat) {
        this.lamDat = lamDat;
    }

    public boolean isGieoHat() {
        return gieoHat;
    }

    public void setGieoHat(boolean gieoHat) {
        this.gieoHat = gieoHat;
    }

    public boolean isTuoiNuoc() {
        return tuoiNuoc;
    }

    public void setTuoiNuoc(boolean tuoiNuoc) {
        this.tuoiNuoc = tuoiNuoc;
    }

    public boolean isBonPhan() {
        return bonPhan;
    }

    public void setBonPhan(boolean bonPhan) {
        this.bonPhan = bonPhan;
    }

    public int getSoNgayThuHoach() {
        return soNgayThuHoach;
    }

    public void setSoNgayThuHoach(int soNgayThuHoach) {
        this.soNgayThuHoach = soNgayThuHoach;
    }

    public int getSanLuongThuHoach() {
        return sanLuongThuHoach;
    }

    public void setSanLuongThuHoach(int sanLuongThuHoach) {
        this.sanLuongThuHoach = sanLuongThuHoach;
    }

    public void Create(int oDatID){
        this.setoDatID(oDatID);
        this.setMoKhoa(false);
        this.setLamDat(false);
        this.setGieoHat(false);
        this.setTuoiNuoc(false);
        this.setBonPhan(false);
        this.setSoNgayThuHoach(0);
        this.setSanLuongThuHoach(0);
    }
}
