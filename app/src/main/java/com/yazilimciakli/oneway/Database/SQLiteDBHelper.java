package com.yazilimciakli.oneway.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PlayLevelDataResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PointResponse;
import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;

import java.sql.SQLException;

public class SQLiteDBHelper extends OrmLiteSqliteOpenHelper {

    Dao<ProfileResponse, Integer> profileResponses;
    Dao<LevelsResponse, Integer> levelsResponses;
    Dao<PointResponse, Integer> pointResponses;
    Dao<PlayLevelDataResponse, Integer> playLevelData;

    public SQLiteDBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        TableUtils.createTable(connectionSource, profileResponses);
        TableUtils.createTable(connectionSource, levelsResponses);
        TableUtils.createTable(connectionSource, pointResponses);
        TableUtils.createTable(connectionSource, playLevelData);
    }

    public Dao<ProfileResponse, Integer> getProfileResponses() throws SQLException {
        if (profileResponses == null) {
            profileResponses = getDao(ProfileResponse.class);
        }
        return profileResponses;
    }

    public Dao<LevelsResponse, Integer> getLevelsResponses() throws SQLException {
        if (levelsResponses == null) {
            levelsResponses = getDao(LevelsResponse.class);
        }
        return levelsResponses;
    }

    public Dao<PointResponse, Integer> getPointResponses() throws SQLException {
        if (pointResponses == null) {
            pointResponses = getDao(PointResponse.class);
        }
        return pointResponses;
    }

    public Dao<PlayLevelDataResponse, Integer> getPlayLevelData() throws SQLException {
        if (playLevelData == null) {
            playLevelData = getDao(PlayLevelDataResponse.class);
        }
        return playLevelData;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
