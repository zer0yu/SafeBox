package com.mxn.soul.flowingdrawer.SQLcloud;

import com.mxn.soul.flowingdrawer.data.Const;
import com.mxn.soul.flowingdrawer.data.UserMessage;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zero on 2015/11/13.
 */
public class Sqltools_cloud {
    private static Connection getConn() {
        Connection conn = null;
        String url = Const.Aliurl;
        String username = Const.AliUsername;
        String password = Const.AliPassword;
        {
            try {
                Class.forName("com.mysql.jdbc.Driver"); //classLoader,加载对应驱动
                conn = (Connection) DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public int insert(UserMessage user) {
        Connection conn = getConn();
        int i = 0;
        String sql = "insert into usermessage (username,password) values(?,?)";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }


    public static String GetPass(String username) {
        Connection conn = getConn();
        String sql = "select password from usermessage where username ='" + username + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    return rs.getString(i) ;
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    /*public String Getsup(String username) {
        Connection conn = getConn();
        String sql = "select * from usermessage where username ='" + username + "'";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(!rs.next() && username != rs.getString(username)){
            }
            String user =  rs.getString(username);
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
