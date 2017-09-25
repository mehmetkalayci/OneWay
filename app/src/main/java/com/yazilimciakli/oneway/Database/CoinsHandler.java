package com.yazilimciakli.oneway.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class CoinsHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "coin";

    //Healt table name
    private static final String TABLE_HEALTH = "coins";

    // Healt Table Columns names
    private static final String KEY_HEALTH_ID = "coinsID";
    private static final String KEY_HEALTH = "totalCoin";
    private static final String KEY_COEFFICINT = "totalCoefficient";
    /*HEALT TABLES END*/

    Context cntx;
    public CoinsHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        cntx=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*HEALT TABLE CREATE*/
        String CREATE_TABLE = "CREATE TABLE " + TABLE_HEALTH + "("
                + KEY_HEALTH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HEALTH + " INTEGER,"
                + KEY_COEFFICINT + " INTEGER"
                + ")";
        db.execSQL(CREATE_TABLE);
        setDefaultLabel(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void setDefaultLabel(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        DatabaseHandler databaseHandler = new DatabaseHandler(cntx);
        if(databaseHandler.getPoints()==null)
        {
            values.put(KEY_HEALTH, 0);
            values.put(KEY_COEFFICINT, 1);
        }
        else
        {
            values.put(KEY_HEALTH, Integer.parseInt(databaseHandler.getPoints()));
            values.put(KEY_COEFFICINT, 1);
        }
        db.insert(TABLE_HEALTH, null, values);
    }

    public HashMap<String, String> getCoins(int id) {
        HashMap<String, String> memos = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM " + TABLE_HEALTH + " WHERE coinsID=" + id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            memos.put(KEY_HEALTH, cursor.getString(1));
            memos.put(KEY_COEFFICINT, cursor.getString(2));
        }
        cursor.close();
        db.close();
        return memos;
    }

    public void setCoins(Integer health, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_HEALTH, health);

        db.update(TABLE_HEALTH, values, KEY_HEALTH_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
    public void setCount(Integer count, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COEFFICINT, count);

        db.update(TABLE_HEALTH, values, KEY_HEALTH_ID + " = ?",
                new String[]{String.valueOf(id)});
    }
}
