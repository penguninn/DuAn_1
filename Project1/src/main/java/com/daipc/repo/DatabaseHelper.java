/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.bouncycastle.cms.RecipientId.password;

/**
 *
 * @author admin
 */
public class DatabaseHelper {
      private static final String DB_SERVER = "localhost";
    private static final String DB_NAME = "DuAn1_Final";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "Dai1082005@";
    private static final String DB_URL ="jdbc:sqlserver://localhost;databaseName=DuAn1_Final;encrypt=true;trustServerCertificate=true;";

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
    
     public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement pstmt = DatabaseHelper.getStmt(sql, args);
//C1:    ResultSet rs = pstmt.executeQuery();
//        return rs;
        return pstmt.executeQuery(); //C2
    }
     public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {

        //Thực hiện kết nối với Database
        Connection cn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        //Tạo container chứa câu truy vấn
        PreparedStatement pstmt;
        //Check xem nó là Câu lệnh thường hay là câu gọi PROC
        //trim(): Để xóa khoảng trắng đầu và cuối chuỗi. 
        //StartsWith(String prefix , int offset) kiểm tra xem chuỗi dó có bắt đầu bằng 'prefix' và từ vị trí 'offset' không.
        if (sql.trim().startsWith("{")) {
            pstmt = cn.prepareCall(sql);
            /*              
Tạo ra đối tượng CallableStatement thông qua phương thức prepareCall của lớp Connection. 
Lớp CallableStatement là 1 lớp con kế thừa từ lớp cha PreparedStatement
--> nó có tất cả thuộc tính và phương thức của lớp PreparedStatement--1 lớp thực thi câu lệnh SQL thông thường.
--> Bn đag ép kiểu từ kiểu con sang kiểu cha --> Bn có thể dụng thuộc tính/phương thức của lớp PreparedStatement trên biến pstmt.
             */
        } else {
            pstmt = cn.prepareStatement(sql); //SQL
        }
        //Trường hợp có tham số. Thì nó sẽ thiết lập giá trị của tham số truyền vào 1 cách tương ứng.
        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        /*VD
                 String sql = "INSERT INTO students ( id , name , age ) Values ( ? , ? , ? ) ;
                 Object[] params = {101 , "Mèo yêu" , 26};
                 PreparedStatement pstmt = JDBCHelper.getStmt(sql,params ; 
         */
        return pstmt; //trả về đối tượng để chứa câu lệnh SQL hơp lý.
    }

    public static void main(String[] args) {
        Connection conn = DatabaseHelper.getConnection();
        System.out.println(conn);
    }
}
