package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.IResource;

/**
 * Created by daneel on 16.04.17.
 */
public class Resource implements IResource {

    String name;
    int limit = 0, value = 0;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public void add(int value) {
        this.value+=value;
        if (this.value > limit)
            this.value = limit;
    }

    @Override
    public void sub(int value) throws IllegalArgumentException{
        if (value > this.value)
            throw new IllegalArgumentException("Can't sub more " + name + " then have");
        this.value -= value;
    }

    @Override
    public void expand(int value) {
        this.limit += value;
    }

    @Override
    public void contract(int value) throws IllegalArgumentException{
        if (value > this.limit)
            throw new IllegalArgumentException("Can't contract more " + name + " then have");
        limit -= value;
    }

    public Resource(String name){
        this.name = name;
    }
}
