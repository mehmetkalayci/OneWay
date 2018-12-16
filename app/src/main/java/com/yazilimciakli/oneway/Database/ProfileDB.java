package com.yazilimciakli.oneway.Database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.yazilimciakli.oneway.Database.TableResponse.ProfileResponse;

import java.sql.SQLException;

public class ProfileDB extends CoreDB<ProfileResponse> {
    Dao<ProfileResponse, Integer> dao;

    public ProfileDB(Context context) {
        super(context);
        try {
            dao = getSqLiteDBHelper().getProfileResponses();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
