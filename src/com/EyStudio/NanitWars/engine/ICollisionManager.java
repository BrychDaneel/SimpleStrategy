package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 16.04.17.
 */
public interface ICollisionManager {
    boolean isLevelCollide(UnitModel model, ILevel level);    
    boolean isUnitCollide(UnitModel model, int unitID, ILevel level);
    boolean isAnyCollide(UnitModel model, int unitID, ILevel level);
    Vector2D findFreeSpace(Vector2D startPoint, int radius, ILevel level);
}
