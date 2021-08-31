package com.example.happyfarm.Model;

import java.util.Date;

public class Order {
    private String uid;
    private String order_id;
    private String orderNPC;
    private String orderNote;
    private int product_id;         //id sản phẩm, vd: lúa id=1
    private String product_img;     //ảnh sản phẩm
    private int product_sl;         //số lượng sản phẩm
    private int order_price;        //giá trị đơn hàng
    private Date order_time;        //thời gian đơn hàng xuất hiện
    private int order_status;       //trạng thái đơn hàng: 0-chưa xong, 1- đã xong

    public Order(){}

    public Order(String uid,
                 String orderID,
                 String orderNPC,
                 String orderNote,
                 int product_id,
                 String product_img,
                 int product_sl,
                 int order_price,
                 Date order_time,
                 int order_status)
    {
        this.uid = uid;
        this.order_id = orderID;
        this.orderNPC = orderNPC;
        this.orderNote = orderNote;
        this.product_id = product_id;
        this.product_img = product_img;
        this.product_sl = product_sl;
        this.order_price = order_price;
        this.order_time = order_time;
        this.order_status = order_status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderID() {
        return order_id;
    }

    public void setOrderID(String orderID) {
        this.order_id = orderID;
    }

    public String getOrderNPC() {
        return orderNPC;
    }

    public void setOrderNPC(String orderNPC) {
        this.orderNPC = orderNPC;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int plant_id) {
        this.product_id = plant_id;
    }

    public int getProduct_sl() {
        return product_sl;
    }

    public void setProduct_sl(int plant_sl) {
        this.product_sl = plant_sl;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_pricee(int product_price) {
        this.order_price = product_price;
    }
    
}
