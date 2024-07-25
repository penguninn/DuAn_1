/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author DaiPc
 */
public class GioHang {
    private int id;
    private String maSPCT;
    private String tenSPCT;
    private double donGia;
    private int soLuong;
    private boolean trangThai;
    private double thanhTien;

    public GioHang() {
    }
    
    

    public GioHang(int id, String maSPCT, String tenSPCT, double donGia, int soLuong, double thanhTien, boolean trangThai) {
        this.id = id;
        this.maSPCT = maSPCT;
        this.tenSPCT = tenSPCT;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    public GioHang(String maCTSP, String tenSPCT, double donGia, int soLuong) {
        this.maSPCT = maCTSP;
        this.tenSPCT = tenSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = this.soLuong * this.donGia;
    }

    public Object[] getGioHang() {
        return new Object[]{maSPCT, tenSPCT, donGia, soLuong, thanhTien};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getMaCTSP() {
        return maSPCT;
    }

    public void setMaCTSP(String MaCTSP) {
        this.maSPCT = MaCTSP;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSPCT() {
        return tenSPCT;
    }

    public void setTenSPCT(String tenSPCT) {
        this.tenSPCT = tenSPCT;
    }

    public double getTongTien() {
        return thanhTien;
    }

    public void setTongTien(double tongTien) {
        this.thanhTien = tongTien;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

}
