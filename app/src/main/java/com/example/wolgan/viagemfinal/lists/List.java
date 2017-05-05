package com.example.wolgan.viagemfinal.lists;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wolgan on 23/04/17.
 */

public class List implements Parcelable{
    private long _id;
    private String name;

    public List(long _id, String name){
        this._id = _id;
        this.name = name;
    }

    public List(Parcel in) {
        _id = in.readLong();
        name = in.readString();
    }

    public String getName(){
        return this.name;
    }
    public long getId(){
        return this._id;
    }
    private void setName(String name){
        this.name = name;
    }
    private void setId(long id){
        this._id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeString(name);
    }
    public static final Creator<List> CREATOR
            = new Creator<List>() {
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        public List[] newArray(int size) {
            return new List[size];
        }
    };

}