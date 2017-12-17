package com.EyStudio.NanitWars.engine;

import com.EyStudio.NanitWars.engine.events.GameEvent;

/**
 * Created by daneel on 15.04.17.
 */
public interface IEventProxy {
    void registerObserver(IObserver obj, Class<?> eventClass);
    void unregistrerObserver(IObserver obj, Class<?> eventClass);
    void raiseEvent(GameEvent event);
}
