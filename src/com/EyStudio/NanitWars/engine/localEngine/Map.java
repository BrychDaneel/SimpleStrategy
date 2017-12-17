package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.ILevel;
import com.EyStudio.NanitWars.engine.IMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by daneel on 16.04.17.
 */
public class Map implements IMap {

    HashMap<Integer, ILevel> levels = new HashMap<>();
    String name;
    int width, height;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public ILevel getLevel(int levelID) {
        return levels.get(levelID);
    }

    @Override
    public Collection<ILevel> getAllLevels() {
        return levels.values();
    }

    public Map(String name, int width, int height){
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public void addLevel(ILevel level){
        levels.put(level.getLevelID(), level);
    }
}
