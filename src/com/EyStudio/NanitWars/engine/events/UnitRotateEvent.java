package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.NormalVector2D;
import com.EyStudio.NanitWars.engine.Unit;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitRotateEvent extends UnitTransformEvent{
    NormalVector2D oldRotation, newRotation;

    public NormalVector2D getOldRotation() {
        return oldRotation;
    }

    public NormalVector2D getNewRotation() {
        return newRotation;
    }
    
    public UnitRotateEvent(Unit source, NormalVector2D oldRotation, NormalVector2D newRotation){
        super(source);
        this.oldRotation = oldRotation;
        this.newRotation = newRotation;
    }
}
