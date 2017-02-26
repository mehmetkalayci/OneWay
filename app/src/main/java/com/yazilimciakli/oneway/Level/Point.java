
package com.yazilimciakli.oneway.Level;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
