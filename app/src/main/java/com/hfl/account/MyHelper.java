package com.hfl.account;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hfl on 2018/6/2.
 */

public class MyHelper extends SQLiteOpenHelper {
    public MyHelper(Context context){
        super(context,"accout.db",null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("onCreate");
        db.execSQL("Create TABLE account(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(20),balance INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
    }
}
