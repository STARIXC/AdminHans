package com.starixc.adminhans.Model;

public class OrderStatus {
    private String orderNo;
    private String state;

    public OrderStatus() {
    }

    public OrderStatus(String orderNo, String state) {
        this.orderNo = orderNo;
        this.state = state;
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
}
