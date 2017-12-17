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
import com.jme3.renderer.ViewPort;

/**
 *
 * @author daneel
 */
public class NiftyAppState extends AbstractAppState {
    
    NiftyJmeDisplay niftyDisplay;
    Application app;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;

        ViewPort HUD = app.getRenderManager().createPostView("HUD", app.getCamera());
        
        niftyDisplay = new NiftyJmeDisplay(
            app.getAssetManager(), 
            app.getInputManager(), 
            app.getAudioRenderer(), 
            HUD
        );
        
        HUD.addProcessor(niftyDisplay);
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        app.getRenderManager().getPostView("HUD").removeProcessor(niftyDisplay);
    }

    public NiftyJmeDisplay getNiftyDisplay() {
        return niftyDisplay;
    }
    
}
