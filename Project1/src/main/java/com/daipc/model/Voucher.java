/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author daipc
 */
public class Voucher {
    private int id;
    private String maVoucher;
    private double giaTriVoucher;
    private String ngayBatDau;
    private String ngayKetThuc;
    private int soLuong;
    private String moTa;

    public Voucher() {
    }

    public Voucher(int id, String maVoucher, double giaTriVoucher, String ngayBatDau, String ngayKetThuc, int soLuong, String moTa) {
        this.id = id;
        this.maVoucher = maVoucher;
        this.giaTriVoucher = giaTriVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }
    
    public Object[] getObj() {
        return new Object[]{id, maVoucher, giaTriVoucher, ngayBatDau, ngayKetThuc, soLuong, moTa};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public double getGiaTriVoucher() {
        return giaTriVoucher;
    }

    public void setGiaTriVoucher(double giaTriVoucher) {
        this.giaTriVoucher = giaTriVoucher;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    
}
