package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */
public interface IEngine {
    IEventProxy getEventProxy();
    IMap getMap();
    IUnitManager getUnitManager();
    ICollisionManager getCollisionManager();
    IPathManager getPathManager();
    ITickGenerator getTickGenerator();
    IPlayerManager getPlayerManager();
    void  start();
    void  pause();
    void stop();
}
