package com.example.happyfarm.Model;

public class SanPham {
    private int sanPhamID;
    private String tenSanPham;          //hạt giống cấp n
    private String anhMinhHoa;
    private String moTaSanPham;
    private int donGia;
    private boolean trangThaiSanPham;

    public SanPham(){}

    public SanPham(int sanPhamID, String tenSanPham, String anhMinhHoa, String moTaSanPham, int donGia, boolean trangThaiSanPham) {
        this.sanPhamID = sanPhamID;
        this.tenSanPham = tenSanPham;
        this.anhMinhHoa = anhMinhHoa;
        this.moTaSanPham = moTaSanPham;
        this.donGia = donGia;
        this.trangThaiSanPham = trangThaiSanPham;
    }

    public int getSanPhamID() {
        return sanPhamID;
    }

    public void setSanPhamID(int sanPhamID) {
        this.sanPhamID = sanPhamID;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public void setAnhMinhHoa(String anhMinhHoa) {
        this.anhMinhHoa = anhMinhHoa;
    }

    public String getMoTaSanPham() {
        return moTaSanPham;
    }

    public void setMoTaSanPham(String moTaSanPham) {
        this.moTaSanPham = moTaSanPham;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public boolean isTrangThaiSanPham() {
        return trangThaiSanPham;
    }

    public void setTrangThaiSanPham(boolean trangThaiSanPham) {
        this.trangThaiSanPham = trangThaiSanPham;
    }

}
