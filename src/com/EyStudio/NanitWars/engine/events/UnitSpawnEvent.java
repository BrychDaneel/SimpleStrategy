package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.Vector2D;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitSpawnEvent extends UnitEvent {

    Vector2D start, destination;

    public UnitSpawnEvent(Unit source, Vector2D start, Vector2D destination){
        super(source);
        this.start = start;
        this.destination = destination;
    }

    public Vector2D getStart() {
        return start;
    }

    public Vector2D getDestination() {
        return destination;
    }
}
