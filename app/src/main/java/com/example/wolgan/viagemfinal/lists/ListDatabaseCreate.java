package com.example.wolgan.viagemfinal.lists;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wolgan on 28/04/17.
 */

public class ListDatabaseCreate extends SQLiteOpenHelper{
    private static final String db = "banco.db";
    public static final String table = "lists";
    public static final String id = "_id";
    public static final String name = "name";
    private static final int version = 6;


    public ListDatabaseCreate(Context context) {
        super(context, db, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+table+" ("
                + id + " integer primary key autoincrement,"
                + name + " text "
                +");";
        System.out.println("tentou");
        db.execSQL(sql);
        String sql_i = "CREATE TABLE list_items (" +
                "list_id integer integer foreign_key references lists(_id), " +
                "item_id integer integer foreign_key references items(_id), " +
                "checked tinyint," +
                "qnt integer," +
                "primary key(list_id, item_id) "+
        ")";
        db.execSQL(sql_i);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table);
        onCreate(db);
    }
}
