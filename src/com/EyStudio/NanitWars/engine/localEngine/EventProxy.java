package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IEventProxy;
import com.EyStudio.NanitWars.engine.IObserver;
import com.EyStudio.NanitWars.engine.events.GameEvent;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by daneel on 16.04.17.
 */
public class EventProxy implements IEventProxy {

    private  class Object_Event {
        IObserver obj;
        Class<?> eventClass;
        public Object_Event(IObserver obj, Class<?> eventClass){
            this.obj = obj;
            this.eventClass = eventClass;
        }

        boolean equals(Object_Event other){
            return obj == other.obj && eventClass == other.eventClass;
        }
    }

    ArrayList<Object_Event> listeners = new ArrayList<>();

    @Override
    public void registerObserver(IObserver obj, Class<?> eventClass) {
        listeners.add(new Object_Event(obj, eventClass));
    }

    @Override
    public void unregistrerObserver(IObserver obj, Class<?> eventClass) {
        Iterator<Object_Event> iter = listeners.iterator();
        Object_Event object_event = new Object_Event(obj, eventClass);
        while (iter.hasNext()){
            if (iter.next().equals(object_event))
                iter.remove();
        }
    }

    @Override
    public void raiseEvent(GameEvent event) {
        for (Object_Event object_event : (Iterable<Object_Event>)listeners.clone()){
            IObserver obj = object_event.obj;
            Class<?> eventClass = object_event.eventClass;
            if (eventClass.isInstance(event))
                obj.onRaiseEvent(event);
        }
    }
}
