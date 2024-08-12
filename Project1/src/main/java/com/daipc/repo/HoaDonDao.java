/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.HoaDonModel;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.*;

/**
 *
 * @author admin
 */
public class HoaDonDao {

    JDBCHelper dbHelper;
    private Connection conn = DatabaseHelper.getConnection();

    public List<HoaDonModel> getListHD() {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
        SELECT 
            hd.ID AS id, 
            hd.MaHD AS MaHD,
            kh.hoTen AS khachHangHoTen, 
            kh.soDT AS khachHangSoDT, 
            hd.tongGiaTriHoaDon AS tongGiaTri, 
            hd.TrangThai AS TrangThai,
            hd.ngayTao AS ngayTao, 
            nv.hoTen AS nhanVienHoTen
        FROM 
            HoaDon hd
        INNER JOIN 
            HoaDonCT hdct ON hdct.IDHoaDon = hd.ID
        RIGHT JOIN 
            NhanVien nv ON nv.ID = hd.IDNhanVien
        LEFT JOIN 
            KhachHang kh ON kh.ID = hd.IDKhachHang
        WHERE 
            hd.TrangThai = 1
        GROUP BY 
            hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;

        try (PreparedStatement stm = conn.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String maHD = rs.getString("MaHD");
                String tenKH = rs.getString("khachHangHoTen");
                String sdtKH = rs.getString("khachHangSoDT");
                BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                String trangThai = rs.getString("TrangThai");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                String tenNV = rs.getString("nhanVienHoTen");

                HoaDonModel hoaDonModel = new HoaDonModel();
                hoaDonModel.setId(id);
                hoaDonModel.setMaHD(maHD); // Assuming MaHD is an int field; adjust accordingly if it's a String
                hoaDonModel.setTenKH(tenKH);
                hoaDonModel.setSdt(sdtKH);
                hoaDonModel.setDonGia(tongGiaTri);
                hoaDonModel.setTrangThai(trangThai);
                hoaDonModel.setNgayTao(ngayTao);
                hoaDonModel.setTenNguoiTao(tenNV);

                list.add(hoaDonModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> selectBySQL(String sql, Object... args) {
        List<HoaDonModel> listHD = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = DatabaseHelper.query(sql, args); // Lấy kết quả truy vấn
            ResultSetMetaData rsmd = rs.getMetaData(); // Lấy thông tin về các cột trong ResultSet
            int columnCount = rsmd.getColumnCount();
            Map<Integer, HoaDonModel> hdMap = new HashMap<>(); // Map để lưu trữ và loại bỏ trùng lặp

            while (rs.next()) {
                HoaDonModel hd = new HoaDonModel();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    switch (columnName) { // Gán giá trị cho các thuộc tính của HoaDon dựa trên tên cột
                        case "MaHD" ->
                            hd.setMaHD(rs.getString(i));
                        case "hoTen" ->
                            hd.setTenKH(rs.getString(i));
                        case "soDT" ->
                            hd.setSdt(rs.getString(i));
                        case "tongGiaTriHoaDon" ->
                            hd.setDonGia(rs.getBigDecimal(i));
                        case "tenTTHD" ->
                            hd.setTrangThai(rs.getString(i));
                        case "ngayTao" ->
                            hd.setNgayTao(rs.getDate(i));
                        case "tenNV" ->
                            hd.setTenNguoiTao(rs.getString(i));
                    }
                }
                hdMap.put(hd.getId(), hd); // Lưu trữ vào map, tự động loại bỏ trùng lặp dựa trên maHD
            }

            listHD.addAll(hdMap.values()); // Thêm các giá trị không trùng lặp vào danh sách

        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lỗi truy vấn danh sách hóa đơn");
        } finally {
            try {
                if (rs != null) {
                    rs.close(); // Đóng ResultSet thủ công
                }
            } catch (SQLException ex) {
                Logger.getLogger(HoaDonDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listHD; // Trả về danh sách hóa đơn không trùng lặp
    }

    public List<HoaDonModel> getSeachListHD(String HoaDon) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
                 
            SELECT 
                hd.ID AS id, 
                hd.MaHD AS MaHD,
                kh.hoTen AS khachHangHoTen, 
                kh.soDT AS khachHangSoDT, 
                hd.tongGiaTriHoaDon AS tongGiaTri, 
                hd.TrangThai AS TrangThai,
                hd.ngayTao AS ngayTao, 
                nv.hoTen AS nhanVienHoTen
            FROM 
                HoaDon hd
            INNER JOIN 
                HoaDonCT hdct ON hdct.IDHoaDon = hd.ID
            RIGHT JOIN 
                NhanVien nv ON nv.ID = hd.IDNhanVien
            LEFT JOIN 
                KhachHang kh ON kh.ID = hd.IDKhachHang
            WHERE 
                hd.TrangThai = 1 AND hd.MaHD LIKE ? 
            GROUP BY 
                hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
        """;

        try (
                Connection conn = DatabaseHelper.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, "%" + HoaDon + "%"); // Correctly set the MaHD parameter

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String maHD = rs.getString("MaHD");
                    String tenKH = rs.getString("khachHangHoTen");
                    String sdtKH = rs.getString("khachHangSoDT");
                    BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                    String trangThai = rs.getString("TrangThai");
                    Timestamp ngayTao = rs.getTimestamp("ngayTao");
                    String tenNV = rs.getString("nhanVienHoTen");

                    HoaDonModel hoaDonModel = new HoaDonModel();
                    hoaDonModel.setId(id);
                    hoaDonModel.setMaHD(maHD); // Assuming MaHD is a String field
                    hoaDonModel.setTenKH(tenKH);
                    hoaDonModel.setSdt(sdtKH);
                    hoaDonModel.setDonGia(tongGiaTri);
                    hoaDonModel.setTrangThai(trangThai);
                    hoaDonModel.setNgayTao(ngayTao);
                    hoaDonModel.setTenNguoiTao(tenNV);

                    list.add(hoaDonModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> getSeachList_SDT_HD(String sdt) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
        SELECT 
            hd.ID AS id, 
            hd.MaHD AS MaHD,
            kh.hoTen AS khachHangHoTen, 
            kh.soDT AS khachHangSoDT, 
            hd.tongGiaTriHoaDon AS tongGiaTri, 
            hd.TrangThai AS TrangThai,
            hd.ngayTao AS ngayTao, 
            nv.hoTen AS nhanVienHoTen
        FROM 
            HoaDon hd
        INNER JOIN 
            NhanVien nv ON nv.ID = hd.IDNhanVien
        LEFT JOIN 
            KhachHang kh ON kh.ID = hd.IDKhachHang
        WHERE 
            hd.TrangThai = 1 AND kh.SoDT LIKE ?
        GROUP BY 
            hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;

        try (
                Connection conn = DatabaseHelper.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            // Thiết lập tham số tìm kiếm số điện thoại
            stm.setString(1, "%" + sdt + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String maHD = rs.getString("MaHD");
                    String tenKH = rs.getString("khachHangHoTen");
                    String sdtKH = rs.getString("khachHangSoDT");
                    BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                    String trangThai = rs.getString("TrangThai");
                    Timestamp ngayTao = rs.getTimestamp("ngayTao");
                    String tenNV = rs.getString("nhanVienHoTen");

                    HoaDonModel hoaDonModel = new HoaDonModel();
                    hoaDonModel.setId(id);
                    hoaDonModel.setMaHD(maHD);
                    hoaDonModel.setTenKH(tenKH);
                    hoaDonModel.setSdt(sdtKH);
                    hoaDonModel.setDonGia(tongGiaTri);
                    hoaDonModel.setTrangThai(trangThai);
                    hoaDonModel.setNgayTao(ngayTao);
                    hoaDonModel.setTenNguoiTao(tenNV);

                    list.add(hoaDonModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDonModel> getSeachList_TenKH_HD(String Ho_Ten) {
        List<HoaDonModel> list = new ArrayList<>();
        String sql = """
        SELECT 
                    hd.ID AS id, 
                    hd.MaHD AS MaHD,
                    kh.hoTen AS khachHangHoTen, 
                    kh.soDT AS khachHangSoDT, 
                    hd.tongGiaTriHoaDon AS tongGiaTri, 
                    hd.TrangThai AS TrangThai,
                    hd.ngayTao AS ngayTao, 
                    nv.hoTen AS nhanVienHoTen
                FROM 
                    HoaDon hd
                INNER JOIN 
                    NhanVien nv ON nv.ID = hd.IDNhanVien
                LEFT JOIN 
                    KhachHang kh ON kh.ID = hd.IDKhachHang
                WHERE 
                    hd.TrangThai = 1 AND kh.HoTen LIKE ?
                GROUP BY 
                    hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;

        try (
                Connection conn = DatabaseHelper.getConnection(); PreparedStatement stm = conn.prepareStatement(sql)) {

            // Thiết lập tham số tìm kiếm số điện thoại
            stm.setString(1, "%" + Ho_Ten + "%");

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String maHD = rs.getString("MaHD");
                    String tenKH = rs.getString("khachHangHoTen");
                    String sdtKH = rs.getString("khachHangSoDT");
                    BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                    String trangThai = rs.getString("TrangThai");
                    Timestamp ngayTao = rs.getTimestamp("ngayTao");
                    String tenNV = rs.getString("nhanVienHoTen");

                    HoaDonModel hoaDonModel = new HoaDonModel();
                    hoaDonModel.setId(id);
                    hoaDonModel.setMaHD(maHD);
                    hoaDonModel.setTenKH(tenKH);
                    hoaDonModel.setSdt(sdtKH);
                    hoaDonModel.setDonGia(tongGiaTri);
                    hoaDonModel.setTrangThai(trangThai);
                    hoaDonModel.setNgayTao(ngayTao);
                    hoaDonModel.setTenNguoiTao(tenNV);

                    list.add(hoaDonModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HoaDonModel> getHDforNV(String maNV, String dateToday, String dateTarget) {
        dbHelper = new JDBCHelper();
        ArrayList<HoaDonModel> list = new ArrayList<>();
        String sql = """
        SELECT
                hd.MaHD AS MaHD,
                    hd.tongGiaTriHoaDon AS tongGiaTri,
                    hd.TrangThai AS TrangThai,
                    hd.ngayTao AS ngayTao,
                    hd.ThanhToan AS ThanhToan
                FROM
                    HoaDon hd
                    INNER JOIN
                    HoaDonCT hdct ON hdct.IDHoaDon = hd.ID
                    RIGHT JOIN
                    NhanVien nv ON nv.ID = hd.IDNhanVien
                    LEFT JOIN
                    KhachHang kh ON kh.ID = hd.IDKhachHang
                WHERE 
                        hd.TrangThai = 1 AND nv.MaNhanVien = ? AND hd.NgayTao BETWEEN ? AND ?
                GROUP BY 
                        hd.ID, hd.MaHD, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, hd.ThanhToan   
        """;

        try {
            ResultSet rs = dbHelper.executeQuery(sql, maNV, dateTarget, dateToday);
            while (rs.next()) {
                String maHD = rs.getString("MaHD");
                BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                String trangThai = rs.getString("TrangThai");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                BigDecimal thanhToan = rs.getBigDecimal("ThanhToan");

                HoaDonModel hoaDonModel = new HoaDonModel();
                hoaDonModel.setMaHD(maHD); // Assuming MaHD is an int field; adjust accordingly if it's a String
                hoaDonModel.setDonGia(tongGiaTri);
                hoaDonModel.setTrangThai(trangThai);
                hoaDonModel.setNgayTao(ngayTao);
                hoaDonModel.setThanhToan(thanhToan);
                list.add(hoaDonModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    Truong
    public ArrayList<HoaDonModel> getDataByMaHD(String maHD) {
        dbHelper = new JDBCHelper();
        ArrayList<HoaDonModel> data = new ArrayList<>();
        String sql = """
                 SELECT
                         hd.MaHD AS MaHD,
                         nv.HoTen AS nhanVienHoTen,
                         hd.NgayTao AS ngayTao,
                         kh.HoTen AS khachHangHoTen,
                         kh.SoDT AS khachHangSoDT,
                         hd.TrangThai AS TrangThai,
                         hd.ThanhToan AS ThanhToan,
                         hd.TongGiaTriHoaDon AS tongGiaTri,
                         pt.TenPhuongThucTT AS phuongThucTT
                     FROM
                         HoaDon hd
                         LEFT JOIN
                         NhanVien nv ON nv.ID = hd.IDNhanVien
                         LEFT JOIN
                         KhachHang kh ON kh.ID = hd.IDKhachHang
                         LEFT JOIN
                         PhuongThucThanhToan pt ON pt.ID = hd.IDPhuongThucTT
                     WHERE 
                         hd.TrangThai = 1 AND hd.MaHD = ?;
                 """;
        try {
            ResultSet rs = dbHelper.executeQuery(sql, maHD);
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và tạo đối tượng HoaDonModel
                HoaDonModel hoaDon = new HoaDonModel(
                        rs.getString("MaHD"),
                        rs.getString("khachHangHoTen"),
                        rs.getString("khachHangSoDT"),
                        rs.getBigDecimal("tongGiaTri"),
                        rs.getString("TrangThai"),
                        rs.getDate("ngayTao"), // nếu 'ngayTao' là kiểu Datex
                        rs.getString("nhanVienHoTen"),
                        rs.getBigDecimal("ThanhToan"),
                        rs.getString("phuongThucTT")
                );
                // Thêm đối tượng vào danh sách
                data.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<ChiTietSP> getSPCTByMaHD(String maHD) {
        dbHelper = new JDBCHelper();
        ArrayList<ChiTietSP> dataHD = new ArrayList<>();
        String sql = """
                 SELECT
                             sp.TenSP AS tenSanPham,
                             sz.TenSize AS kichCo,
                             ms.TenMauSac AS tenMauSac,
                             hdct.SoLuong AS soLuong,
                             hdct.DonGia AS giaBan
                         FROM
                             SanPhamChiTiet spct
                             LEFT JOIN
                             SanPham sp ON spct.IdSanPham = sp.ID
                             LEFT JOIN
                             Size sz ON spct.IdSize = sz.ID
                             LEFT JOIN
                             MauSac ms ON spct.IdMauSac = ms.ID
                             LEFT JOIN
                             HoaDonCT hdct ON hdct.IDCTSP = spct.ID
                             LEFT JOIN
                             HoaDon hd ON hd.ID = hdct.IDHoaDon
                         WHERE 
                                         spct.HienThi = 'Hien' AND hd.MaHD = ?;
                 """;
        try {
            ResultSet rs = dbHelper.executeQuery(sql, maHD);
            while (rs.next()) {
                // Lấy dữ liệu từ ResultSet và tạo đối tượng HoaDonModel
                ChiTietSP ctsp = new ChiTietSP(
                        rs.getString("tenSanPham"),
                        rs.getBigDecimal("giaBan"),
                        rs.getInt("soLuong"),
                        rs.getString("tenMauSac"),
                        rs.getString("kichCo")
                );
                // Thêm đối tượng vào danh sách
                dataHD.add(ctsp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataHD;
    }

}
