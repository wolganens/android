package com.example.wolgan.viagemfinal.lists;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wolgan.viagemfinal.categories.Category;
import com.example.wolgan.viagemfinal.categories.CategoryDatabaseCreate;
import com.example.wolgan.viagemfinal.lists.ListDatabaseCreate;

/**
 * Created by wolgan on 28/04/17.
 */

public class ListDatabaseController {
    private SQLiteDatabase sqlDatabase;
    public ListDatabaseCreate sqlOpenHelper;

    public ListDatabaseController(Context context){
        this.sqlOpenHelper = new ListDatabaseCreate(context);
    }
    public long insert (String name){
        this.setWriteableSqlDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name.toString());
        return this.sqlDatabase.insert(this.sqlOpenHelper.table,null,values);
    }
    private void setReadableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getReadableDatabase();
    }
    private void setWriteableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getWritableDatabase();
    }
    public Cursor retrieve(){
        this.setReadableSqlDatabase();
        String[] fields =  {ListDatabaseCreate.id, ListDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(ListDatabaseCreate.table, fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        this.sqlDatabase.close();
        return cursor;
    }
    public List find(Long id){
        this.setReadableSqlDatabase();
        String[] campos =  {ListDatabaseCreate.id, ListDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(ListDatabaseCreate.table, campos, "_id = " + id.toString(), null, null, null, null, null);
        List list = null;
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                list = new List(_id,name);

                cursor.moveToNext();
            }
        }
        cursor.close();
        this.sqlDatabase.close();

        return list;
    }
}
