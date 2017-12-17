package com.EyStudio.NanitWars.engine.events;

/**
 * Created by daneel on 16.04.17.
 */
public abstract class TickEvent extends GameEvent {
    int tick;
    public int getTick() {
        return tick;
    }
    TickEvent(int tick){
        this.tick = tick;
    }
}
