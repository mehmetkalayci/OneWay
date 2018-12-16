package com.yazilimciakli.oneway.Database.TableResponse;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "points")
public class PointResponse {

    public static final String LEVEL_ID="levelID";
    public static final String POINT_ID="pointID";
    public static final String IS_SUB_POINT="isSubPoint";
    public static final String POSX="posX";
    public static final String POSY="posY";

    @DatabaseField(columnName = LEVEL_ID)
    private int levelID;
    @DatabaseField(columnName = POINT_ID)
    private int pointID;
    @DatabaseField(columnName = IS_SUB_POINT)
    private int isSubPoint;
    @DatabaseField(columnName = POSY)
    private int y;
    @DatabaseField(columnName = POSX)
    private int x;

    public PointResponse(int levelID, int pointID, int isSubPoint, int posY, int posX) {
        this.levelID = levelID;
        this.pointID = pointID;
        this.isSubPoint = isSubPoint;
        this.y = posY;
        this.x = posX;
    }

    public int getLevelID() {
        return levelID;
    }

    public int getPointID() {
        return getPointID();
    }

    public int getIsSubPoint() {
        return getIsSubPoint();
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setLevelID(int levelID) {
        this.levelID = levelID;
    }

    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    public void setIsSubPoint(int isSubPoint) {
        this.isSubPoint = isSubPoint;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
