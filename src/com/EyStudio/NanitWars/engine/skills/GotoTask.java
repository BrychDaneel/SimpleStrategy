package com.EyStudio.NanitWars.engine.skills;

import com.EyStudio.NanitWars.engine.ICollisionManager;
import com.EyStudio.NanitWars.engine.IEngine;
import com.EyStudio.NanitWars.engine.ILevel;
import com.EyStudio.NanitWars.engine.IMap;
import com.EyStudio.NanitWars.engine.IPathManager;
import com.EyStudio.NanitWars.engine.IUnitTask;
import com.EyStudio.NanitWars.engine.NormalVector2D;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.UnitModel;
import com.EyStudio.NanitWars.engine.Vector2D;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.ArrayDeque;

/**
 * Created by daneel on 17.04.17.
 */
public class GotoTask implements IUnitTask {

    Unit owner;
    boolean first = true;
    Vector2D destination;
    ArrayDeque<Vector2D> path;
    IPathManager pathManager;
    ICollisionManager collisionManager;
    IMap map;
    int distance;

    private void firstRun(){
        path = new ArrayDeque<>(pathManager.pathToPoint(owner, destination, distance));
    }

    double speed = 0;
    private void move(){
        speed += owner.getSpeed() * owner.getManager().getEngine().getTickGenerator().getFrequency() / 1000;
        Vector2D nextPoint = null;
        int x = owner.getPosition().getX();
        int y = owner.getPosition().getY();

        Vector2D top = path.peekFirst();
        while (top != null && speed > Math.hypot(top.getX() - x, top.getY() - y)) {
            nextPoint = path.pollFirst();
            if (path.isEmpty())
                top = null;
            else
                top = path.peekFirst();
        }

        if (nextPoint != null) {
            int rx = nextPoint.getX();
            int ry = nextPoint.getY(); 
            
            rx-= owner.getPosition().getX();
            ry-= owner.getPosition().getY();
            
            UnitModel unitModel = new UnitModel(nextPoint, owner.getRadius());
            ILevel level = owner.getLevel();
            if (collisionManager.isAnyCollide(unitModel, owner.getUnitID(), level)){
                path = new ArrayDeque<>(pathManager.pathToPoint(owner, destination, distance));
                return;
            }
            owner.rotate(new NormalVector2D(rx, ry));
            owner.move(nextPoint);
            speed = 0;
        }
    }

    @Override
    public boolean perform(Unit source) {
        if (first)
            firstRun();
        first = false;
        move();
        return path.isEmpty();
    }

    public GotoTask(Unit owner, Vector2D destination, int distance){
        this.owner = owner;
        this.destination = destination;
        this.distance = distance;
        IEngine engine = owner.getManager().getEngine();
        this.pathManager = engine.getPathManager();
        this.collisionManager = engine.getCollisionManager();
        this.map = engine.getMap();
    }
    
    public GotoTask(Unit owner, Vector2D destination){
        this(owner, destination, 0);
    }
    
    @Override
    public void serialize(ObjectOutput out) throws IOException{
        out.writeBoolean(first);
        out.writeInt(destination.getX());
        out.writeInt(destination.getY());
        out.writeInt(distance);
        out.writeObject(path);
    }
    
    public GotoTask(ObjectInput in,  Unit owner)  throws IOException, ClassNotFoundException { 
        this.owner = owner;
        first = in.readBoolean();
        int destX = in.readInt();
        int destY = in.readInt();
        distance = in.readInt();
        path = (ArrayDeque<Vector2D>) in.readObject();
        
        destination = new Vector2D(destX, destY);
        
        IEngine engine = owner.getManager().getEngine();
        this.pathManager = engine.getPathManager();
        this.collisionManager = engine.getCollisionManager();
        this.map = engine.getMap();
    }
}
