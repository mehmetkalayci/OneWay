package com.yazilimciakli.oneway.Database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PointResponse;
import com.yazilimciakli.oneway.Level.Point;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class PointsDB extends CoreDB<PointResponse> {
    Dao<PointResponse, Integer> dao;

    public PointsDB(Context context) {
        super(context);
        try {
            dao = getSqLiteDBHelper().getPointResponses();
            setDao(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public PointResponse get(int levelID) {
        try {
            return dao.queryBuilder().where().eq(PointResponse.LEVEL_ID, levelID).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<PointResponse> getPoint() {
        try {
            return dao.queryBuilder().where().eq(PointResponse.IS_SUB_POINT, 0).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public List<PointResponse> getSubPoint(int subID) {
        try {
            return dao.queryBuilder().where().eq(PointResponse.IS_SUB_POINT, 1)
                    .and().eq(PointResponse.POINT_ID,subID).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    public long getPointCount() {
        try {
            return dao.queryBuilder().where().eq(PointResponse.IS_SUB_POINT,0).countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
