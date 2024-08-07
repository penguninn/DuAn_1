/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.ThongKe;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DaiPc
 */
public class QuanLiThongKe {

    JDBCHelper dBHelper;

    public ThongKe getCardThongKe() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """ 
                                SELECT 
                                        (SELECT COUNT(*) FROM HoaDon) AS TotalOrders,
                                        (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon) AS TotalRevenue,
                                        (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalCost,
                                        (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon) - (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalProfit
                            """;
        ThongKe thongKe = new ThongKe();

        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                thongKe.setDonHang(rs.getInt(1));
                thongKe.setDoanhThu(rs.getBigDecimal(2));
                thongKe.setChiPhi(rs.getBigDecimal(3));
                thongKe.setLoiNhuan(rs.getBigDecimal(4));
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thongKe;
    }

    public List<ChiTietSP> getThongKeSP() {
        dBHelper = new JDBCHelper();
        String sqlQuery =   """
                                SELECT 
                                        spct.id,
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
                                        NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
                                    WHERE spct.hienthi = 'hien'
                            """;
        List<ChiTietSP> listSPCT = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listSPCT.add(
                        new ChiTietSP(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getBigDecimal(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getInt(10)
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
