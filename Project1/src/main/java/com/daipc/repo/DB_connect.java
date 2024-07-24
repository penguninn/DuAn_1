/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author DaiPc
 */
public class DB_connect {

    private Connection connection = null;
    private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String URL = "jdbc:sqlserver://DAIPC\\SQLEXPRESS:1433;databaseName=DuAn1_Final;encrypt=true;trustServerCertificate=true;";
    private final String USER = "sa";
    private final String PASSWORD = "Dai1082005@";

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        try {
            DatabaseMetaData dbmt = new DB_connect().getConnection().getMetaData();
            System.out.println(dbmt.getDriverName());
            System.out.println(dbmt.getDatabaseProductName());
            System.out.println(dbmt.getDatabaseProductVersion());
        } catch (Exception e) {
        }
    }
}
