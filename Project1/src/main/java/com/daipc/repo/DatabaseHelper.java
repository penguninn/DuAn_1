/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author admin
 */
public class DatabaseHelper {
      private static final String DB_SERVER = "localhost";
    private static final String DB_NAME = "DuAn1_Final";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "Dai1082005@";

    private static java.sql.Connection conn;

    private DatabaseHelper() {
    }

    public static java.sql.Connection getConnection() {
        if (conn != null) {
            return conn;
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String strConn = String.format("jdbc:sqlserver://%s;DatabaseName=%s;TrustServerCertificate=true;",
                    DB_SERVER, DB_NAME);
            conn = DriverManager.getConnection(strConn, DB_USERNAME, DB_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
            conn = null;
        }

        return conn;
    }

    public void closeConnection() {
    
        
    }
    
    public static void main(String[] args) {
        Connection conn = DatabaseHelper.getConnection();
        System.out.println(conn);
    }
}
