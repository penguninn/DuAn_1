/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.Size;
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
public class SizeRepo {
    private JDBCHelper dbHelper;
    public List<Size> selectAllSize(String sql, Object... params) {
        dbHelper =  new JDBCHelper();
        List<Size> listSize = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sql, params);
            while(rs.next()){
                listSize.add(
                    new Size(
                            rs.getInt("id"),
                            rs.getString("maSize"),
                            rs.getString("tenSize")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSize;
    }
    public int add(Size size) {
        String sql = "INSERT INTO Size (MaSize, TenSize) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, size.getMaSize(),size.getTenSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(Size size) {
        String sql = "UPDATE Size SET MaSize = ?, TenSize = ? WHERE id = ?";
        try {
            return dbHelper.executeUpdate(sql, size.getMaSize(),size.getTenSize(), size.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<Size> getAll(){
        ArrayList<Size> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaSize, TenSize from Size";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                Size  size = new Size(id, MaSp, TenSP);
                list.add(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
