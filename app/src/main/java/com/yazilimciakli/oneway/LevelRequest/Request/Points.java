package com.yazilimciakli.oneway.LevelRequest.Request;

public class Points
{
    private Subpoints[] subpoints;

    private String y;

    private String x;

    public Subpoints[] getSubpoints ()
    {
        return subpoints;
    }

    public void setSubpoints (Subpoints[] subpoints)
    {
        this.subpoints = subpoints;
    }

    public String getY ()
    {
        return y;
    }

    public void setY (String y)
    {
        this.y = y;
    }

    public String getX ()
    {
        return x;
    }

    public void setX (String x)
    {
        this.x = x;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subpoints = "+subpoints+", y = "+y+", x = "+x+"]";
    }
}