package com.yazilimciakli.oneway.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PlayLevelDataResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PointResponse;
import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;
import com.yazilimciakli.oneway.Level.Point;

import java.sql.SQLException;

public class SQLiteDBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "oneway";
    private static final int DATABASE_VERSION = 1;

    private Dao<ProfileResponse, Integer> profileResponsesDao;
    private Dao<LevelsResponse, Integer> levelsResponsesDao;
    private Dao<PointResponse, Integer> pointResponsesDao;
    private Dao<PlayLevelDataResponse, Integer> playLevelDataDao;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ProfileResponse.class);
            TableUtils.createTable(connectionSource, LevelsResponse.class);
            TableUtils.createTable(connectionSource, PointResponse.class);
            TableUtils.createTable(connectionSource, PlayLevelDataResponse.class);
        }catch (Exception e)
        {
            Log.d("Create Error", "onCreate: "+e.getMessage());
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
    public Dao<ProfileResponse, Integer> getProfileResponses() throws SQLException {
        if (profileResponsesDao == null) {
            profileResponsesDao = getDao(ProfileResponse.class);
        }
        return profileResponsesDao;
    }

    public Dao<LevelsResponse, Integer> getLevelsResponses() throws SQLException {
        if (levelsResponsesDao == null) {
            levelsResponsesDao = getDao(LevelsResponse.class);
        }
        return levelsResponsesDao;
    }

    public Dao<PointResponse, Integer> getPointResponses() throws SQLException {
        if (pointResponsesDao == null) {
            pointResponsesDao = getDao(PointResponse.class);
        }
        return pointResponsesDao;
    }

    public Dao<PlayLevelDataResponse, Integer> getPlayLevelData() throws SQLException {
        if (playLevelDataDao == null) {
            playLevelDataDao = getDao(PlayLevelDataResponse.class);
        }
        return playLevelDataDao;
    }


}
