package com.daipc.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for managing database connections and queries.
 */
public class DatabaseHelper {

    private static final String DB_SERVER = "localhost";
    private static final String DB_NAME = "DuAn1_Final";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "hung";
    private static final String DB_URL = "jdbc:sqlserver://localhost;databaseName=DuAn1_Final;encrypt=true;trustServerCertificate=true;";

    private DatabaseHelper() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static ResultSet query(String sql, Object... args) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = getStmt(conn, sql, args)) {
            return pstmt.executeQuery();
        }
    }

    public static PreparedStatement getStmt(Connection conn, String sql, Object... args) throws SQLException {
        PreparedStatement pstmt;
        if (sql.trim().startsWith("{")) {
            pstmt = conn.prepareCall(sql);
        } else {
            pstmt = conn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
