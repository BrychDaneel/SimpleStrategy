package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitModel {
    Vector2D center;
    int radius;

    public UnitModel(Vector2D center, int radius){
        this.center = center;
        this.radius = radius;
    }

    public UnitModel(Unit unit){
        this(unit.getPosition(), unit.getRadius());
    }

    public Vector2D getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
