package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainQuad;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        this.getGuiViewPort().setEnabled(false);
        
        rootNode.attachChild(assetManager.loadModel("Scenes/map.j3o"));
        stateManager.attach(new RTSCameraAppState());
        
        stateManager.attach(new NiftyAppState());
        HUDAppState hudAppState = new HUDAppState(); 
        stateManager.attach(hudAppState);
        
        SelectAppState selectAppState = new SelectAppState(); 
        stateManager.attach(selectAppState);
        
        CastAppState castAppState = new CastAppState();
        castAppState.map = rootNode.getChild("terrain-map");
        hudAppState.castAppState = castAppState;
        stateManager.attach(castAppState);
        
        EngineAppState engineAppState = new EngineAppState();
        engineAppState.setRootNode((Node)rootNode.getChild("Scene"));
        engineAppState.setHeightMap((TerrainQuad)rootNode.getChild("terrain-map"));
        engineAppState.setSelectAppState(selectAppState);
        stateManager.attach(engineAppState);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void initialize() {
        super.initialize(); //To change body of generated methods, choose Tools | Templates.

    }
    
}
