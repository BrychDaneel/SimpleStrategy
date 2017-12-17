/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.EyStudio.NanitWars.engine;

/**
 *
 * @author daneel
 */
public abstract class SimpleLevel implements ILevel{
    
    protected boolean[][] fill;
    protected String name;
    protected int levelID;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getLevelID() {
        return levelID;
    }

    @Override
    public boolean isFill(int x, int y) {
        return fill[x][y];
    }

    public SimpleLevel(String name, int levelID){
        this.name = name;
        this.levelID = levelID;
    }
}
