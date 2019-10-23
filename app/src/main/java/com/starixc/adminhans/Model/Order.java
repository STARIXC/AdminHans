package com.starixc.adminhans.Model;

import java.util.List;

public class Order {
    private String orderNo;
    private String state;
    private String phone;
    private String name;
    private String total;
    private List<Order> orders;
    private String orderTime;

    public Order() {
    }

    public Order(String orderNo, String state, String phone, String name, String total, List<Order> orders, String orderTime) {
        this.orderNo = orderNo;
        this.state = state;
        this.phone = phone;
        this.name = name;
        this.total = total;
        this.orders = orders;
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
