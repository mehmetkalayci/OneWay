package com.yazilimciakli.oneway.LevelRequest.Request;

import com.yazilimciakli.oneway.Level.Level;

public class AllLevels {

    private LevelRequest levels[];

    public AllLevels() {
    }

    public AllLevels(LevelRequest[] level) {
        this.levels = level;
    }

    public LevelRequest[] getLevels() {
        return levels;
    }

    public void setLevels(LevelRequest[] levels) {
        this.levels = levels;
    }
}