package com.example.vcafe;

import android.app.DownloadManager;


public class TaiKhoan {
    private String LoaiTK;
    private String MaNV;
    private String MaTK;
    private String MatKhau;
    private String TaiKhoan;

    public TaiKhoan() {

    }

    public TaiKhoan(String LoaiTK, String MaNV, String MatKhau, String TaiKhoan, String MaTK) {
        this.LoaiTK = LoaiTK;
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.TaiKhoan = TaiKhoan;
        this.MaTK = MaTK;
    }

    public String getLoaiTK() {
        return LoaiTK;
    }

    public void setLoaiTK(String loaiTK) {
        LoaiTK = loaiTK;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public String getMaTK() {
        return MaTK;
    }

    public void setMaTK(String maTK) {
        MaTK = maTK;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        TaiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }



}
