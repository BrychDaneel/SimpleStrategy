package com.EyStudio.NanitWars.engine;

import java.io.Serializable;

/**
 * Created by daneel on 15.04.17.
 */
public interface ILevel extends Serializable{
    String getName();
    int getLevelID();
    boolean isFill(int x, int y);
}
