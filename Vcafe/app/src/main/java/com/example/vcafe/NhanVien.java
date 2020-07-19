package com.example.vcafe;

import com.google.firebase.database.DatabaseReference;

public class NhanVien {
    private String LoaiNV;
    private int Luong;
    private String MaNV;
    private String TenNV;


    public NhanVien() {
    }

    public NhanVien(String LoaiNV, int Luong, String MaNV, String TenNV) {
        this.LoaiNV = LoaiNV;
        this.Luong = Luong;
        this.MaNV = MaNV;
        this.TenNV = TenNV;
    }

    public String getLoaiNV() {
        return LoaiNV;
    }

    public void setLoaiNV(String loaiNV) {
        LoaiNV = loaiNV;
    }

    public int getLuong() {
        return Luong;
    }

    public void setLuong(int luong) {
        Luong = luong;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String tenNV) {
        TenNV = tenNV;
    }
}
