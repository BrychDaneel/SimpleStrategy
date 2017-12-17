package com.EyStudio.NanitWars.engine.localEngine;

import com.EyStudio.NanitWars.engine.ITickGenerator;
import com.EyStudio.NanitWars.engine.ITickable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by daneel on 16.04.17.
 */
public class TickGenerator extends TimerTask implements ITickGenerator{
    @Override
    public void start() {
        timer.scheduleAtFixedRate(this, 0, frequency);
    }

    @Override
    public void stop() {
        this.cancel();
        timer.cancel();
    }

    @Override
    public void setFrequency(int milliseconds) {
        frequency = milliseconds;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }

    private ITickable listener;
    int frequency;
    Timer timer = new Timer();

    TickGenerator(ITickable listener, int frequency){
        this.listener = listener;
        this.frequency = frequency;
    }

    @Override
    public void run(){
        listener.onTick();
    }
}
