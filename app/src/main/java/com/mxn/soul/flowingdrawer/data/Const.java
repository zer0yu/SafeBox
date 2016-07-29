package com.mxn.soul.flowingdrawer.data;


import com.mxn.soul.flowingdrawer.main.loginActivity;

/**
 * Created by Sunny on 2015/12/20.
 */
public class Const {
    private static String username = loginActivity.userNameValue;
    private static String password = loginActivity.passwordValue;
    public static final int SQL_Version = 1;
    public static final String Database_name = "storedb";
    public static final String Tablename_usermessage = "usermessage";
    //public static final String CreateTable_usermessage = "Create Table if not exists usermessage (id Varchar,username Varchar,password Varchar," +
    //       " key Varchar);";
    public static int maxresult = 1;
    //阿里云数据库的URL
    public static String Aliurl = "jdbc:mysql://nwpu9699.mysql.rds.aliyuncs.com:3306/storedb";
    public static String AliUsername = "weznwpu";
    public static String AliPassword = "123456weznwpu906";
    //在云数据库中查询数据的语句
    public static String selectusername = "Select username From usermessage Where user = ?;";
    public static String selectuserpass = "Select password From usermessage Where pass = ?;";
    //在云数据库中插入用户名到表uasermessage中
    public static String insertusername = "Insert into usermassage(username) values(?);";
    //向云数据库中把用户的password插入到表usermessage中
    public static String insertuserpass = "insert into usermessage(password) values(?);";
    //从云端获取秘钥
    public static String sql1="select filekey1 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";
    public static String sql2="select filekey2 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";
    public static String sql3="select filekey3 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";
    public static String sql4="select filekey4 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";
    public static String sql5="select filekey5 from secretkey where username ='" + 1111 + "'and filename ='" + 2222 +"'";

}

