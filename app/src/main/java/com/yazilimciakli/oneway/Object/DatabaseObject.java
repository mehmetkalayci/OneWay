package com.yazilimciakli.oneway.Object;

import android.content.Context;

import com.yazilimciakli.oneway.Database.LevelsDB;
import com.yazilimciakli.oneway.Database.PlayLevelsDB;
import com.yazilimciakli.oneway.Database.PointsDB;
import com.yazilimciakli.oneway.Database.ProfileDB;

public class DatabaseObject {
    private static DatabaseObject dataBaseObject = null;

    private LevelsDB levelsDB;
    private PointsDB pointsDB;
    private ProfileDB profileDB;
    private PlayLevelsDB playLevelsDB;
    private Context mContext;

    private DatabaseObject(Context context) {
        this.mContext=context;
        levelsDB = new LevelsDB(mContext);
        pointsDB = new PointsDB(mContext);
        profileDB = new ProfileDB(mContext);
        playLevelsDB = new PlayLevelsDB(mContext);
    }
    public static DatabaseObject newInstance(Context context) {
        synchronized (context) {
            if (dataBaseObject == null) {
                dataBaseObject = new DatabaseObject(context);
            }
        }
        return dataBaseObject;
    }

    public LevelsDB getLevelsDB() {
        return levelsDB;
    }

    public PointsDB getPointsDB() {
        return pointsDB;
    }

    public ProfileDB getProfileDB() {
        return profileDB;
    }

    public PlayLevelsDB getPlayLevelsDB() {
        return playLevelsDB;
    }

}
