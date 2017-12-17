/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.EyStudio.NanitWars.engine.IObserver;
import com.EyStudio.NanitWars.engine.Unit;
import com.EyStudio.NanitWars.engine.UnitGroup;
import com.EyStudio.NanitWars.engine.events.GameEvent;
import com.EyStudio.NanitWars.engine.events.UnitDieEvent;
import com.EyStudio.NanitWars.engine.localEngine.Engine;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author daneel
 */
public class SelectAppState extends AbstractAppState implements ActionListener, IObserver{
    
    private UnitGroup unitGroup = new UnitGroup();
    private final ArrayList<Spatial> selectable = new  ArrayList<Spatial>();
    private final HashMap<Spatial, Unit> modelToUnit = new HashMap<>();
    private final HashMap<Unit, Spatial> unitToModel = new HashMap<>();
    InputManager inputManager;
    Application app;
    boolean dontClear = false;
    
    public enum InputMapping{
        SelectUnit, DontClear;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
        inputManager = app.getInputManager();
        this.app = app;
        addInputMappings();
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
        for (InputMapping i : InputMapping.values())
            inputManager.deleteMapping(i.name());
    }

    public UnitGroup getUnitGroup() {
        return unitGroup;
    }
    
    public void addSelecteble(Spatial spatial, Unit unit){
        selectable.add(spatial);
        modelToUnit.put(spatial, unit);
        unitToModel.put(unit, spatial);
    }
    
    public void removeSelecteble(Spatial spatial){
        selectable.remove(spatial);
        unitToModel.remove(modelToUnit.get(spatial));
        modelToUnit.remove(spatial);
    }
    
    public void removeSelecteble(Unit unit){
        selectable.remove(unitToModel.get(unit));
        modelToUnit.remove(unitToModel.get(unit));
        unitToModel.remove(unit);
    }
    
    private void addInputMappings(){
        inputManager.addMapping(InputMapping.SelectUnit.name(), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping(InputMapping.DontClear.name(), new KeyTrigger(KeyInput.KEY_LSHIFT));
        for (InputMapping i : InputMapping.values())
            inputManager.addListener(this, i.name());
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(InputMapping.SelectUnit.name()) && isPressed)
            onClick();
        if (name.equals(InputMapping.DontClear.name()))
            dontClear = isPressed;
    }
    
    public Unit getUnitUnderCursor(){
        Camera camera = app.getCamera();
        Vector2f mousePos2D = inputManager.getCursorPosition();
        Vector3f mousePos3D = camera.getWorldCoordinates(mousePos2D, 0f);
        //Vector3f clickDir = mousePos3D.add(camera.getWorldCoordinates(mousePos2D, 1f)).normalizeLocal();
        Vector3f clickDir = camera.getWorldCoordinates(mousePos2D, 1f).subtractLocal(mousePos3D).normalizeLocal();

        Ray ray = new Ray(mousePos3D, clickDir);
        CollisionResults collisionResults = new CollisionResults();

        CollisionResult firstCollision = null;
        Spatial firstSpation = null;
        for (Spatial spatial : selectable){
            spatial.getWorldBound().collideWith(ray, collisionResults);
            for (CollisionResult collision : collisionResults)
                if (firstCollision == null || firstCollision.getDistance() > collision.getDistance()){
                    firstCollision = collision;
                    firstSpation = spatial;
                }
            collisionResults.clear();
        }
        
        return firstSpation == null? null : modelToUnit.get(firstSpation);
    } 
    
    public void onClick(){
       
       Unit clickedUnit = getUnitUnderCursor();
        
        if (clickedUnit == null)
            return;

        Spatial selected = unitToModel.get(clickedUnit);
        if  (!dontClear){ 
            for (Unit unit : unitGroup){
                Spatial unitModel = unitToModel.get(unit);
                    ((SelectControl)unitModel.getControl(SelectControl.class)).setSelected(false);
            }
            unitGroup.clear();
        }

        unitGroup.add(clickedUnit);
        ((SelectControl)selected.getControl(SelectControl.class)).setSelected(true);

        HUDAppState HUD = app.getStateManager().getState(HUDAppState.class);
        if (HUD != null)
            HUD.getControler().updateSelected(unitGroup);

    }

    @Override
    public void onRaiseEvent(GameEvent event) {
        removeSelecteble(((UnitDieEvent)event).getSource());
    }
    
    
}
