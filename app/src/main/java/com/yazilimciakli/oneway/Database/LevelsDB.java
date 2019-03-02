package com.yazilimciakli.oneway.Database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class LevelsDB extends CoreDB<LevelsResponse> {

    Dao<LevelsResponse, Integer> dao;

    public LevelsDB(Context context) {
        super(context);
        try {
            dao = getSqLiteDBHelper().getLevelsResponses();
            setDao(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public LevelsResponse get(int levelID) {
        try {
            return dao.queryBuilder().where().eq(LevelsResponse.LEVEL_ID, levelID).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
