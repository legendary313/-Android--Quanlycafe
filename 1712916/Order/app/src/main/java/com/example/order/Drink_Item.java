package com.example.order;

public class Drink_Item {
    private String ten;
    private int gia;
    private int anh;
    private String moTa;
    public Drink_Item( ) {

    }


    public Drink_Item(String ten, int gia, int anh) {
        this.ten = ten;
        this.gia = gia;
        this.anh = anh;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Drink_Item(String ten, int gia, int anh, String moTa) {
        this.ten = ten;
        this.gia = gia;
        this.anh = anh;
        this.moTa = moTa;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getAnh() {
        return anh;
    }

    public void setAnh(int anh) {
        this.anh = anh;
    }
}
