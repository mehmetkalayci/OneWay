package com.yazilimciakli.oneway.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "oneway";

    // Levels table name
    private static final String TABLE_LEVELS = "levels";

    // Levels Table Columns names
    private static final String KEY_LEVEL_ID = "levelId";
    private static final String KEY_SCORE = "score";
    private static final String KEY_ELAPSED_TIME = "elapsedTime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LEVELS_TABLE = "CREATE TABLE " + TABLE_LEVELS + "("
                + KEY_LEVEL_ID + " INTEGER PRIMARY KEY," + KEY_SCORE + " INTEGER,"
                + KEY_ELAPSED_TIME + " INTEGER" + ")";

        db.execSQL(CREATE_LEVELS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
        onCreate(db);
    }

    public void addLevel(Level level) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LEVEL_ID, level.levelId);
        values.put(KEY_SCORE, level.score);
        values.put(KEY_ELAPSED_TIME, level.elapsedTime);

        db.insert(TABLE_LEVELS, null, values);
        db.close();
    }

    public Level getLevel(int levelId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Level level = null;

        Cursor cursor = db.query(TABLE_LEVELS, new String[]{KEY_LEVEL_ID, KEY_SCORE, KEY_ELAPSED_TIME}, KEY_LEVEL_ID + "=?", new String[]{String.valueOf(levelId)}, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            level = new Level(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
        }

        return level;
    }

    public List<Level> getAllLevels() {
        List<Level> levelList = new ArrayList<Level>();

        String selectQuery = "SELECT  * FROM " + TABLE_LEVELS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Level level = new Level();
                level.setLevelId(cursor.getInt(0));
                level.setScore(cursor.getInt(1));
                level.setElapsedTime(cursor.getInt(2));

                levelList.add(level);
            } while (cursor.moveToNext());
        }

        return levelList;
    }

    public String getPoints() {
        String countQuery = "SELECT  SUM(score) FROM " + TABLE_LEVELS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        return cursor.getString(0);
    }

    public int updateLevel(Level level) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SCORE, level.getScore());
        values.put(KEY_ELAPSED_TIME, level.getElapsedTime());

        return db.update(TABLE_LEVELS, values, KEY_LEVEL_ID + " = ?", new String[]{String.valueOf(level.getLevelId())});
    }

    public void deleteContact(Level level) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEVELS, KEY_LEVEL_ID + " = ?", new String[]{String.valueOf(level.getLevelId())});
        db.close();
    }

}
