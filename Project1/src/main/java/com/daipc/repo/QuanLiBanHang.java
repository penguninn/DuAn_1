/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.enumm.TrangThaiCRUD;
import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDon;
import com.daipc.model.HoaDonCho;
import com.daipc.model.KhachHang;
import com.daipc.model.SanPham;
import com.daipc.model.Voucher;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DaiPc
 */
public class QuanLiBanHang {

    JDBCHelper dBHelper;

    public List<HoaDonCho> selectAllHDC(String sqlQuery, Object... params) {
        dBHelper = new JDBCHelper();
        List<HoaDonCho> listHD = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listHD.add(
                        new HoaDonCho(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getInt(6),
                                rs.getDouble(7)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> selectAllHD() {
        dBHelper = new JDBCHelper();
        List<HoaDon> listHD = new ArrayList<>();
        String sqlQuery = """
                            SELECT 
                                ID, 
                                MaHD, 
                                IDKhachHang, 
                                IDNhanVien, 
                                IDVoucher, 
                                TongGiaTriHoaDon, 
                                ThanhToan, 
                                NgayTao, 
                                TrangThai
                            FROM 
                                HoaDon;
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String maHD = rs.getString("MaHD");
                int idKhachHang = rs.getInt("IDKhachHang");
                int idNhanVien = rs.getInt("IDNhanVien");
                int idVoucher = rs.getInt("IDVoucher");
                double tongGiaTriHoaDon = rs.getDouble("TongGiaTriHoaDon");
                int thanhToan = rs.getInt("ThanhToan");
                String ngayTao = rs.getString("NgayTao");
                int trangThai = rs.getInt("TrangThai");

                listHD.add(new HoaDon(id, maHD, idKhachHang, idNhanVien, idVoucher,
                        tongGiaTriHoaDon, thanhToan, ngayTao, trangThai));
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<GioHang> selectAllGH(String sqlQuery, Object... params) {
        dBHelper = new JDBCHelper();
        List<GioHang> listGH = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listGH.add(
                        new GioHang(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getDouble(3),
                                rs.getInt(4),
                                rs.getDouble(5)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listGH;
    }

    public List<ChiTietSP> selectAllSPCT() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
                            SELECT 
                                spct.MaSPCT,
                                spct.TenSPCT,
                                spct.GiaBan,
                                ms.TenMauSac,
                                s.TenSize,
                                cl.TenChatLieu,
                                dd.TenDoDay,
                                ncc.TenNhaCungCap,
                                spct.SoLuong
                            FROM 
                                SanPhamChiTiet spct
                            LEFT JOIN 
                                SanPham sp ON spct.IdSanPham = sp.ID
                            LEFT JOIN 
                                MauSac ms ON spct.IdMauSac = ms.ID
                            LEFT JOIN 
                                Size s ON spct.IdSize = s.ID
                            LEFT JOIN 
                                ChatLieu cl ON spct.IdChatLieu = cl.ID
                            LEFT JOIN 
                                DoDay dd ON spct.IdDoDay = dd.ID
                            LEFT JOIN 
                                NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID;
                         """;
        List<ChiTietSP> listSPCT = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listSPCT.add(
                        new ChiTietSP(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getBigDecimal(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getInt(9)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSPCT;
    }

    public List<KhachHang> getAllKH() {
        dBHelper = new JDBCHelper();
        List<KhachHang> listKH = new ArrayList<>();
        String sqlQuery = """
                            select id, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NguoiTao, NgayTao from KhachHang
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listKH.add(new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (Exception e) {
        }
        return listKH;
    }

    public List<ChiTietSP> getSPCT() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
                            SELECT 
                                spct.MaSPCT,
                                spct.TenSPCT,
                                spct.GiaBan,
                                ms.TenMauSac,
                                s.TenSize,
                                cl.TenChatLieu,
                                dd.TenDoDay,
                                ncc.TenNhaCungCap,
                                spct.SoLuong
                            FROM 
                                SanPhamChiTiet spct
                            LEFT JOIN 
                                SanPham sp ON spct.IdSanPham = sp.ID
                            LEFT JOIN 
                                MauSac ms ON spct.IdMauSac = ms.ID
                            LEFT JOIN 
                                Size s ON spct.IdSize = s.ID
                            LEFT JOIN 
                                ChatLieu cl ON spct.IdChatLieu = cl.ID
                            LEFT JOIN 
                                DoDay dd ON spct.IdDoDay = dd.ID
                            LEFT JOIN 
                                NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID;
                         """;
        List<ChiTietSP> listSPCT = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listSPCT.add(
                        new ChiTietSP(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getBigDecimal(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getInt(9)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSPCT;
    }

    public List<Voucher> getAllVoucher() {
        dBHelper = new JDBCHelper();
        List<Voucher> listVoucher = new ArrayList<>();
        String sqlQuery = """
                            select * from Voucher
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listVoucher.add(new Voucher(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7)
                ));
            }
        } catch (Exception e) {
        }
        return listVoucher;
    }

    public TrangThaiCRUD update(String sqlQuery, Object... params) {
        dBHelper = new JDBCHelper();
        try {
            if (dBHelper.executeUpdate(sqlQuery, params) > 0) {
                return TrangThaiCRUD.ThanhCong;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            if ("23000".equals(e.getSQLState()) || e.getErrorCode() == 2627 || e.getErrorCode() == 2601 || "DaTonTai".equals(e.getMessage())) {
                return TrangThaiCRUD.DaTonTai;
            } else {
                e.printStackTrace();
            }
        }
        return TrangThaiCRUD.ThatBai;
    }
}
