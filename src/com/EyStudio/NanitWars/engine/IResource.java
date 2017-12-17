package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 15.04.17.
 */
public interface IResource {
    String getName();
    int getValue();
    int getLimit();
    void add(int value);
    void sub(int value);
    void expand(int value);
    void contract(int value);
}
