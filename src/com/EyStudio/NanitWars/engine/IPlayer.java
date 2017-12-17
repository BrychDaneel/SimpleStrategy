package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */
public interface IPlayer {
    String getName();
    int getPlayerID();
    IResourceSet getResourceSet();
    int getTeam();
}
