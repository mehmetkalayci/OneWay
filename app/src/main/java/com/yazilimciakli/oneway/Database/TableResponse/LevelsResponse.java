package com.yazilimciakli.oneway.Database.TableResponse;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "levels")
public class LevelsResponse {
    public final static String LEVEL_ID = "levelID";
    public final static String SCORE = "score";
    public final static String MOVE_NUMBER = "moveNumber";
    public final static String TIME = "time";


    @DatabaseField(columnName = TIME)
    private String time;
    @DatabaseField(columnName = MOVE_NUMBER)
    private String moveNumber;
    @DatabaseField(columnName = SCORE)
    private String score;
    @DatabaseField(columnName = LEVEL_ID)
    private int levelid;

    public LevelsResponse() {

    }

    public LevelsResponse(String time, String moveNumber, String score, int levelid) {
        this.levelid = levelid;
        this.moveNumber = moveNumber;
        this.score = score;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(String moveNumber) {
        this.moveNumber = moveNumber;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    public int getLevelid() {
        return levelid;
    }

    public void setLevelid(int levelid) {
        this.levelid = levelid;
    }
}