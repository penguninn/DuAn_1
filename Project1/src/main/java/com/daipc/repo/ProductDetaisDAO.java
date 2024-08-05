/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.SanPham;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ProductDetaisDAO {
    public List<ChiTietSP> selectAll_By_ID_MaHD(int maHD) {//Dùng cho truy vấn trong HĐ
        String sql = """
                    	SELECT 
                            spct.ID, 
                            sp.TenSP AS tenSanPham, 
                        	
                            ncc.TenNhaCungCap AS tenThuongHieu,
                            sz.TenSize AS kichCo,
                            ms.TenMauSac AS tenMauSac, 
                            hdct.SoLuong AS soLuong,  
                            hdct.DonGia AS giaBan,
                            cl.TenChatLieu AS tenChatLieu, 
                            dd.TenDoDay AS tenDoDay,  
                            spct.TrangThai,
                        	spct.MaSPCT
                        	
                        FROM 
                            SanPhamChiTiet spct
                        LEFT JOIN 
                            SanPham sp ON spct.IdSanPham = sp.ID
                        LEFT JOIN 
                            NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
                        LEFT JOIN 
                            ChatLieu cl ON spct.IdChatLieu = cl.ID
                        LEFT JOIN 
                            DoDay dd ON spct.IdDoDay = dd.ID
                        LEFT JOIN 
                            Size sz ON spct.IdSize = sz.ID
                        LEFT JOIN 
                            MauSac ms ON spct.IdMauSac = ms.ID
                        LEFT JOIN 
                            HoaDonCT hdct ON hdct.IDCTSP = spct.ID
                        LEFT JOIN 
                            HoaDon hd ON hd.ID = hdct.IDHoaDon
                        WHERE 
                            spct.HienThi = 'Hien' AND hd.ID = ?;
                    """;
        return selectBySQL(sql, maHD);
    }
    public List<ChiTietSP> selectBySQL(String sql, Object... args) {
    List<ChiTietSP> listProdetails = new ArrayList<>();
    try {
        ResultSet rs = DatabaseHelper.query(sql, args);
        while (rs.next()) {
            ChiTietSP prodetails = new ChiTietSP();
            prodetails.setId(rs.getInt("ID"));
            
            prodetails.setTenChatLieu(rs.getString("tenChatLieu"));
            prodetails.setTenDoDay(rs.getString("tenDoDay"));
            prodetails.setTenSize(rs.getString("kichCo"));
            prodetails.setTenMauSac(rs.getString("tenMauSac"));
            prodetails.setTenSP(rs.getString("tenSanPham"));
prodetails.setTenNhaCungCap(rs.getString("tenThuongHieu"));
            prodetails.setSoLuong(rs.getInt("soLuong"));
            prodetails.setGiaBan(rs.getBigDecimal("giaBan"));
            prodetails.setTrangThai(rs.getString("TrangThai"));
            
            prodetails.setMaCTSP(rs.getString("MaSPCT"));
            // Note: Ensure field name matches
            //prodetails.setNgayTao(rs.getDate("NgayTao")); // Ensure correct date format

            listProdetails.add(prodetails);
        }
        rs.getStatement().getConnection().close();
    } catch (SQLException ex) {
        Logger.getLogger(ProductDetaisDAO.class.getName()).log(Level.SEVERE, null, ex);
        ex.printStackTrace();
    }
    return listProdetails;
}

    
    public static void main(String[] args) {
        ProductDetaisDAO detaisDAO = new ProductDetaisDAO();
        System.out.println(detaisDAO.selectAll_By_ID_MaHD(1));
    }
}
