package com.example.wolgan.viagemfinal.categories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;

import java.util.List;

/**
 * Created by wolgan on 13/04/17.
 */

public class CategoryDatabaseController {
    private SQLiteDatabase sqlDatabase;
    public CategoryDatabaseCreate sqlOpenHelper;
    private List<Category> categories;

    public CategoryDatabaseController(Context context){
        this.sqlOpenHelper = new CategoryDatabaseCreate(context);
    }
    private void setReadableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getReadableDatabase();
    }
    private void setWriteableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getWritableDatabase();
    }
    public Long insert(String name){
        ContentValues values = new ContentValues();
        values.put("name", name.toString());
        this.setWriteableSqlDatabase();
        return this.sqlDatabase.insert(this.sqlOpenHelper.table,null,values);
    }
    public Cursor retrieve(){
        this.setReadableSqlDatabase();
        String[] fields =  {CategoryDatabaseCreate.id, CategoryDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(CategoryDatabaseCreate.table, fields, null, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        this.sqlDatabase.close();
        return cursor;
    }
    public Category find(Long id){
        this.setReadableSqlDatabase();
        String[] campos =  {CategoryDatabaseCreate.id, CategoryDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(CategoryDatabaseCreate.table, campos, "_id = " + id.toString(), null, null, null, null, null);
        Category category = null;
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                category = new Category(_id,name);

                cursor.moveToNext();
            }
        }
        cursor.close();
        this.sqlDatabase.close();

        return category;
    }
    public int update(long id, String newName){
        this.setWriteableSqlDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        int updated = this.sqlDatabase.update(CategoryDatabaseCreate.table, values, "_id = " + id, null);
        return updated;
    }
    public int delete(long id){
        this.setWriteableSqlDatabase();
        int delete = this.sqlDatabase.delete(CategoryDatabaseCreate.table, "_id = " + id, null);
        return delete;
    }
    public List<Category> cursorToCagetory(Cursor cursor){
        return this.categories;
    }
}