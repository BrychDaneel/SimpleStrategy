package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */

import static java.lang.Math.sqrt;

public class NormalVector2D {

    protected double x, y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        normalaize();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        normalaize();
    }

    public NormalVector2D(double x, double y){
        this.x = x;
        this.y = y;
        normalaize();
    }

    void normalaize(){
        double len = sqrt(x*x + y*y);
        if (len > 0){
            x = x / len;
            y = y / len;
        }
    }
}
