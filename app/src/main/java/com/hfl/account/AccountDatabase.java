package com.hfl.account;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hfl on 2018/6/2.
 */

public class AccountDatabase {
    private  MyHelper helper;
    public AccountDatabase(MainActivity context){
        helper=new MyHelper(context);
    }
    public void insert(Account a){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",a.getName());
        values.put("balance",a.getBalance());
        // 向account表插入数据values,
        long id=db.insert("account",null,values);
        a.setId(id);
        db.close();
    }
    public int delete(long id){
        SQLiteDatabase db=helper.getWritableDatabase();
        int count=db.delete("account","_id=?",
                new String[]{id+""});// 按条件删除指定表中的数据, 返回受影响的行数
        db.close();
        return count;
    }
    public int update(Account a) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues(); // 要修改的数据
        values.put("name", a.getName());
        values.put("balance", a.getBalance());
        int count = db.update("account", values, "_id=?",
                new String[] { a.getId() + "" }); // 更新并得到行数
        db.close();
        return count;
    }
    public List<Account> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
       Cursor c=db.query("account",null,null,null
       ,null,null,"balance DESC");
       List<Account> list=new ArrayList<Account>();
       while (c.moveToNext()){
           long id=c.getLong(c.getColumnIndex("_id"));
           String name=c.getString(1);
           int balance=c.getInt(2);
           list.add(new Account(id,name,balance));
       }
        c.close();
        db.close();
        return list;
    }
}
