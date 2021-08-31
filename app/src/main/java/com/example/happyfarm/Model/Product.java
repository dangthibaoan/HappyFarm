package com.example.happyfarm.Model;

public class Product {
    private int prod_id;
    private int prod_status;        //0 - ẩn, 1 - hiện
    private String prod_img;
    private String prod_name;       //tên sản phẩm
    private String prod_detail;     //mô tả sản phẩm
    private int prod_price;         //giá hàng

    public Product(){}

    public Product(int prod_id, int prod_status, String prod_img, String prod_name, String prod_detail, int prod_price) {
        this.prod_id = prod_id;
        this.prod_status = prod_status;
        this.prod_img = prod_img;
        this.prod_name = prod_name;
        this.prod_detail = prod_detail;
        this.prod_price = prod_price;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public int getProd_status() {
        return prod_status;
    }

    public void setProd_status(int prod_status) {
        this.prod_status = prod_status;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public String getProd_detail() {
        return prod_detail;
    }

    public void setProd_detail(String prod_detail) {
        this.prod_detail = prod_detail;
    }

    public int getProd_price() {
        return prod_price;
    }

    public void setProd_price(int prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_img() {
        return prod_img;
    }

    public void setProd_img(String prod_img) {
        this.prod_img = prod_img;
    }
}
