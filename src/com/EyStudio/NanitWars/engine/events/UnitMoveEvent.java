package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.Vector2D;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitMoveEvent extends UnitTransformEvent {
    Vector2D newPosition, oldPosition;

    public Vector2D getNewPosition() {
        return newPosition;
    }

    public Vector2D getOldPosition() {
        return oldPosition;
    }

    public UnitMoveEvent(Unit source, Vector2D oldPosition, Vector2D newPosition){
        super(source);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }
}
