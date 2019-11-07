package com.starixc.adminhans.Model;

public class Transaction {
    private String orderNo;
    private String state;
    private String phone;
    private String name;
    private String total;
    private String orderTime;

    public Transaction() {
    }

    public Transaction(String orderNo, String state, String phone, String name, String total, String orderTime) {
        this.orderNo = orderNo;
        this.state = state;
        this.phone = phone;
        this.name = name;
        this.total = total;
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
