package com.mxn.soul.flowingdrawer.SQLcloud;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import com.mxn.soul.flowingdrawer.data.SecretKey;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Created by ZEROYU on 5/24/2016.
 */
public class Sqltools_key {
    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://nwpu9699.mysql.rds.aliyuncs.com:3306/storedb";
        String username = "weznwpu";
        String password = "123456weznwpu906";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int insert(SecretKey user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into secretkey (username,filename,filekey1,filekey2,filekey3,filekey4,filekey5) values(?,?,?,?,?,?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getFilename());
            pstmt.setString(3, user.getFilekey1());
            pstmt.setString(4, user.getFilekey2());
            pstmt.setString(5, user.getFilekey3());
            pstmt.setString(6, user.getFilekey4());
            pstmt.setString(7, user.getFilekey5());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static int update(SecretKey user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "update secretkey set username='" + user.getUsername() + "' where filename='" + user.getFilename() + "' where filekey1='" + user.getFilekey1() +"' where filekey2='" + user.getFilekey2() +"' where filekey3='" +user.getFilekey3() +"'where filekey4='" +user.getFilekey4() +"'where filekey5='" +user.getFilekey5() +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static String getKey(String sql) {
        Connection conn = getConn();
        //String sql = "select filekey3 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    return rs.getString(i);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int delete(String filename) {
        Connection conn = getConn();
        int i = 0;
        String sql = "delete from secretkey where filename='" + filename + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            System.out.println("resutl: " + i);
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
}
