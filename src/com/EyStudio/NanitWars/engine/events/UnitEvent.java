package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.Unit;

/**
 * Created by daneel on 16.04.17.
 */
public abstract class UnitEvent extends GameEvent{
    Unit source;

    public UnitEvent(Unit source){
        this.source = source;
    }
    public Unit getSource(){
        return source;
    }
}
