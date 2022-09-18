package com.example.mtn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class DBHelper  extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(eNumber TEXT primary key,ePin TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String eNumber, String tMessage) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("eNumber", tMessage);
        contentValues.put("ePin", eNumber);
        long result = DB.insert("users", null, contentValues);
        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkusername(String eNumber) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where eNumber=?", new String[]{eNumber});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String eNumber, String ePin) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where eNumber = ? and ePin=?", new String[]{eNumber, ePin});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;

    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from users", null);
        return cursor;
    }


    public Cursor SignIn() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select ePin from users", null, null);
        cursor.close();
        return cursor;
    }



    }

