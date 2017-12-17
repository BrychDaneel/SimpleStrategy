package com.EyStudio.NanitWars.engine;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;

/**
 * Created by daneel on 15.04.17.
 */
public interface IMap extends Serializable{
    String getName();
    int getWidth();
    int getHeight();
    ILevel getLevel(int levelID);
    Collection<ILevel> getAllLevels();
}
