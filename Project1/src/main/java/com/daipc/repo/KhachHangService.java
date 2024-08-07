/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class KhachHangService {
    
    private Connection conn = DatabaseHelper.getConnection();

    public ArrayList<KhachHang> getAll() {
        ArrayList<KhachHang> listkh = new ArrayList<>();
        String sql = "SELECT ID, MaKhachHang,HoTen, GioiTinh, SoDT, DiaChi    FROM KhachHang";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                 rs.getInt("ID"),  // Cần ID để có thể tạo đối tượng KhachHang đầy đủ
                rs.getString("MaKhachHang"),
                rs.getString("HoTen"),
                rs.getBoolean("GioiTinh"),
                rs.getString("SoDT"),
                rs.getString("DiaChi"),
                null, // Thêm các trường còn lại nếu cần
                rs.getString("NguoiTao")
            );
            listkh.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listkh;
    }
    
    
    public KhachHang get_TTKH_In_HD(int maHD) {
    // Câu lệnh SQL với tham số placeholder cho ID hóa đơn
    String sql = """
        SELECT kh.id, 
               kh.MaKhachHang, 
               kh.hoTen, 
               kh.gioiTinh, 
               kh.soDT, 
               kh.diaChi, 
               hd.tongGiaTriHoaDon
        FROM KhachHang kh
        INNER JOIN HoaDon hd ON kh.id = hd.IDKhachHang
        WHERE hd.ID = ?
        GROUP BY kh.id, 
                 kh.MaKhachHang, 
                 kh.hoTen, 
                 kh.gioiTinh, 
                 kh.soDT, 
                 kh.diaChi, 
                 hd.tongGiaTriHoaDon;
    """;
    KhachHang kh = null;
    try {
        // Tạo PreparedStatement từ câu lệnh SQL
        PreparedStatement stm = conn.prepareStatement(sql);
        
        // Gán tham số vào câu lệnh SQL
        stm.setInt(1, maHD);
        
        // Thực thi câu lệnh và lấy kết quả
        ResultSet rs = stm.executeQuery();
        
        // Xử lý kết quả
        if (rs.next()) {
            kh = new KhachHang();
            kh.setId(rs.getInt("id"));
            kh.setMaKhachHang(rs.getString("MaKhachHang"));
            kh.setHoTen(rs.getString("hoTen"));
            kh.setGioiTinh(rs.getBoolean("gioiTinh"));
            kh.setSoDT(rs.getString("soDT"));
            kh.setDiaChi(rs.getString("diaChi"));
            kh.setTongGTHD(rs.getInt("tongGiaTriHoaDon"));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // In lỗi ra console để dễ dàng gỡ lỗi
    }
    return kh;
}

}
