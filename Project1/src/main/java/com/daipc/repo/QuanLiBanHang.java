/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDon;
import com.daipc.model.HoaDonCho;
import com.daipc.model.SanPham;
import java.math.BigDecimal;
import java.sql.ResultSet;
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
                            rs.getInt("id"),
                            rs.getString("mahd"),
                            rs.getString("HoTen"),
                            rs.getString("nguoitao"),
                            rs.getString("NgayTao"),
                            rs.getInt("TrangThai"),
                            rs.getDouble("TongGiaTriHoaDon")
                    )
                );
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
}
