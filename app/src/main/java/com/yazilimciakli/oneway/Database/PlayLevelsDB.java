package com.yazilimciakli.oneway.Database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yazilimciakli.oneway.Database.TableResponse.PlayLevelDataResponse;

import java.sql.SQLException;

public class PlayLevelsDB extends CoreDB<PlayLevelDataResponse> {

    Dao<PlayLevelDataResponse, Integer> dao;

    public PlayLevelsDB(Context context) {
        super(context);
        try {
            dao = getSqLiteDBHelper().getPlayLevelData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
