package com.example.mymodel;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addUser(String email, String password) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        return db.insert("users", null, values);
    }

    public boolean checkUser(String email, String password) {
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email=? AND password=?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
    public List<String> getAllUserEmails() {
        List<String> emails = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT email FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                String email = cursor.getString(0);
                emails.add(email);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return emails;
    }
}