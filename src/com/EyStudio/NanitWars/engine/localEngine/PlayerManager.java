package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IPlayer;
import com.EyStudio.NanitWars.engine.IPlayerManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by daneel on 16.04.17.
 */
public class PlayerManager implements IPlayerManager {

    HashMap<Integer, IPlayer> players = new HashMap<>();
    int nextID = 1;

    @Override
    public int register(IPlayer player) {
        players.put(nextID, player);
        return nextID++;
    }

    @Override
    public IPlayer getPlayer(int playerID) {
        return players.get(playerID);
    }

    @Override
    public ArrayList<IPlayer> getAllies(IPlayer player) {
        ArrayList<IPlayer> result =  new ArrayList<>();
        for (IPlayer pl : players.values())
            if (pl.getTeam() == player.getTeam())
                result.add(pl);
        return result;
    }

    @Override
    public ArrayList<IPlayer> getEnemys(IPlayer player) {
        ArrayList<IPlayer> result =  new ArrayList<>();
        return result;
    }
}
