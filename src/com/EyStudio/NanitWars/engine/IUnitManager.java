package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */

public interface IUnitManager extends Iterable<Unit>{
    int register(Unit unit);
    void unregister(Unit unit);
    Unit getUnit(int unitID);
    IEngine getEngine();
}
