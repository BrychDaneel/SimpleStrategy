package com.EyStudio.NanitWars.engine;

import com.EyStudio.NanitWars.engine.localEngine.Player;

import java.util.ArrayList;

/**
 * Created by daneel on 16.04.17.
 */
public interface IPlayerManager {
    int register(IPlayer player);
    IPlayer getPlayer(int playerID);
    ArrayList<IPlayer> getAllies(IPlayer player);
    ArrayList<IPlayer> getEnemys(IPlayer player);
}
