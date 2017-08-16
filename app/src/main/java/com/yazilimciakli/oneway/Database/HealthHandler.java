package com.yazilimciakli.oneway.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by Admin on 16.08.2017.
 */

public class HealthHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "heal";

    //Healt table name
    private static final String TABLE_HEALT = "health";

    // Healt Table Columns names
    private static final String KEY_HEALT_ID = "healthId";
    private static final String KEY_HEALT = "health";
    private static final String KEY_ENDED_TIME = "timeStamp";
    /*HEALT TABLES END*/



    public HealthHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*HEALT TABLE CREATE*/
        String CREATE_TABLE = "CREATE TABLE " + TABLE_HEALT + "("
                + KEY_HEALT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HEALT + " INTEGER,"
                + KEY_ENDED_TIME + " INTEGER)";
        db.execSQL(CREATE_TABLE);
        setDefaultLabel(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void setDefaultLabel(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(KEY_HEALT, 5);

        db.insert(TABLE_HEALT, null, values);
    }

    public HashMap<String, String> getHealt(int id){


        HashMap<String,String> memos = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_HEALT+ " WHERE healthId="+id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            memos.put(KEY_HEALT, cursor.getString(1));
            memos.put(KEY_ENDED_TIME, cursor.getString(2));
        }
        cursor.close();
        db.close();
        return memos;
    }

    public void setHealt(Integer healt,Long endedTime, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HEALT, healt);
        values.put(KEY_ENDED_TIME, endedTime);
        db.update(TABLE_HEALT, values, KEY_HEALT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }
}
