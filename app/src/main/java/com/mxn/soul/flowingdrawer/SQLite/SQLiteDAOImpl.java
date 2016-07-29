package com.mxn.soul.flowingdrawer.SQLite;

/**
 * Created by ZEROYU on 6/7/2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mxn.soul.flowingdrawer.data.SecretKey;

public class SQLiteDAOImpl {
    private DBOpenHandler dbOpenHandler;

    public SQLiteDAOImpl(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context, "dbtest.db", null, 1);
    }

    public void save(SecretKey scretkey) {// 插入记录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();// 取得数据库操作
        db.execSQL("insert into t_users (username,filename,key1,key2,key3,key4,key5) values(?,?,?,?,?,?,?)", new Object[] { scretkey.getUsername(), scretkey.getFilename(),scretkey.getFilekey1(),scretkey.getFilekey2(),scretkey.getFilekey3(),scretkey.getFilekey4(),scretkey.getFilekey5() });
        db.close();// 记得关闭数据库操作
    }

    public void delete(String filename) {// 删除纪录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.execSQL("delete from t_users where filename=?", new Object[] { filename });
        db.close();
    }


    public SecretKey find(String filename) {// 根据ID查找纪录
        SecretKey scretkey = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // 用filename接收从数据库检索到的数据
        Cursor cursor = db.rawQuery("select * from t_users where filename=?", new String[] { filename });
        if (cursor.moveToFirst()) {// 依次取出数据
            scretkey.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            scretkey.setFilename(cursor.getString(cursor.getColumnIndex("filename")));
            scretkey.setFilekey1(cursor.getString(cursor.getColumnIndex("key1")));
            scretkey.setFilekey2(cursor.getString(cursor.getColumnIndex("key2")));
            scretkey.setFilekey3(cursor.getString(cursor.getColumnIndex("key3")));
            scretkey.setFilekey4(cursor.getString(cursor.getColumnIndex("key4")));
            scretkey.setFilekey5(cursor.getString(cursor.getColumnIndex("key5")));
        }
        db.close();
        return scretkey;
    }

    /*public List<TUsers> findAll() {// 查询所有记录
        List<TUsers> lists = new ArrayList<TUsers>();
        TUsers tusers = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
        // String[]{offset.toString(),maxLength.toString()});
        // //这里支持类型MYSQL的limit分页操作

        Cursor cursor = db.rawQuery("select * from t_users ", null);
        while (cursor.moveToNext()) {
            tusers = new TUsers();
            tusers.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tusers.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            tusers.setPass(cursor.getString(cursor.getColumnIndex("pass")));
            lists.add(tusers);
        }
        db.close();
        return lists;
    }*/

    public long getCount() {//统计所有记录数
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from t_users ", null);
        cursor.moveToFirst();
        db.close();
        return cursor.getLong(0);
    }

}
