package com.yazilimciakli.oneway.Database.TableResponse;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "profile")
public class ProfileResponse {
    public final static String ID="id";
    public final static String HEALT="healt";
    public final static String ENDED_TIME="endedTime";
    public final static String TOTAL_COINS="totalCoins";
    public final static String TOTAL_COEFFICIENT="totalCoefficient";
    public final static String TIME_STAMP="timeStamp";

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = HEALT)
    private int healt;
    @DatabaseField(columnName = TOTAL_COINS)
    private int totalCoins;
    @DatabaseField(columnName = TOTAL_COEFFICIENT)
    private String totalCoefficient;
    @DatabaseField(columnName = TIME_STAMP)
    private String timeStamp;
    @DatabaseField(columnName = ENDED_TIME)
    private String endedTime;
    public ProfileResponse(int id, int healt, int totalCoins, String totalCoefficient, String timeStamp, String endedTime) {
        this.id = id;
        this.healt = healt;
        this.totalCoins = totalCoins;
        this.totalCoefficient = totalCoefficient;
        this.timeStamp = timeStamp;
        this.endedTime = endedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealt() {
        return healt;
    }

    public void setHealt(int healt) {
        this.healt = healt;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }

    public String getTotalCoefficient() {
        return totalCoefficient;
    }

    public void setTotalCoefficient(String totalCoefficient) {
        this.totalCoefficient = totalCoefficient;
    }

    public String getEndedTime() {
        return endedTime;
    }

    public void setEndedTime(String endedTime) {
        this.endedTime = endedTime;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
