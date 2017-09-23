package com.yazilimciakli.oneway.Level;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Point {

    @SerializedName("x")
    @Expose
    public Integer x;
    @SerializedName("y")
    @Expose
    public Integer y;
    @SerializedName("subpoints")
    @Expose
    public List<Subpoint> subpoints = null;

}
