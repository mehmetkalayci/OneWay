package com.yazilimciakli.oneway.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class CoreDB<T>{

    private Dao<T, Integer> dao;
    private SQLiteDBHelper sqLiteDBHelper;
    private Context context;

    public CoreDB(Context context) {
        this.context = context;
    }

    public Dao getDao() {
        return dao;
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }

    public SQLiteDBHelper getSqLiteDBHelper() {
        if (sqLiteDBHelper == null) {
            sqLiteDBHelper = OpenHelperManager.getHelper(context, SQLiteDBHelper.class);
        }
        return sqLiteDBHelper;
    }
    public void create(List<T> clazz) {
        try {
            dao.create(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void create(T clazz) {
        try {
            dao.create(clazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<T> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public void clear(List<T> delete) {
        try {
            dao.delete(delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public long count() {
        try {
            return dao.queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
