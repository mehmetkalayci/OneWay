
package com.yazilimciakli.oneway.Level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Level {

    @SerializedName("levelid")
    @Expose
    public int levelid;

    @SerializedName("points")
    @Expose
    public List<Point> points = null;
    @SerializedName("moveNumber")
    @Expose
    public Integer moveNumber;
    @SerializedName("score")
    @Expose
    public Integer score;
    @SerializedName("time")
    @Expose
    public Integer time;

    @Override
    public String toString() {
        return "Level{" +
                "levelid=" + levelid +
                ", points=" + points +
                ", moveNumber=" + moveNumber +
                ", score=" + score +
                ", time=" + time +
                '}';
    }
}
