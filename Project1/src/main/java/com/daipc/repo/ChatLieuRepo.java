/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChatLieu;
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
public class ChatLieuRepo {
    private JDBCHelper dbHelper;
    public List<ChatLieu> selectAll(String sqlQuery, Object... params) {
        dbHelper =  new JDBCHelper();
        List<ChatLieu> listCL = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while(rs.next()){
                listCL.add(
                    new ChatLieu(
                            rs.getInt("id"),
                            rs.getString("maChatLieu"),
                            rs.getString("tenChatLieu")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCL;
    }
    
    public int add(ChatLieu chatLieu) {
        String sql = "INSERT INTO ChatLieu (MaChatLieu, TenChatLieu) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, chatLieu.getMaChatLieu(),chatLieu.getTenChatLieu());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(ChatLieu chatLieu) {
        String sql = "UPDATE ChatLieu SET MaChatLieu = ?, TenChatLieu = ? WHERE id = ?";
        try {
            return dbHelper.executeUpdate(sql, chatLieu.getMaChatLieu(), chatLieu.getTenChatLieu(), chatLieu.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<ChatLieu> getAll(){
        ArrayList<ChatLieu> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaChatLieu, TenChatLieu from ChatLieu";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                ChatLieu  chatLieu = new ChatLieu(id, MaSp, TenSP);
                list.add(chatLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
