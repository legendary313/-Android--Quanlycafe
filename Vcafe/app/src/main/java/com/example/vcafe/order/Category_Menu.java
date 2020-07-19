package com.example.vcafe.order;

public class Category_Menu {
    private String loai;
    private  java.util.List<Drink_Item> List;

    public Category_Menu(String loai, java.util.List<Drink_Item> list) {
        this.loai = loai;
        List = list;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public java.util.List<Drink_Item> getList() {
        return List;
    }

    public void setList(java.util.List<Drink_Item> list) {
        List = list;
    }
}
