package com.example.wolgan.viagemfinal.items;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wolgan on 26/04/17.
 */

public class Item implements Parcelable {
    private String name;
    private long category;
    private long _id;
    public Item(long id, long category_id, String name){
        this._id = id;
        this.category = category_id;
        this.name = name;
    }

    protected Item(Parcel in) {
        this._id = in.readLong();
        this.category = in.readLong();
        this.name = in.readString();
    }


    public String getName(){
        return this.name;
    }
    public long getCategory(){
        return this.category;
    }
    public long getId(){
        return this._id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeLong(category);
        dest.writeString(name);
    }
    public static final Creator<Item> CREATOR
            = new Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
