/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DaiPc
 */
public class QuanLiTaiKhoan {
    JDBCHelper dbHelper;
    
    public List<NhanVien> getAccount(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        List<NhanVien> listAccount = new ArrayList<>();
        try {
            ResultSet rs  = dbHelper.executeQuery(sqlQuery, params);
            
            while (rs.next()) {                
                listAccount.add(
                        new NhanVien(
                                rs.getString("chucvu"), 
                                rs.getString("taikhoan"),
                                rs.getString("matkhau")
                        )
                );
            }
        } catch (Exception e) {
            
        }
        return listAccount;
    }
}
