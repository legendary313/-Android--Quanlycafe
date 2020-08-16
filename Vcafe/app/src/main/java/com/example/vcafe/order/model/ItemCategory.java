package com.example.vcafe.order.model;


import java.util.ArrayList;
import java.util.List;

public class ItemCategory {

    private String category;
    private List<Item> items;

    public ItemCategory(String category, List<Item> items) {
        this.category = category;
        this.items = items;
    }

    public ItemCategory() {
        items=new ArrayList<>();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public  void addItem(Item item){
        items.add(item);
    }
}
