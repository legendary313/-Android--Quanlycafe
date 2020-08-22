package com.example.vcafe.order.model;

import java.util.ArrayList;
import java.util.List;

public class TableOrder {
    private String tableKey;
    public   List<OrderItem> orders=new ArrayList<>();

    public TableOrder() {
    }

    public TableOrder(String tableKey, List<OrderItem> orders) {
        this.tableKey = tableKey;
        this.orders = orders;
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }
}
