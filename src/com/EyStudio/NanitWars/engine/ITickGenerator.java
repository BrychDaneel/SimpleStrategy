package com.EyStudio.NanitWars.engine;

/**
 * Created by daneel on 16.04.17.
 */
public interface ITickGenerator {
    void start();
    void stop();
    void setFrequency(int milliseconds);
    int getFrequency();
}
