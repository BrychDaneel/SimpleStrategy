/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.ITickGenerator;
import com.EyStudio.NanitWars.engine.ITickable;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 *
 * @author daneel
 */
public class TickGeneratorAppState extends AbstractAppState implements ITickGenerator{
    
    boolean started;
    int accamulated;
    int frequency;
    ITickable listener;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }
    
    @Override
    public void update(float tpf) {
        if (!started)
            return;
        
        accamulated += Math.round(tpf * 1000);
        if (accamulated >= frequency){
            accamulated = 0;
            listener.onTick();
        }
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    @Override
    public void start() {
        accamulated = 0;
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void setFrequency(int milliseconds) {
        frequency = milliseconds;
    }

    @Override
    public int getFrequency() {
        return frequency;
    }
    
    TickGeneratorAppState(ITickable listener, int frequency){
        super();
        this.listener = listener;
        this.frequency = frequency;
    }
    
}
