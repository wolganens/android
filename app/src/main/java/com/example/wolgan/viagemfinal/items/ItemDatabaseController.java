package com.example.wolgan.viagemfinal.items;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wolgan.viagemfinal.categories.CategoryDatabaseController;

/**
 * Created by wolgan on 26/04/17.
 */

public class ItemDatabaseController {
    private final Context context;
    private SQLiteDatabase sqlDatabase;
    public ItemDatabaseCreate sqlOpenHelper;

    public ItemDatabaseController(Context context){
        this.context = context;
        this.sqlOpenHelper = new ItemDatabaseCreate(context);
    }
    private void setReadableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getReadableDatabase();
    }
    private void setWriteableSqlDatabase(){
        this.sqlDatabase = this.sqlOpenHelper.getWritableDatabase();
    }
    public Cursor retrieve(){
        this.setReadableSqlDatabase();
        String[] fields =  {ItemDatabaseCreate.id, ItemDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(ItemDatabaseCreate.table, fields, null, null, null, null, "category_id", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        this.sqlDatabase.close();
        return cursor;
    }
    public Long insert(String name, long category_id){
        ContentValues values = new ContentValues();
        values.put("name", name.toString());
        values.put("category_id", category_id);
        this.setWriteableSqlDatabase();
        return this.sqlDatabase.insert(this.sqlOpenHelper.table,null,values);
    }
    public int update(long id, String newName, long newCatId){
        this.setWriteableSqlDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("category_id", newCatId);
        int updated = this.sqlDatabase.update(ItemDatabaseCreate.table, values, "_id = " + id, null);
        return updated;
    }
    public int delete(long id){
        this.setWriteableSqlDatabase();
        System.out.println(id);
        int delete = this.sqlDatabase.delete(ItemDatabaseCreate.table, "_id = " + id, null);
        return delete;
    }
    public Item find(Long id){
        this.setReadableSqlDatabase();
        String[] campos =  {ItemDatabaseCreate.id, ItemDatabaseCreate.name, ItemDatabaseCreate.category_id};
        Cursor cursor = this.sqlDatabase.query(ItemDatabaseCreate.table, campos, "_id = " + id.toString(), null, null, null, null, null);
        Item item= null;
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                long category_id = cursor.getLong(cursor.getColumnIndex("category_id"));
                System.out.println(category_id);
                CategoryDatabaseController catDbController = new CategoryDatabaseController(this.context);
                item = new Item(_id, category_id, name);
                cursor.moveToNext();
            }
        }
        cursor.close();
        this.sqlDatabase.close();
        return item;
    }
    public Cursor findByList(long id){
        this.setReadableSqlDatabase();
        String[] fields =  {"items." + ItemDatabaseCreate.id, "items." + ItemDatabaseCreate.name};
        Cursor cursor = this.sqlDatabase.query(ItemDatabaseCreate.table + " INNER JOIN list_items on list_items.list_id = " +id+ " and items._id = list_items.item_id", fields, null, null, null, null, "category_id", null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        this.sqlDatabase.close();
        return cursor;
    }
    public long insertItemToList(long item_id, long list_id){
        this.setWriteableSqlDatabase();
        ContentValues values = new ContentValues();
        values.put("item_id", item_id);
        values.put("list_id", list_id);
        return this.sqlDatabase.insert("list_items", null ,values);
    }
}
