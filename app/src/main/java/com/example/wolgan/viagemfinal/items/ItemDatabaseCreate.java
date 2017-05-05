package com.example.wolgan.viagemfinal.items;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wolgan on 26/04/17.
 */

public class ItemDatabaseCreate extends SQLiteOpenHelper {
    private static final String db = "banco.db";
    public static final String table = "items";
    public static final String id = "_id";
    public static final String name = "name";
    public static final String category_id = "category_id";
    private static final int version = 6;


    public ItemDatabaseCreate(Context context) {
        super(context, db, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+table+" ("
                + id + " integer primary key autoincrement,"
                + name + " text, "
                + category_id + " integer foreign_key references category(_id)"
                +");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }
}
