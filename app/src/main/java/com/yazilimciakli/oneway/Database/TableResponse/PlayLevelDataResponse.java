package com.yazilimciakli.oneway.Database.TableResponse;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.yazilimciakli.oneway.Database.PlayLevelsDB;

@DatabaseTable(tableName = "playLevelData")
public class PlayLevelDataResponse {
    @DatabaseField(id = true)
    private Integer id;
    @DatabaseField(columnName = "levelID")
    private int levelID;
    @DatabaseField(columnName = "score")
    private int score;
    @DatabaseField(columnName = "elapsedTime")
    private int elapsedTime;

    public PlayLevelDataResponse()
    {

    }
    public PlayLevelDataResponse(Integer levelId, Integer score, Integer elapsedTime) {

        this.levelID = levelId;
        this.score = score;
        this.elapsedTime = elapsedTime;
    }

    public Integer getLevelId() {
        return levelID;
    }

    public void setLevelId(Integer levelId) {
        this.levelID = levelId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
