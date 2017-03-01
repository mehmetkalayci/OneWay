
package com.yazilimciakli.oneway.Level;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Level {

    @SerializedName("levelid")
    @Expose
    public int levelid;
    @SerializedName("name")
    @Expose
    public String name;
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

}
