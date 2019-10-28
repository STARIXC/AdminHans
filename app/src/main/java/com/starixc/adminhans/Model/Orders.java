package com.starixc.adminhans.Model;

import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Orders {
    @PropertyName("orders")
    private List<String> listOrders;

    public Orders() {
    }

    public Orders(List<String> listOrders) {
        this.listOrders = listOrders;
    }
    @PropertyName("orders")
    public List<String> getListOrders() {
        return listOrders;
    }
    @PropertyName("orders")
    public void setListOrders(List<String> listOrders) {
        this.listOrders = listOrders;
    }
}
