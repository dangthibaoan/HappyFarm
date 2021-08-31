package com.example.happyfarm.Model;

import java.util.Date;

public class Order {
    private String uid;
    private String order_id;
    private String orderNPC;
    private String orderNote;
    private int product_id;
    private int product_sl;
    private Date order_time;        //thời gian đơn hàng xuất hiện
    private int order_deadline;     //số ngày phải hoàn thành đơn hàng
    private int order_status;       //trạng thái đơn hàng: 0-chưa nhận, 1-đã nhận, 2-đã hủy, 3-đã xong, 4-bị fail

    public Order(){}

    public Order(String uid,
                 String orderID,
                 String orderNPC,
                 String orderNote,
                 int product_id,
                 int product_sl,
                 Date order_time,
                 int order_deadline,
                 int order_status)
    {
        this.uid = uid;
        this.order_id = orderID;
        this.orderNPC = orderNPC;
        this.orderNote = orderNote;
        this.product_id = product_id;
        this.product_sl = product_sl;
        this.order_time = order_time;
        this.order_deadline = order_deadline;
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

    public int getOrder_deadline() {
        return order_deadline;
    }

    public void setOrder_deadline(int order_deadline) {
        this.order_deadline = order_deadline;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }
}
