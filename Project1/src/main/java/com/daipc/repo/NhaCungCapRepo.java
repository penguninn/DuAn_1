/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.NhaCungCap;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class NhaCungCapRepo {
    private JDBCHelper dbHelper;
    public List<NhaCungCap> selectAll(String sqlQuery, Object... params) {
        dbHelper =  new JDBCHelper();
        List<NhaCungCap> listNhaCC = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while(rs.next()){
                listNhaCC.add(
                    new NhaCungCap(
                            rs.getInt("id"),
                            rs.getString("maNhaCungCap"),
                            rs.getString("tenNhaCungCap"),
                            rs.getString("diaChi"),
                            rs.getString("lienHe")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNhaCC;
    }
    
    public int add(NhaCungCap NhaCc) {
        String sql = "INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, DiaChi, LienHe) VALUES (?, ?, ?, ?)";
        try {
            return dbHelper.executeUpdate(sql, NhaCc.getMaNhaCungCap(),NhaCc.getTenNhaCungCap(),NhaCc.getDiaChi(), NhaCc.getLienHe());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(NhaCungCap NhaCc) {
        String sql = "UPDATE NhaCungCap SET MaNhaCungCap = ?, TenNhaCungCap = ?, DiaChi = ?, LienHe = ? WHERE id = ?";
        try {
            return dbHelper.executeUpdate(sql, NhaCc.getMaNhaCungCap(),NhaCc.getTenNhaCungCap(),NhaCc.getDiaChi(), NhaCc.getLienHe(), NhaCc.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<NhaCungCap> getAll(){
        ArrayList<NhaCungCap> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaNhaCungCap, TenNhaCungCap from NhaCungCap";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                NhaCungCap  nhaCungCap = new NhaCungCap(id, MaSp, TenSP, MaSp, TenSP);
                list.add(nhaCungCap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return list;
    }
}
