package com.mxn.soul.flowingdrawer.SQLite;

/**
 * Created by ZEROYU on 6/7/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHandler extends SQLiteOpenHelper {

    /**
     *
     * @param context
     *            上下文
     * @param name
     *            数据库名
     * @param factory
     *            可选的数据库游标工厂类，当查询(query)被提交时，该对象会被调用来实例化一个游标。默认为null。
     * @param version
     *            数据库版本号
     */
    public DBOpenHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {// 覆写onCreate方法，当数据库创建时就用SQL命令创建一个表
        // 创建一个t_users表，id主键，自动增长，字符类型的username和pass;
        db.execSQL("create table t_users(id integer primary key autoincrement,username varchar(200),filename varchar(200),key1 varchar(200),key2 varchar(200),key3 varchar(200),key4 varchar(200),key5 varchar(200) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
