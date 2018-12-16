package com.yazilimciakli.oneway.LevelRequest.Request;

public class LevelRequest {
    private String time;

    private String moveNumber;

    private String score;

    private Points[] points;

    private String levelid;

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

    public Points[] getPoints() {
        return points;
    }

    public void setPoints(Points[] points) {
        this.points = points;
    }

    public String getLevelid() {
        return levelid;
    }

    public void setLevelid(String levelid) {
        this.levelid = levelid;
    }

    @Override
    public String toString() {
        return "ClassPojo [time = " + time + ", moveNumber = " + moveNumber + ", score = " + score + ", points = " + points + ", levelid = " + levelid + "]";
    }
}