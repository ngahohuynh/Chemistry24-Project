package com.example.user.chemistry24.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBOpenHelper extends SQLiteAssetHelper {

    private static final String DB_NAME = "chemistry24.db";
    private static final int DB_VERSION = 10;

    public DBOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }
}
