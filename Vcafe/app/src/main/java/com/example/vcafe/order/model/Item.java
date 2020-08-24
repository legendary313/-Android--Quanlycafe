package com.example.vcafe.order.model;

public class Item {
    protected String id;
    protected String name;
    protected String img_link;
    protected int price;
    protected int discountMoney;
    protected String category;
    protected String description;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Item(){}


    public Item(String name, String img_link, int price, String category, String description) {
        this.name = name;
        this.img_link = img_link;
        this.price = price;
        this.category = category;
        this.description = description;
    }

    public Item(String id, String name, String img_link, int price, int discountMoney, String category, String description) {
        this.id = id;
        this.name = name;
        this.img_link = img_link;
        this.price = price;
        this.discountMoney = discountMoney;
        this.category = category;
        this.description = description;
    }

    public Item(String id, String name, String img_link, int price, String category, String description) {
        this.id = id;
        this.name = name;
        this.img_link = img_link;
        this.price = price;
        this.category = category;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
