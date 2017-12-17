package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Unit;

/**
 * Created by daneel on 16.04.17.
 */
public abstract class UnitTransformEvent extends UnitEvent {

    public UnitTransformEvent(Unit source){
        super(source);
    }
}
