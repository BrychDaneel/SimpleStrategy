package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IPathManager;
import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.IPlayerManager;
import com.EyStudio.NanitWars.engine.IResourceSet;

/**
 * Created by daneel on 16.04.17.
 */
public class Player implements IPlayer {

    IResourceSet resourceSet;
    String name;
    int playerID, team;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPlayerID() {
        return playerID;
    }

    @Override
    public IResourceSet getResourceSet() {
        return resourceSet;
    }

    @Override
    public int getTeam(){
        return team;
    };

    public Player(IPlayerManager playerManager, int team, String name, IResourceSet resources){
        resourceSet = resources;
        this.name = name;
        this.team = team;
        playerID = playerManager.register(this);
    }

}
