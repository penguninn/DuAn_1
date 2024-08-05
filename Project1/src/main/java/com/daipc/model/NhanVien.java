/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.util.Date;

/**
 *
 * @author DaiPc
 */
public class NhanVien {
    private String maNhanVien; // MaNhanVien
    private String hoTen; // HoTen
    private String soDT; // SoDT
    private String cccd; // CCCD
    private Date ngaySinh; // NgaySinh
    private String chucVu; // ChucVu
    private boolean gioiTinh; // GioiTinh
    private String diaChi; // DiaChi
    private String taiKhoan; // TaiKhoan
    private String matKhau; // MatKhau
    private Date ngayTao; // NgayTao
    private Date ngaySua; // NgaySua
    private boolean trangThai; // TrangThai

    // Constructor
    public NhanVien(String maNhanVien, String hoTen, String soDT, String cccd, Date ngaySinh, String chucVu,
                    boolean gioiTinh, String diaChi, String taiKhoan, String matKhau, Date ngayTao, Date ngaySua,
                    boolean trangThai) {
        this.maNhanVien = maNhanVien;
        this.hoTen = hoTen;
        this.soDT = soDT;
        this.cccd = cccd;
        this.ngaySinh = ngaySinh;
        this.chucVu = chucVu;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.trangThai = trangThai;
    }

    public NhanVien(String chucVu, String taiKhoan, String matKhau) {
        this.chucVu = chucVu;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
    }
    
    

    // Getters and Setters
    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Date getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(Date ngaySua) {
        this.ngaySua = ngaySua;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
