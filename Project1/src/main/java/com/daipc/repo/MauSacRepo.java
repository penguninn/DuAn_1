/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.enumm.TrangThai;
import com.daipc.model.MauSac;
import com.daipc.model.SanPham;
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
public class MauSacRepo{
    private JDBCHelper dbHelper;
    
    
    public List<MauSac> select(String sqlQuery, Object... params) {
        dbHelper =  new JDBCHelper();
        List<MauSac> listMS = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while(rs.next()){
                listMS.add(
                    new MauSac(
                            rs.getInt("id"),
                            rs.getString("maMauSac"),
                            rs.getString("tenMauSac")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMS;
    }

    public int addDoDay(MauSac mauSac) {
        String sql = "INSERT INTO MauSac (MaMauSac, TenMauSac) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, mauSac.getMaMauSac(), mauSac.getTenMauSac());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(MauSac mauSac) {
        String sql = "UPDATE MauSac SET maMauSac = ?, tenMauSac = ? WHERE id = ?";
        try {
            return dbHelper.executeUpdate(sql, mauSac.getMaMauSac(), mauSac.getTenMauSac(), mauSac.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    
    public ArrayList<MauSac> getAll(){
        ArrayList<MauSac> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaMauSac, TenMauSac from MauSac";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                MauSac  mauSac = new MauSac(id, MaSp, TenSP);
                list.add(mauSac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
