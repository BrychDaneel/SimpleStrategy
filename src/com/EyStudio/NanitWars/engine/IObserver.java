package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */
import com.EyStudio.NanitWars.engine.events.GameEvent;

public interface IObserver {
    void onRaiseEvent(GameEvent event);
}
