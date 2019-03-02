package com.yazilimciakli.oneway.Database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Database.TableResponse.PlayLevelDataResponse;
import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;

import java.sql.SQLException;

public class ProfileDB extends CoreDB<ProfileResponse> {
    Dao<ProfileResponse, Integer> dao;

    public ProfileDB(Context context) {
        super(context);
        try {
            dao = getSqLiteDBHelper().getProfileResponses();
            setDao(dao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ProfileResponse get(int Id) {
        try {
            return dao.queryBuilder().where().eq(ProfileResponse.ID, Id).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public UpdateBuilder<ProfileResponse, Integer> getUpdateBuilder(int id) {
        UpdateBuilder<ProfileResponse, Integer> updateBuilder = dao.updateBuilder();
        try {
            updateBuilder.where().eq(ProfileResponse.ID, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateBuilder;
    }
    public UpdateBuilder<ProfileResponse, Integer> setHealt(int id,int healt,long time) {
        UpdateBuilder<ProfileResponse, Integer> updateBuilder = dao.updateBuilder();
        try {
            updateBuilder.where().eq(ProfileResponse.ID, id);
            updateBuilder.updateColumnValue(ProfileResponse.HEALT,healt);
            updateBuilder.updateColumnValue(ProfileResponse.TIME_STAMP,time);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateBuilder;
    }
    public UpdateBuilder<ProfileResponse, Integer> setCount(int id,int coefficient) {
        UpdateBuilder<ProfileResponse, Integer> updateBuilder = dao.updateBuilder();
        try {
            updateBuilder.where().eq(ProfileResponse.ID, id);
            updateBuilder.updateColumnValue(ProfileResponse.TOTAL_COEFFICIENT,coefficient);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateBuilder;
    }
    public UpdateBuilder<ProfileResponse, Integer> setCoins(int id,int coins) {
        UpdateBuilder<ProfileResponse, Integer> updateBuilder = dao.updateBuilder();
        try {
            updateBuilder.where().eq(ProfileResponse.ID, id);
            updateBuilder.updateColumnValue(ProfileResponse.TOTAL_COINS,coins);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateBuilder;
    }
}
