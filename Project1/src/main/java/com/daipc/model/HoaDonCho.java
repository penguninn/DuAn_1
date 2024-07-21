/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author DaiPc
 */
public class HoaDonCho {
    private String maHD;
    private String tenKhachHang;
    private String nguoiTao;
    private String ngayTao;
    private double tong;

    public HoaDonCho() {
    }

    public HoaDonCho(String maHD, String tenKhachHang, String nguoiTao, String ngayTao, double tong) {
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.nguoiTao = nguoiTao;
        this.ngayTao = ngayTao;
        this.tong = tong;
    }
    
    public Object[] getObjects() {
        return new Object[] {maHD, tenKhachHang, nguoiTao, ngayTao, tong};
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public double getTong() {
        return tong;
    }

    public void setTong(double tong) {
        this.tong = tong;
    }
    
    
}
