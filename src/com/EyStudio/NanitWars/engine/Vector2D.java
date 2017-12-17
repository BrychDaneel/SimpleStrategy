package com.EyStudio.NanitWars.engine;

import java.io.Serializable;

/**
 * Created by daneel on 15.04.17.
 */

public class Vector2D implements Serializable{
    protected int x, y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int distanceSquared(Vector2D other){
        int dx = other.getX() - x;
        int dy = other.getY() - y;
        return dx * dx + dy * dy;
    }
    public double distance(Vector2D other){
        return Math.sqrt(distanceSquared(other));
    }
}
