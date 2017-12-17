package com.EyStudio.NanitWars.engine.events;

import com.EyStudio.NanitWars.engine.ILevel;
import com.EyStudio.NanitWars.engine.Unit;

/**
 * Created by daneel on 16.04.17.
 */
public class UnitChangeLevelEvent extends UnitEvent{
    ILevel oldLevel, newLevel;
    public UnitChangeLevelEvent(Unit source, ILevel oldLevel, ILevel newLevel){
        super(source);
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    public ILevel getOldLevel() {
        return oldLevel;
    }

    public ILevel getNewLevel() {
        return newLevel;
    }
}
