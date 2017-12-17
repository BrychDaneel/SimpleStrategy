package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.*;
import com.EyStudio.NanitWars.engine.events.TickEndEvent;
import com.EyStudio.NanitWars.engine.events.TickStartEvent;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by daneel on 16.04.17.
 */
public class Engine implements IEngine, ITickable {

    IEventProxy eventProxy;
    Map map;
    UnitManager unitManager;
    ICollisionManager collisionManager;
    IPathManager pathManager;
    ITickGenerator tickGenerator;
    IPlayerManager playerManager;

    @Override
    public IEventProxy getEventProxy() {
        return eventProxy;
    }

    @Override
    public IMap getMap() {
        return map;
    }

    @Override
    public IUnitManager getUnitManager() {
        return unitManager;
    }

    @Override
    public ICollisionManager getCollisionManager() {
        return collisionManager;
    }

    @Override
    public IPathManager getPathManager() {
        return pathManager;
    }

    @Override
    public ITickGenerator getTickGenerator() {
        return tickGenerator;
    }

    @Override
    public void start() {
        tickGenerator.start();
    }

    @Override
    public void pause() {
        tickGenerator.stop();
    }

    @Override
    public void stop() {
        tickGenerator.stop();
    }

    @Override
    public IPlayerManager getPlayerManager() {
        return playerManager;
    }

    int tick = -1;
    @Override
    public void onTick() {
        eventProxy.raiseEvent(new TickStartEvent(++tick));
        eventProxy.raiseEvent(new TickEndEvent(tick));
    }

    public Engine(Map map, IPlayerManager playerManager){
        eventProxy = new EventProxy();
        this.map = map;
        unitManager = new UnitManager(this);
        collisionManager = new CollisionManager(this);
        pathManager = new BFSPathManager(this, map);
        //tickGenerator = new TickGenerator(this, 40);
        this.playerManager = playerManager;
    }

    public void setTickGenerator(ITickGenerator tickGenerator) {
        this.tickGenerator = tickGenerator;
    }
    
    public void serialize(ObjectOutput out) throws IOException{
        out.writeObject(map);
        unitManager.serialize(out);
    }
    
    public Engine(ObjectInput in, IPlayerManager playerManager)  throws IOException, ClassNotFoundException { 
        eventProxy = new EventProxy();
        this.playerManager = playerManager;
        //tickGenerator = new TickGenerator(this, 40);
        map = (Map) in.readObject();
        unitManager = new UnitManager(in, this);
        collisionManager = new CollisionManager(this);
        pathManager = new BFSPathManager(this, map);
    }
}
