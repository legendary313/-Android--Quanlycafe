package com.example.vcafe;

import java.io.Serializable;

public class NuocUong implements Serializable {
    private String category;
    private String description;
    private int discountMoney;
    private String id;
    private String img_link;
    private String name;
    private int price;

    public NuocUong()
    {

    }

    public NuocUong(String category, String description, int discountMoney, String id, String img_link, String name, int price) {
        this.category = category;
        this.description = description;
        this.discountMoney = discountMoney;
        this.id = id;
        this.img_link = img_link;
        this.name = name;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(int discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
