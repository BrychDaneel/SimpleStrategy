/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.elements.Element;
import mygame.NiftyAppState;

/**
 *
 * @author daneel
 */
public class HUDAppState extends AbstractAppState {
    
    NiftyJmeDisplay niftyDisplay;
    HUDControler controler;
    CastAppState castAppState;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        NiftyAppState niftyAppState = app.getStateManager().getState(NiftyAppState.class);
        niftyDisplay = niftyAppState.getNiftyDisplay();
        niftyDisplay.getNifty().fromXml("Interface/screens/HUDNiftyGui.xml", "HUD");
        controler = (HUDControler) niftyDisplay.getNifty().getScreen("HUD").getScreenController();
        controler.castAppState = castAppState;
        controler.engineAppState = (EngineAppState)app.getStateManager().getState(EngineAppState.class);
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    public HUDControler getControler() {
        return controler;
    }
    
    
}
