package com.yazilimciakli.oneway.Database;


public class Level {

    Integer levelId, score, elapsedTime;

    public Level() {
    }

    public Level(Integer levelId, Integer score, Integer elapsedTime) {
        this.levelId = levelId;
        this.score = score;
        this.elapsedTime = elapsedTime;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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
    @Override
    public String toString() {
        return "Level{" +
                "levelid=" + getLevelId() +
                ", score=" + getScore() +
                ", ElapsedTime=" + getElapsedTime() +
                '}';
    }
}
