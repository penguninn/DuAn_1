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
    private int id;
    private String maHD;
    private String tenKhachHang;
    private String nguoiTao;
    private String voucher;
    private String thanhToan;
    private String ngayTao;
    private int TrangThai;
    private double tong;
    private String SDT;
    
    public HoaDonCho() {
    }
    

    public HoaDonCho(int id, String maHD, String tenKhachHang, String nguoiTao, String voucher, String thanhToan, String ngayTao, int TrangThai, double tong, String SDT) {
        this.id = id;
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.nguoiTao = nguoiTao;
        this.voucher = voucher;
        this.thanhToan = thanhToan;
        this.ngayTao = ngayTao;
        this.TrangThai = TrangThai;
        this.tong = tong;
        this.SDT = SDT;
    }
    
    
    public Object[] getHDC() {
        return new Object[]{maHD, tenKhachHang, nguoiTao, ngayTao, tong};
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

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public double getTong() {
        return tong;
    }

    public void setTong(double tong) {
        this.tong = tong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    
}
