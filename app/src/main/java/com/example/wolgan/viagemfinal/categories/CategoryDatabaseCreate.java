package com.example.wolgan.viagemfinal.categories;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wolgan on 09/04/17.
 */

public class CategoryDatabaseCreate extends SQLiteOpenHelper {
    private static final String db = "banco.db";
    public static final String table = "categories";
    public static final String id = "_id";
    public static final String name = "name";
    private static final int version = 6;


    public CategoryDatabaseCreate(Context context) {
        super(context, db, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+table+" ("
                + id + " integer primary key autoincrement,"
                + name + " text"
                +");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + table);
        onCreate(db);
    }
}
